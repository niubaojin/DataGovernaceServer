package com.synway.datarelation.util.v3;


import com.synway.datarelation.pojo.monitor.DagRunQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.NodeQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskQueryParameters;
import com.synway.datarelation.pojo.datawork.v3.TaskRunQueryParameters;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里大数据平台（3.7版本）api接口的通用方法
 */
@Component
public class DataWorksApi {
    private static Logger logger = LoggerFactory.getLogger(DataWorksApi.class);
    @Autowired
    private Environment env;


    private static String byteArrayToHexString(byte[] byteArray) {
        final char[] HEX_DIGITS= {'0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'};
        String result= null;
        int l= byteArray.length;
        char[] str= new char[l<< 1];
        int k= 0;
        for(int i= 0; i< l; i++) {
            byte byte0= byteArray[i];
            str[k++] = HEX_DIGITS[byte0>>> 4& 0xf];
            str[k++] = HEX_DIGITS[byte0& 0xf];
        }
        result= new String(str);
        return result;
    }

    public static String tokenSign(String param, String salt) throws
            Exception{
        if(StringUtils.isEmpty(param) || StringUtils.isEmpty(salt)) {
            return null;
        }
        String query= URLDecoder.decode(param, "UTF-8");
        String[] pStr= query.trim().split("&");
        Arrays.sort(pStr);
        StringBuilder buf= new StringBuilder();
        for(int i= 0; i< pStr.length; ++i) {
            buf.append(pStr[i]);
            buf.append("&");
        }
        String paramStr= buf.substring(0, buf.length() - 1);
        String sourceStr= salt+ ":" + paramStr+ ":" + salt;
        MessageDigest md= MessageDigest.getInstance("SHA-256");
        byte[] byteArray= md.digest(sourceStr.getBytes("UTF-8"));
        return byteArrayToHexString(byteArray);
    }

    protected static HttpUriRequest createHttpRequest(String baseKey,
                                                      String baseToken, String uri) {
        StringBuilder buf;
        int index= uri.indexOf('?');
        if(index== -1) {
            index= uri.length();
            buf= new StringBuilder(uri.length() + baseKey.length() +
                    13);
            buf.append(uri).append('?');
        } else{
            buf= new StringBuilder(uri.length() + baseKey.length() +
                    13);
            buf.append(uri).append('&');
        }
        buf.append("baseKey=").append(baseKey);
        buf.append("&timestamp=").append(System.currentTimeMillis());
        HttpUriRequest request= new HttpGet(buf.toString());
        String signature;
        try{
            signature= tokenSign(buf.substring(index+ 1), baseToken
            );
        } catch(Exception ex) {
            throw new RuntimeException("error on sign for uri: " + uri
                    , ex);
        }
        request.addHeader("signature", signature);
        return request;
    }

    /**
     * 拼接查询条件
     * @param params
     * @return
     */
    private String getQueryParam(Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        String[] keyNames = params.keySet().toArray(new String[]{});
        String tmpKeyName;
        String tmpValue;
        for (int i = 0; i < keyNames.length; i++) {
            tmpKeyName = keyNames[i];
            try{
                if(StringUtils.isNotEmpty(params.get(tmpKeyName))){
                    if(tmpKeyName.equalsIgnoreCase("createTimeFrom")||
                            tmpKeyName.equalsIgnoreCase("createTimeTo")
                            ||tmpKeyName.equalsIgnoreCase("startRunTimeFrom")
                              ||tmpKeyName.equalsIgnoreCase("startRunTimeTo")){
                        tmpValue = URLEncoder.encode(params.get(tmpKeyName),"utf-8");
                    }else{
                        tmpValue = params.get(tmpKeyName);;
                    }
                }else{
                    tmpValue = params.get(tmpKeyName);
                }
            }catch (Exception e){
                tmpValue = params.get(tmpKeyName);
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            if(StringUtils.isEmpty(tmpValue)||"class".equalsIgnoreCase(tmpKeyName)){
                continue;
            }
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
     * 查询的基本方法
     * @param url
     * @return
     * @throws Exception
     */
    public String queryDataWorksApi(String url) throws Exception{
        HttpClient httpClient= HttpClientBuilder.create().build();
//        String url= "http://baseapi.xxx.com/v1.0/node/prod?executeMethod=SEARCH&searchText=nodeTest";
        String baseKey = env.getProperty("dataworks.http.baseKey");
        String baseToken = env.getProperty("dataworks.http.baseToken");
        String tenantId = env.getProperty("dataworks.http.tenantId");
        String baseId = env.getProperty("dataworks.http.baseId");
        HttpUriRequest request= createHttpRequest(
                baseKey,
                baseToken,
                url
        );
        request.setHeader("tenant_id", tenantId);
        request.setHeader("base_id", baseId);
        logger.info("请求的url信息为："+request.getURI());
        HttpResponse response= httpClient.execute(request);
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder= new StringBuilder();
        try{
            inputStreamReader =new
                    InputStreamReader(response.getEntity().getContent());
            bufferedReader= new BufferedReader(inputStreamReader);
            while(bufferedReader.ready()) {
                String line= bufferedReader.readLine();
                if(line== null) {
                    break;
                }
                stringBuilder.append(line);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(inputStreamReader != null){
                    inputStreamReader.close();
                }
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
        return stringBuilder.toString();
    }



    /**
     * 查询节点的相关信息
     * @return
     * @throws Exception
     */
    public String queryNode(NodeQueryParameters nodeQueryParameters) throws Exception{
        String nodeUrl = env.getProperty("dataworks.http.nodeUrl");
        String url = nodeUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=nodeQueryParameters){
            paramMap = BeanUtils.describe(nodeQueryParameters);
        }
        String queryParamString =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParamString)){
            url = url+"?"+queryParamString;
        }
        return queryDataWorksApi(url);
    }

    /**
     * 查询任务实例信息
     * @return
     * @throws Exception
     */
    public String queryTask(TaskQueryParameters taskQueryParameters) throws Exception{
        try{
            Thread.sleep(3000);
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        String taskUrl = env.getProperty("dataworks.http.taskUrl");
        String urlTask = taskUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=taskQueryParameters){
            paramMap = BeanUtils.describe(taskQueryParameters);
        }
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlTask = urlTask+"?"+queryParam;

        }
        return queryDataWorksApi(urlTask);
    }


    public String getTaskRunLog(TaskRunQueryParameters taskRunQueryParameters) throws Exception{
        String taskUrl = env.getProperty("dataworks.http.taskUrl");
        String urlTask = taskUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=taskRunQueryParameters){
            paramMap = BeanUtils.describe(taskRunQueryParameters);
        }
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlTask = urlTask+"?"+queryParam;
            return queryDataWorksApi(urlTask);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }

