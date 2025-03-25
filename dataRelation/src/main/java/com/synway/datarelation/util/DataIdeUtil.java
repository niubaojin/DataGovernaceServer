package com.synway.datarelation.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.env.Environment;

import com.synway.datarelation.pojo.modelmonitor.ModelNodeInfo;
import com.synway.datarelation.pojo.modelmonitor.ModelNodeInsInfo;
import com.synway.datarelation.pojo.modelmonitor.NodeInsQueryParam;
import com.synway.datarelation.pojo.modelmonitor.NodeQueryParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2019/4/22 8:53
 */
@Component
public class DataIdeUtil {


    private static Logger logger = LoggerFactory.getLogger(DataIdeUtil.class);
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private Environment env;

//    @Value("${baseKey}")
//    private String baseKey;
//    @Value("${baseToken}")
//    private String baseToken;
//    @Value("${baseId}")
//    private String baseId;
//    @Value("${tenantId}")
//    private String tenantId;
//    @Value("${baseApiUrl}")
//    private String baseApiUrl;
//
//    @Value("${projectUrl}")
//    private String projectUrl;
//    @Value("${nodeUrl}")
//    private String nodeUrl;
//    @Value("${nodeInsUrl}")
//    private String nodeInsUrl;



    /**
     * 发送请求的方法
     * @param url
     * @param params
     * @return
     */
    private String postRequest(String url, Map<String,String> params)throws Exception{
        String baseToken = env.getProperty("dataworks.http.baseToken");
        String baseId = env.getProperty("dataworks.http.baseId");
        String tenantId = env.getProperty("dataworks.http.tenantId");
        String timestamp = System.currentTimeMillis()+"";
        String queryParamStr = getQueryParam(timestamp,params);
        String signature = getSignature(baseToken,queryParamStr);
        //生成公共请求头Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("signature",signature);
        httpHeaders.add("base_id",baseId);
        httpHeaders.add("tenant_id",tenantId);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(params),httpHeaders);
        String requestUrl = url;
        HttpEntity<String> entity = restTemplate.postForEntity(requestUrl,httpEntity,String.class);
        String resultStr = entity.getBody();

