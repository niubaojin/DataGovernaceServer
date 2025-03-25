package com.synway.datarelation.service.datablood.impl;

import com.synway.datarelation.pojo.databloodline.DataBloodlineQueryParams;
import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.RelationInfoRestTemolate;
import com.synway.datarelation.service.datablood.ExternalInterfceService;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExternalInterfceServiceImpl implements ExternalInterfceService {
    private Logger logger = LoggerFactory.getLogger(ExternalInterfceServiceImpl.class);

    @Autowired
    RestTemplateHandle restTemplateHandle;
    /**
     *  在 数据历程中，需要用到这个接口，要获取到大数据平台上表的数据历程信息
     *  需要调取到 血缘中的输入协议 因为 数据接入/数据探查/质量检测使用的都是源协议
     *  输入的是
     * @param type  1:表示是标准管理
     * @param searchName  这个是查询内容 可能是 项目名.表名 / tableId
     */
    @Override
    public  List<String> getStandardBloodByName(String type, String searchName) throws Exception{
        List<String> result = null;
        if(StringUtils.isEmpty(searchName)){
            throw new Exception("搜索的字段不能为空");
        }
        if("1".equalsIgnoreCase(type)){
            DataBloodlineQueryParams queryParams = new DataBloodlineQueryParams();
            queryParams.setQuerytype("EXACT");
            queryParams.setQueryinfo(searchName);
            RelationInfoRestTemolate restTemolate = restTemplateHandle.queryStandardRelationInfo(queryParams);
            if("ok".equalsIgnoreCase(restTemolate.getReqRet())){
                List<QueryBloodlineRelationInfo> relationInfos =  restTemolate.getReqInfo();
                result = parsingStandardGetInput(relationInfos , searchName);
            }else{
                throw new Exception(restTemolate.getError());
            }
        }
        return result;
    }


    /**
     * 从获取到的数据中解析出指定协议的全部输入协议信息
     * 这个用于数据历程的查询功能 根据输出协议查询出输入协议
     * 即  sourceEngName
     * @param relationInfos
     * @return
     */
    private List<String> parsingStandardGetInput(List<QueryBloodlineRelationInfo> relationInfos,String queryName){
        List<String> result = new ArrayList<>();
        Map<String , List<String>> sourceMap = new HashMap<>();
        for(QueryBloodlineRelationInfo relationInfo:relationInfos){
            String targetId = (relationInfo.getTargetEngName()+"|"+relationInfo.getTargetSysId()).toUpperCase();
            String nodeId = (relationInfo.getTargetEngName()+"|"+relationInfo.getTargetSysId()+"|"
                    + relationInfo.getTargetProjectName()+"."+relationInfo.getTargetTableName()+"|"
                    +relationInfo.getTargetDBEngName()).toUpperCase();
            List<String> nodeIds = sourceMap.getOrDefault(targetId,new ArrayList<String>());
            nodeIds.add(nodeId);
            sourceMap.put(targetId.toUpperCase(),nodeIds);
        }
        for(QueryBloodlineRelationInfo queryInfo:relationInfos){
            String sourceNodeId = (queryInfo.getSourceEngName()+"|"+queryInfo.getSourceSysId()).toUpperCase();
            if(sourceMap.getOrDefault(sourceNodeId,null) == null
                    &&( queryName.equalsIgnoreCase(queryInfo.getTargetEngName()) ||
                    queryName.equalsIgnoreCase(queryInfo.getTargetDBEngName()+"."+queryInfo.getTargetTableName()))){
                if(!result.contains(queryInfo.getSourceEngName().toLowerCase())){
                    result.add(queryInfo.getSourceEngName().toLowerCase());
                }
            }
        }
        sourceMap.clear();
        return result;
    }
}