    /**
     * 查询节点代码信息
     */
    public String getNodeCode(Long nodeId) throws Exception{
        String nodeUrl = env.getProperty("dataworks.http.nodeUrl");
        String urlNode = nodeUrl;
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("executeMethod","SEARCH_NODE_CODE");
        paramMap.put("cloudUUID",String.valueOf(nodeId));
        paramMap.put("version","");
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlNode = urlNode+"?"+queryParam;
            return queryDataWorksApi(urlNode);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }

    /**
     * 向上查询 按条件分层查询指定Node的父节点
     * @param nodeId
     * @param depth
     * @return
     * @throws Exception
     */
    public String getParentNode(String nodeId,String nodeIds,int depth) throws Exception{
        String nodeUrl = env.getProperty("dataworks.http.nodeUrl");
        String urlNode = nodeUrl;
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("executeMethod","SEARCH_NODE_PARENTS_BY_DEPTH");
        paramMap.put("nodeId",nodeId);
        paramMap.put("nodeIds",nodeIds);
        paramMap.put("depth",String.valueOf(depth));
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlNode = urlNode+"?"+queryParam;
            return queryDataWorksApi(urlNode);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }

    /**
     * 向下查询 按条件分层查询指定Node的子节点
     * @param nodeId
     * @param depth
     * @return
     * @throws Exception
     */
    public String getChildNode(String nodeId,int depth) throws Exception{
        String nodeUrl = env.getProperty("dataworks.http.nodeUrl");
        String urlNode = nodeUrl;
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("executeMethod","SEARCH_NODE_CHILDREN_BY_DEPTH");
        paramMap.put("nodeId",nodeId);
        paramMap.put("depth",String.valueOf(depth));
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlNode = urlNode+"?"+queryParam;
            return queryDataWorksApi(urlNode);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }

    /**
     * 向下查询 按条件分层查询指定Task的子Task
     * @param taskId
     * @param depth
     * @return
     * @throws Exception
     */
    public String getChildTasks(String taskId,int depth) throws Exception{
        String taskUrl = env.getProperty("taskUrl");
        String urlTask = taskUrl;
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("executeMethod","SEARCH_TASK_CHILDREN_BY_DEPTH");
        paramMap.put("taskId",taskId);
        paramMap.put("depth",String.valueOf(depth));
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlTask = urlTask+"?"+queryParam;
            return queryDataWorksApi(urlTask);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }

    public String queryDag(DagRunQueryParameters dagRunQueryParameters) throws Exception {
        String dagUrl = env.getProperty("dataworks.http.dagUrl");
        String urlDag = dagUrl;
        Map<String,String> paramMap = new HashMap<>();
        if(null!=dagRunQueryParameters){
            paramMap = BeanUtils.describe(dagRunQueryParameters);
        }
        String queryParam =getQueryParam(paramMap);
        if(StringUtils.isNotEmpty(queryParam)){
            urlDag = urlDag+"?"+queryParam;
            return queryDataWorksApi(urlDag);
        }else{
            throw new Exception("传入的参数不能为空");
        }
    }
}
