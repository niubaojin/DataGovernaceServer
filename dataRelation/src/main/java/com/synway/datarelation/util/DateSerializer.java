package com.synway.datarelation.util;

import com.synway.datarelation.util.DateUtil;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Date;
/**
 * @ClassName DateSerializer
 * @description 这个是时间类型的 Serializer
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
public class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String formatter = DateUtil.formatDate(date,DateUtil.DEFAULT_PATTERN_DATETIME);
        jsonGenerator.writeString(formatter);
    }
}
