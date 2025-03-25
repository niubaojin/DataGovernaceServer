package com.synway.property.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author majia
 * @version 1.0
 * @date 2021/3/19 15:33
 */
public class NumUtil {

    public static String handleNumWithUnit(String data){
        if(StringUtils.isBlank(data) || data.equals("0")){
            return data;
        }
        String temp = data.trim();
        String unit = temp.substring(temp.length() - 2);
        String num = temp.substring(0, temp.length() - 2);

        if ("PB".equalsIgnoreCase(unit)){
            return String.format("%.2f",Double.parseDouble(num) * 1024 * 1024);
        }else if("TB".equalsIgnoreCase(unit)){
            return String.format("%.2f",Double.parseDouble(num) * 1024);
        }else if("GB".equalsIgnoreCase(unit)){
            return num;
        }else if("MB".equalsIgnoreCase(unit)){
            return String.format("%.2f",Double.parseDouble(num)/1024);
        }
        return data;
    }
}
