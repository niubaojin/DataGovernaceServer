package com.synway.dataoperations.util;


import cn.hutool.crypto.SmUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

public class CryptoUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    /**
     * sm3进行签名
     *
     * @param srcStr 原文
     * @return String
     */
    public static String encryptHexSm3(String srcStr) {
        return SmUtil.sm3().digestHex(srcStr);
    }

    private static List<Map<String,String>> getTestData(){
        List<Map<String,String>>logContents = new ArrayList<>();
        Map<String,String> m = new HashMap<String,String>();
        m.put("numId","asdfasdfasdfasdf");
        m.put("userId","1");
        m.put("userName","刘广");
        logContents.add(m);
        return logContents;
    }

    public static void main(String[] args) {
        //注意：字段顺序要按照 文档中的字段顺序排列
        LinkedHashMap<String,Object> map = new LinkedHashMap();
        map.put("sysId","");
        map.put("sendId","1");
        map.put("logType","1");
        map.put("subLogType","101");
        map.put("logContents", getTestData());
        map.put("appSecret ", "asdfsdfsdfhplkfgdhkjaoijh");
        StringBuilder singStr = new StringBuilder();
        logger.info("_________________准备计算SM3code__________________");
        for (String key :map.keySet()) {
            logger.info("本次参与计算的key为：{}",key);
            singStr.append(map.get(key)).append("&");
        }
        String substring = singStr.substring(0, singStr.length() - 1);
        logger.info("计算checkSum拼接的字符串为：{}",substring);
        System.out.println(CryptoUtils.encryptHexSm3(substring));
    }


    public static String encode(Map<String,Object> dataMap){
        StringBuilder singStr = new StringBuilder();
        for (String key :dataMap.keySet()) {
            logger.info("本次参与计算的key为：{}",key);
            singStr.append(dataMap.get(key)).append("&");
        }
        String substring = singStr.substring(0, singStr.length() - 1);
        return CryptoUtils.encryptHexSm3(substring);
    }


    public static String httpsRequest(String url,LinkedHashMap<String,Object> map,Class<?> returnType){
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(map), ContentType.APPLICATION_JSON));
        RequestConfig defaultConfig = RequestConfig.custom().setSocketTimeout(-1).setConnectTimeout(30000).
                setConnectionRequestTimeout(3 * 60 * 60 * 1000).build();

        BufferedReader br = null;
        InputStream inputStream = null;
        CloseableHttpClient httpclient = null;
        StringBuffer stb = new StringBuffer();
        try {
            httpclient = getHttpClinet(defaultConfig);
            CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost);
            inputStream = closeableHttpResponse.getEntity().getContent();
            br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";

            while ((line = br.readLine()) != null) {
                stb.append(line);
            }
            logger.info("发送审计日志操作成功,返回日志为：[{}]",stb);
        }catch (Exception ex){

            logger.error("发送审计日志操作失败，错误信息为:",ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (br != null) {
                    br.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (Exception e) {
                logger.error("在关闭请求流时出错，错误信息为:",e);
            }
        }
        return stb.toString();
    }


    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        return sslContext;
    }

    public static CloseableHttpClient getHttpClinet(RequestConfig requestConfig) throws Exception {
        SSLContext sslContext = createIgnoreVerifySSL();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, (HostnameVerifier) NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory((LayeredConnectionSocketFactory) sslConnectionSocketFactory).build();
        return httpClient;
    }

}
