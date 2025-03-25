package com.synway.reconciliation.util;

import java.math.BigDecimal;

/**
 * 字节工具类
 * @author ym
 */
public class ByteUtil {
    public static String bytes2kb(long bytes){
        BigDecimal fileSize = new BigDecimal(bytes);
        BigDecimal megabytes = new BigDecimal(1024*1024);
        float returnValue = fileSize.divide(megabytes,2,BigDecimal.ROUND_UP).floatValue();
        if(returnValue > 1){
            return (returnValue + "MB");
        }
        BigDecimal kilobyte  =new BigDecimal(1024);
        returnValue = fileSize.divide(kilobyte,2,BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "KB");
    }

    public static String bytes2mb(long bytes){
        BigDecimal fileSize = new BigDecimal(bytes);
        BigDecimal megabytes = new BigDecimal(1024*1024);
        float returnValue = fileSize.divide(megabytes,2,BigDecimal.ROUND_HALF_EVEN).floatValue();
         return returnValue + "";
    }
}
