package com.synway.reconciliation.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期参数工具类
 * @author ym
 */
public class DateParamUtil {

    public static String getStartT(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        String d = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        return d;
    }

    public static String getEndT(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        String d = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        return d;
    }

    public static String getStartY(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.add(Calendar.DAY_OF_YEAR,-1);
        String d = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        return d;
    }

    public static int getHourOfDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static String getDayStart(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        String d = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        return d;
    }


    public static String getDayEnd(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        String d = DateUtil.formatDateTime(c.getTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
        return d;
    }

    public static int getDayStartInt(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return (int) (c.getTime().getTime() / 1000);
    }


    public static int getDayEndInt(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return (int) (c.getTime().getTime() / 1000);
    }

}
