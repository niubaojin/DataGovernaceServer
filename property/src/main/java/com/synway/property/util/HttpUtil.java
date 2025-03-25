package com.synway.property.util;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 数据接入
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static JSONObject getJSONparam(HttpServletRequest request) {
        JSONObject jsonObject = null;
        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(
                    new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonObject = JSONObject.parseObject(sb.toString());
            logger.info("请求报文：" + jsonObject);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                }
            }
        }
        return jsonObject;
    }
}
