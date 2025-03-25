package com.synway.property.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author 数据接入
 */
public class DataCheck {

    /**
     * 环比率
     * @param dec1
     * @param dec2
     * @param scale
     * @return
     */
    public static double divideSequential(BigInteger dec1, BigInteger dec2, int scale){
        BigInteger zero = new BigInteger("0");
        if(dec1.compareTo(zero) == 0 && dec2.compareTo(zero) == 0){
            return new BigDecimal(0).setScale(2).doubleValue();
        }else if(dec1.compareTo(zero) == 0){
            return new BigDecimal(-100).setScale(2).doubleValue();
        }else if(dec2.compareTo(zero) == 0){
            return new BigDecimal(100).setScale(2).doubleValue();
        }
        BigDecimal decimal1 = new BigDecimal(dec1);
        BigDecimal decimal2 = new BigDecimal(dec2);
        double value = (decimal1.subtract(decimal2)).divide(decimal2,scale,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)).doubleValue();
        return value;
    }

    public static void main(String[] args) {
//        System.out.println(divideSequential(0,10,4));
    }
}
