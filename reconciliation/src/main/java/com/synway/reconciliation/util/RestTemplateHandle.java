package com.synway.reconciliation.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.pojo.DataResource;
import com.synway.reconciliation.pojo.QueryBloodlineRelationInfo;
import com.synway.reconciliation.pojo.RelationInfoRestTemolate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RestTemplate请求处理类
 *
 * @author
 */
@Component
public class RestTemplateHandle {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHandle.class);

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取对账存储异常数据的ftp信息，如果有多个，则使用轮询
     *
     * @return
     */
    int count = 0;

    public DataResource getReconciliationFtpDataResource() throws Exception {
        if (count > Constants.DEFAULT_LIMIT) {
            count = 0;
        }
        String errorMessage = StringUtils.EMPTY;
        String url = "http://dataresource/dataresource/api/getAllResources";
        List<DataResource> dataResourceList = new ArrayList<>();
        try {
            String result = restTemplate.getForObject(url, String.class);
            String dataStr = JSONObject.parseObject(result).getObject("data", String.class);
            dataResourceList = JSONArray.parseArray(dataStr, DataResource.class);
        } catch (Exception e) {
            logger.error("调用数据仓库接口出错" + ExceptionUtil.getExceptionTrace(e));
            throw new Exception("调用数据仓库接口出错");
        }
        if (CollectionUtils.isEmpty(dataResourceList)) {
            errorMessage = "调用接口getAllDataResource返回为空，请检查对账错误数据的FTP是否已经创建，并且确保连接可用。";
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }
        dataResourceList = dataResourceList.stream().filter(i -> Constants.NUM_THREE == i.getPurpose()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(dataResourceList)) {
            int currentLength = dataResourceList.size();
            return dataResourceList.get(count++ % currentLength);
        }
        throw new Exception("没有检测到对账存储错误数据的FTP，请先配置对账存储错误数据FTP。");
    }

    /**
     * 获取血缘关系
     *
     * @return
     */
    public List<QueryBloodlineRelationInfo> getAllStandardBlood() {
        String url = "http://datarelation/datarelation/api/externalInterfce/getAllStandardBlood";
        RelationInfoRestTemolate result = restTemplate.getForObject(url, RelationInfoRestTemolate.class);
        List<QueryBloodlineRelationInfo> reqInfoList = result.getReqInfo();
        if (!CollectionUtils.isEmpty(reqInfoList)) {
            logger.info("getAllStandardBlood获取血缘关系结果：" + result.getReqRet() + "-" + result.getError() + "-" + result.getReqInfo().size());
        } else {
            logger.info("getAllStandardBlood获取血缘关系结果：" + result.getReqRet() + "-" + result.getError());
        }
        return reqInfoList;
    }

    /**
     * 告警信息推送运维系统
     *
     * @return
     */
    public JSONObject pushAlarmInfo(String msg) {
        String url = "http://dataoperations/dataoperations/pushAlarmInfo";
        JSONObject obj = restTemplate.postForObject(url, msg, JSONObject.class);
        return obj;
    }
}
