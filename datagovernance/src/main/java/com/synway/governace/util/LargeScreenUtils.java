package com.synway.governace.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/15 18:27
 */
@Slf4j
public class LargeScreenUtils {
    private static final BigInteger TEN_MILLION = BigInteger.valueOf(10000000);
    private static final BigInteger TEN_THOUSAND = BigInteger.valueOf(10000);
    private static final BigInteger BILLION = BigInteger.valueOf(100000000);
    private static final DecimalFormat TEN_MILLION_FORMAT = new DecimalFormat("00000000");
    private static final DecimalFormat TEN_THOUSAND_FORMAT = new DecimalFormat("0000");
    /**
     * 如果数据有千万级别，则使用 亿条 的单位 ，
     * 如果不能达到千万级别并且已经达到了万，则单位使用万条
     * 如果是万以下，则使用条
     * @param num
     * @return
     */
    public static String getNumStrConversion(BigInteger num){
        StringBuilder str = new StringBuilder();
        // 可以使用  亿条的单位
        if(num.subtract(TEN_MILLION).compareTo(BigInteger.ZERO)>=0){
            BigInteger[] d = num.divideAndRemainder(BILLION);
            String dStr = TEN_MILLION_FORMAT.format(d[1]);
            return str.append(d[0]).append(".").append((dStr), 0, 2).append("亿条").toString();
        }else if(num.subtract(TEN_THOUSAND).compareTo(BigInteger.ZERO)>=0){
            BigInteger[] d = num.divideAndRemainder(TEN_THOUSAND);
            String dStr = TEN_THOUSAND_FORMAT.format(d[1]);
            return str.append(d[0]).append(".").append((dStr), 0, 2).append("万条").toString();
        }else{
            return str.append(num).append("条").toString();
        }
    }

    public static String getNumStrConversionStr(long num){
        try{
            BigInteger data = BigInteger.valueOf(num);
            return getNumStrConversion(data);
        }catch (Exception e){
            log.error("number转string失败：", e);
            return "0条";
        }
    }
}
