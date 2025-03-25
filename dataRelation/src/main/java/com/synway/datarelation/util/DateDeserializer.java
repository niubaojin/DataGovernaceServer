package com.synway.datarelation.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;


import java.io.IOException;
import java.util.Date;
/**
 * @ClassName DateDeserializer
 * @description 这个是时间类型的解析方法
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException  {

        try{
            return DateUtil.parseDate(p.getText(),DateUtil.DEFAULT_PATTERN_DATETIME);
        }catch (Exception e){
            throw new IOException("解析报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }
}
