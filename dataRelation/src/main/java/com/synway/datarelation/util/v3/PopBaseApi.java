package com.synway.datarelation.util.v3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRpcRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.synway.datarelation.pojo.monitor.pop.PopBussiness;
import com.synway.datarelation.pojo.monitor.pop.PopResponseBody;
import com.synway.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/20 13:57
 */
@Component
public class PopBaseApi {
    private Logger logger = LoggerFactory.getLogger(PopBaseApi.class);
    @Autowired()private Environment env;

    private IAcsClient client;

    public String getBizName(String bizid,String appid){
        if(client==null) {
            String popEndpoint = env.getProperty("dataworks.pop.endpoint");
            String popEndpointName =  env.getProperty("dataworks.pop.endpointName");
            String accessId = env.getProperty("dataworks.pop.accessId");
            String accessKey = env.getProperty("dataworks.pop.accesskey");
            String regionId =  env.getProperty("dataworks.pop.regionId");
            String popProduct = env.getProperty("dataworks.pop.product");

            try {
                DefaultProfile.addEndpoint(popEndpointName, regionId, popProduct, popEndpoint);
            } catch (ClientException e) {
                logger.error("popBaseApi配置报错" + ExceptionUtil.getExceptionTrace(e));
            }
            IClientProfile profile  = DefaultProfile.getProfile(regionId, accessId, accessKey);
            logger.info("已加载pop客户端");
            client = new DefaultAcsClient(profile);
        }
        String result ="";
        CommonRpcRequest commonRpcRequest = new CommonRpcRequest(env.getProperty("dataworks.pop.product"), env.getProperty("dataworks.pop.version"), "SearchBusiness");
        commonRpcRequest.setSysMethod(MethodType.POST);
        commonRpcRequest.putQueryParameter("BaseId", env.getRequiredProperty("dataworks.http.baseId").substring(1));
        commonRpcRequest.putQueryParameter("AccountId", env.getProperty("dataworks.pop.accountId"));
        commonRpcRequest.putQueryParameter("AccountPwd", env.getProperty("dataworks.pop.accountPwd"));
        commonRpcRequest.putQueryParameter("ClientName", env.getProperty("dataworks.pop.clientName"));
        commonRpcRequest.putQueryParameter("TenantId", env.getProperty("dataworks.http.tenantId"));
        commonRpcRequest.putQueryParameter("BizId", bizid);
        commonRpcRequest.putQueryParameter("AppId", appid);
        HttpResponse httpResponse;
        try {
            httpResponse = client.doAction(commonRpcRequest, false, 0);
            result = httpResponse.getHttpContentString();
            PopResponseBody responseBody = JSON.parseObject(result,PopResponseBody.class);
            if("0".equals(responseBody.getReturnCode())) {
                JSONObject returnVal = JSON.parseObject(responseBody.getReturnValue(),JSONObject.class);
                List<PopBussiness> bussinesses = JSON.parseArray(returnVal.getString("ReturnValue"),PopBussiness.class);
                if(bussinesses !=null && bussinesses.size()>0){
                    result = bussinesses.get(0).getBizName();
                }
            }else {
                logger.info(result);
                result = appid+"+"+bizid;
            }
        } catch (ClientException e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return result;
    }

}