        return resultStr;
    }

    /**
     * 发送请求的方法
     * 20201224 转义在url里面跳转 不知道是否正确
     * @param url
     * @param params
     * @return
     */
    private String getRequest(String url, Map<String,String> params){
        if(null==params){
            params = new HashMap<>();
        }
        String baseToken = env.getProperty("dataworks.http.baseToken");
        String baseId = env.getProperty("dataworks.http.baseId");
        String tenantId = env.getProperty("dataworks.http.tenantId");

        String timestamp = System.currentTimeMillis()+"";
        String queryParamStr = getQueryParam(timestamp,params);
        String signature = getSignature(baseToken,queryParamStr);
        //生成公共请求头Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("signature",signature);
        httpHeaders.add("base_id",baseId);
        httpHeaders.add("tenant_id",tenantId);
        HttpEntity<String> httpEntity = new HttpEntity<>(null,httpHeaders);
        String requestUrl = url +"?"+ queryParamStr;
        logger.info("发送请求地址为【"+requestUrl+"】");
        HttpEntity<String> entity = restTemplate.exchange(requestUrl,HttpMethod.GET,httpEntity,String.class);
        String resultStr = entity.getBody();

        return resultStr;
    }

    /**
     * 拼接查询字段为url请求地址字符串
     * @param timestamp
     * @param params
     * @return
     */
    private String getQueryParam(String timestamp,Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        //固定需要的请求参数
        String baseKey = env.getProperty("dataworks.http.baseKey");

        params.put("baseKey",baseKey);
        params.put("timestamp",timestamp);
        String[] keyNames = params.keySet().toArray(new String[]{});
        String tmpKeyName;
        String tmpValue;
        for (int i = 0; i < keyNames.length; i++) {
            tmpKeyName = keyNames[i];
            tmpValue = params.get(tmpKeyName);
            //如果key对应的值为null时则跳过
            if(null==tmpValue||"class".equalsIgnoreCase(tmpKeyName)){
                continue;
            }
//            if("createTime".equalsIgnoreCase(tmpKeyName)){
//                try{
//                    tmpValue = URLEncoder.encode(tmpValue,"utf-8");
//                }catch (Exception e){
//                    logger.error(""+ExceptionUtil.getExceptionTrace(e));
//                }
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
     * 利用新的 url 中的 query parameter 结合 token 生成 signature
     * @param token
     * @param urlQuery
     * @return
     */
    private String getSignature(String token, String urlQuery) {
        if (StringUtils.isNotBlank(urlQuery)) {
            try {
                urlQuery = URLDecoder.decode(urlQuery, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("根据basekey,timestamp生成模块需要的signature失败:" + e);
            }
        }

        String[] paraArray = new String[] {};
        if (StringUtils.isNotBlank(urlQuery)) {
            String[] queryArray = urlQuery.split("&");
            paraArray = (String[]) ArrayUtils.addAll(queryArray, paraArray);
        }

        Arrays.sort(paraArray);

        StringBuffer buffer = new StringBuffer();
        buffer.append(token);
        buffer.append(":");

        for (int i = 0; i < paraArray.length; i++) {
            buffer.append(paraArray[i]);
            buffer.append("&");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(":");
        buffer.append(token);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(buffer.toString().getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("根据basekey,timestamp生成模块需要的signature失败:" + e);
        }
        if(md != null){
            return byteArrayToHexString(md.digest());
        }else{
            return "";
        }
    }

    private String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte byt : byteArray) {
            sb.append(byteToHexString(byt));
        }
        return sb.toString();
    }

    private String byteToHexString(byte byt) {
        int n = byt;
        if (n < 0){
            n = 256 + n;
        }
        return hexDigits[n / 16] + hexDigits[n % 16];
    }

    /**
     * 获取所有项目信息
     * @return
     * @throws Exception
     */
    public String findAllProjects()throws Exception{
        String projectUrl = env.getProperty("dataworks.http.projectUrl");
        String url = projectUrl;
        return getRequest(url,null);
//        return FileUtils.readFileToString(new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\数据\\project.txt"),"utf-8");
    }

    /**
     * 获取节点信息
     * @return
     * @throws Exception
     */
    public String findNodes(NodeQueryParam param)throws Exception{
        String nodeUrl = env.getProperty("dataworks.http.nodeUrl");
        String url = nodeUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=param){
            paramMap = BeanUtils.describe(param);
        }
        return getRequest(url,paramMap);
//        return FileUtils.readFileToString(new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\数据\\node.txt"),"utf-8");
    }

    /**
     * 获取节点实例信息
     * @return
     * @throws Exception
     */
    public String findNodeIns(NodeInsQueryParam param)throws Exception{
        String nodeInsUrl = env.getProperty("dataworks.http.nodeInsUrl");
        String url = nodeInsUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=param){
            paramMap = BeanUtils.describe(param);
        }
        return getRequest(url,paramMap);
//        return FileUtils.readFileToString(new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\数据\\nodeIns-20190413.txt"),"utf-8");
    }

    /**
     * 获取日志信息
     * @param id
     * @return
     * @throws Exception
     */
    public String getlog(Integer id)throws Exception{
        String nodeInsUrl = env.getProperty("dataworks.http.nodeInsUrl");
        String url = nodeInsUrl+"/"+id+"/log";
        Map<String,String> paramMap = new HashMap<>();
        return getRequest(url,paramMap);
//        return FileUtils.readFileToString(new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\数据\\nodeIns-20190413.txt"),"utf-8");
    }

    /**
     * 查询历史日志信息
     * @param id
     * @return
     * @throws Exception
     */
    public String getHistorylog(Integer id)throws Exception{
        String nodeInsUrl = env.getProperty("dataworks.http.nodeInsUrl");
        String url = nodeInsUrl+"/"+id+"/historyLog";
        Map<String,String> paramMap = new HashMap<>();
        return getRequest(url,paramMap);
//        return FileUtils.readFileToString(new File("C:\\Users\\SHIMENG\\Desktop\\数据模型监控模块\\数据\\nodeIns-20190413.txt"),"utf-8");
    }


    /**
     *  查询任务代码 目前主要查询sql节点的代码信息
     * @param projectName  项目名称
     * @param flowName  工作流名称
     * @param nodeName   节点名称
     * @return  {String requestId;//请求的 id
     *           String returnCode;//0 表示调用成功
     *           String returnMessage;//返回执行的详细信息
     *           String returnValue; //执行结果,返回节点代码
     *           }
     * @throws Exception
     */
    public String queryCode(String projectName,String flowName,String nodeName) throws Exception{
        String baseApiUrl = env.getProperty("dataworks.http.baseApiUrl");
        String url = baseApiUrl+ "/v2.0/project/"+projectName+"/flow/"+flowName+"/node/"+nodeName+"/code";
        Map<String,String> paramMap = new HashMap<>(1);
        return getRequest(url,paramMap);
    }

    public static void main(String[] args) throws Exception{

        ModelNodeInfo modelNodeInfo = JSON.parseObject("{\"nodeName\":\"tb_sub_sjyj_jbxx\",\"nodeDisplayName\":\"tb_sub_sjyj_jbxx\",\"runType\":0,\"fileId\":65878,\"fileVersion\":2,\"filePath\":\"./codes/克州下发模型结果/tb_sub_sjyj_jbxx.sh\",\"nodeType\":6,\"description\":\"\",\"paraValue\":null,\"startEffectDate\":\"1970-01-01 00:00:00\",\"endEffectDate\":\"9999-12-30 00:00:00\",\"cronExpress\":\"00 00 06 * * ?\",\"owner\":\"5250157616116044643\",\"flowName\":\"克州下发模型结果\",\"projectName\":\"syndataexchange\",\"dependentType\":0,\"source\":\"base-biz-commonbase\",\"connection\":null,\"createTime\":\"2019-04-21 18:32:52\",\"createUser\":\"5250157616116044643\",\"modifyTime\":\"2019-04-23 12:09:35\",\"modifyUser\":\"5250157616116044643\",\"priority\":1,\"resourceGroupIdentifier\":\"sys_default\",\"baselineId\":6480,\"cycleType\":0,\"projectId\":32558,\"parentRelations\":[],\"childRelations\":[],\"dqcDescription\":null,\"dqcType\":0,\"isFlowType\":null}",ModelNodeInfo.class);
        ModelNodeInsInfo modelNodeInsInfo = JSON.parseObject("{\"historyId\":null,\"instanceId\":43311100,\"dagId\":14087990,\"instanceType\":0,\"dagType\":3,\"projectId\":32557,\"status\":6,\"opSeq\":null,\"opCode\":61,\"owner\":\"5250157616116044643\",\"bizdate\":\"2017-10-31 00:00:00\",\"gmtdate\":\"2017-11-01 00:00:00\",\"nodeType\":6,\"priority\":1,\"paraValue\":null,\"projectName\":\"syndataexchange_dev\",\"relatedDagId\":null,\"finishTime\":\"2019-04-17 22:25:57\",\"beginWaitTimeTime\":\"2019-04-17 18:52:30\",\"beginWaitResTime\":\"2019-04-17 18:52:30\",\"beginRunningTime\":\"2019-04-17 18:52:36\",\"createTime\":\"2019-04-11 22:09:00\",\"createUser\":\"5250157616116044643\",\"modifyTime\":\"2019-04-17 22:25:57\",\"modifyUser\":\"5250157616116044643\",\"cycleType\":0,\"cycleTime\":\"2017-11-01 00:00:00\",\"dependentType\":0,\"nodeName\":\"nb_tab_med\",\"flowName\":\"乌鲁木齐实时数据电围电查\",\"inGroupId\":1,\"resourceGroupIdentifier\":\"sys_default\",\"baselineId\":6500,\"gateway\":\"12.39.168.113\",\"source\":\"base-biz-commonbase\",\"dqcType\":0,\"dqcDescription\":null,\"dqcInstance\":null,\"isFlowType\":null,\"childNodeRelations\":[],\"parentNodeRelations\":[{\"childDagId\":14087990,\"childInsId\":43311100,\"parentInsId\":43311101,\"relationType\":0,\"createTime\":\"2019-04-11 22:09:00\",\"createUser\":\"5250157616116044643\",\"modifyTime\":\"2019-04-11 22:09:00\",\"modifyUser\":\"5250157616116044643\"}]}",ModelNodeInsInfo.class);

        System.out.println(modelNodeInsInfo);

    }



}
