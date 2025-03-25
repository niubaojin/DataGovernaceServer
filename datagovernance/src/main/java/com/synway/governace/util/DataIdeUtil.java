package com.synway.governace.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author
 * @date 2019/4/22 8:53
 */
@Component
public class DataIdeUtil {


    private Logger logger = Logger.getLogger(DataIdeUtil.class);
    private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;

    /**
     * 拼接查询字段为url请求地址字符串
     * @param timestamp
     * @param params
     * @return
     */
    private String getQueryParam(String timestamp,Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        String baseKey = env.getProperty("baseKey");
        //固定需要的请求参数
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
                System.err.println("根据basekey,timestamp生成模块需要的signature失败:" + e);
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
        } catch (NoSuchAlgorithmException e) {
            System.err.println("根据basekey,timestamp生成模块需要的signature失败:" + e);
        } catch (UnsupportedEncodingException e) {
            System.err.println("根据basekey,timestamp生成模块需要的signature失败:" + e);
        }
        String encode = "";
        if(md!=null){
            encode = byteArrayToHexString(md.digest());
        }
        return encode;
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
        if (n < 0) {
            n = 256 + n;
        }
        return HEX_DIGITS[n / 16] + HEX_DIGITS[n % 16];
    }
}
