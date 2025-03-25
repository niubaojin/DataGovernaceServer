package com.synway.datarelation.util;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.pojo.dfwork.NodeInfoParam;
import com.synway.datarelation.pojo.modelmonitor.DfWorkModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelProject;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import com.synway.datarelation.pojo.modelmonitor.ResultObj;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DfWorkRestTemplateHandle {
    private Logger logger = LoggerFactory.getLogger(DfWorkRestTemplateHandle.class);
    RestTemplate restTemplateUrl = new RestTemplate();
    @Autowired
    Environment env;


    /**
     *  获取任务信息的接口
     * @param nodeInsQueryParam
     * @return
     */
    public List<DfWorkModelNodeInsInfo> getTaskInstanceInfo(NodeInsQueryParam nodeInsQueryParam) throws Exception{
        logger.info("需要等待30秒钟，dfwork不能频繁查询");
        Thread.sleep(30*1000);
        logger.info("获取dfwork中任务实例接口的请求参数为"+JSONObject.toJSONString(nodeInsQueryParam));
        String dfWorkUrl = env.getProperty("dfWorkUrl")+"/dfworks/interface/getTaskInstanceInfo";
        Map<String,String> paramMap = new HashMap<>();
        if(null!=nodeInsQueryParam){
            paramMap = BeanUtils.describe(nodeInsQueryParam);
        }
        dfWorkUrl = dfWorkUrl +"?"+getQueryParam(paramMap,"1");
        String responseValue = restTemplateUrl.getForObject(dfWorkUrl,String.class);
        if(StringUtils.isEmpty(responseValue)){
            logger.error("该dfwork中没有获取任务实例信息的接口，返回的数据为空");
            throw new Exception("该dfwork中没有获取任务实例信息的接口，返回的数据为空");
        }
        ResultObj resultObj = JSONObject.parseObject(responseValue,ResultObj.class);
        if("0".equalsIgnoreCase(resultObj.getReturnCode())){
            List<DfWorkModelNodeInsInfo> nodeInsInfos = JSONObject.parseArray(resultObj.getReturnValue(),DfWorkModelNodeInsInfo.class);
            return nodeInsInfos;
        }else{
            logger.error("调用接口 /dfworks/interface/getTaskInstanceInfo：{}", resultObj.getReturnMessage());
            throw new Exception(resultObj.getReturnMessage());
        }
    }

    public List<ModelProject> getProjectByDfWorks() throws Exception{
        // 因为不能频繁查询 所以每次请求前先等待30秒
        Thread.sleep(30*1000);
        logger.info("获取dfwork中所有的项目名信息");
        String dfWorkUrl = env.getProperty("dfWorkUrl")+"/dfworks/interface/getProjectInfo";
//        String dfWorkUrl = "http://10.1.13.46:8080/dfworks/interface/getProjectInfo";
        String responseValue = restTemplateUrl.getForObject(dfWorkUrl,String.class);
        if(StringUtils.isEmpty(responseValue)){
            logger.error("该dfwork中没有获取项目名的接口，返回的数据为空");
            throw new Exception("该dfwork中没有获取项目名的接口，返回的数据为空");
        }
        ResultObj resultObj = JSONObject.parseObject(responseValue,ResultObj.class);
        // 0 表示请求成功 1 表示请求失败
        if("0".equalsIgnoreCase(resultObj.getReturnCode())){
            List<ModelProject> modelProjects = JSONObject.parseArray(resultObj.getReturnValue(),ModelProject.class);
            return modelProjects;
        }else{
            logger.error(resultObj.getReturnMessage());
            throw new Exception(resultObj.getReturnMessage());
        }
    }

    private String getQueryParam(Map<String,String> params,String type){
        StringBuilder stringBuilder = new StringBuilder();

        String[] keyNames = params.keySet().toArray(new String[]{});
        String tmpKeyName;
        String tmpValue;
        for (int i = 0; i < keyNames.length; i++) {
            tmpKeyName = keyNames[i];
            tmpValue = params.get(tmpKeyName);
            //如果key对应的值为null时则跳过
            if(null==tmpValue||"class".equalsIgnoreCase(tmpKeyName)||"null".equalsIgnoreCase(tmpValue)){
                continue;
            }
            if(type.equals("1")){
                if("start".equalsIgnoreCase(tmpKeyName)||"limit".equalsIgnoreCase(tmpKeyName)){
                    continue;
                }
            }
//            try{
//                if("bizdate".equalsIgnoreCase(tmpKeyName)){
//                    tmpValue = URLEncoder.encode(tmpValue,"utf-8");
//                }
//            }catch (Exception e){
//                logger.error(""+ExceptionUtil.getExceptionTrace(e));
//            }
            stringBuilder.append("&");
            stringBuilder.append(tmpKeyName);
            stringBuilder.append("=");
            stringBuilder.append(tmpValue);
        }
        //替换掉多余的&，第一个&
        stringBuilder.replace(0,1,"");
        String queryParamStr = stringBuilder.toString();
        return queryParamStr;
    }

    /**
     *  节点信息查询接口 获取节点的代码信息
     * @param nodeInfoParam
     * @return
     */
    public ResultObj getNodeInfo(NodeInfoParam nodeInfoParam) throws Exception{
        ResultObj resultObj = null;
        try{
            Thread.sleep(30*1000);
            String nodeInfoUrl = env.getProperty("dfWorkUrl")+"/dfworks/interface/getNodeInfo";
            Map<String,String> paramMap = new HashMap<>();
            if(null!=nodeInfoParam){
                paramMap = BeanUtils.describe(nodeInfoParam);
            }
            nodeInfoUrl = nodeInfoUrl +"?"+getQueryParam(paramMap,"2");
            logger.info("获取dfwork中所有节点的信息查询接口,url信息为："+nodeInfoUrl);
            String responseValue = restTemplateUrl.getForObject(nodeInfoUrl,String.class);
            if(StringUtils.isEmpty(responseValue)){
                logger.error("该dfwork中没有获取【节点信息查询接口】的接口，返回的数据为空");
                throw new Exception("该dfwork中没有获取【节点信息查询接口】的接口，返回的数据为空");
            }
            resultObj = JSONObject.parseObject(responseValue,ResultObj.class);
        }catch (Exception e){
            resultObj = new ResultObj();
            resultObj.setReturnCode("1");
            resultObj.setReturnMessage("查询接口报错"+e.getMessage());

        }
        return resultObj;
    }

}
