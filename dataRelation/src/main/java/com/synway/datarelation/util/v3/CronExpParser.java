package com.synway.datarelation.util.v3;

import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CronExpParser {
    private static Logger logger = LoggerFactory.getLogger(CronExpParser.class);
    /**
     * 解析corn表达式，生成指定日期的时间序列
     *
     * @param cronExpression cron表达式
     * @param cronDate cron解析日期
     * @param result crom解析时间序列
     * @return 解析成功失败
     */
    public static boolean parser(String cronExpression, String cronDate, List<String> result)
    {
        if (cronExpression == null || cronExpression.length() < 1 || cronDate == null || cronDate.length() < 1)
        {
            return false;
        }
        else
        {
            CronExpression exp = null;
            // 初始化cron表达式解析器
            try
            {
                exp = new CronExpression(cronExpression);
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                logger.error(ExceptionUtil.getExceptionTrace(e));
                return false;
            }

            // 定义生成时间范围
            // 定义开始时间，前一天的23点59分59秒
            Calendar c = Calendar.getInstance();
            String sStart = cronDate + " 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dStart = null;
            try
            {
                dStart = sdf.parse(sStart);
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }

            c.setTime(dStart);
            c.add(Calendar.SECOND, -1);
            dStart = c.getTime();

            // 定义结束时间，当天的23点59分59秒
            c.add(Calendar.DATE, 1);
            Date dEnd = c.getTime();

            // 生成时间序列
            Date dd = dStart;
            dd = exp.getNextValidTimeAfter(dd);
            while ((dd.getTime() >= dStart.getTime()) && (dd.getTime() <= dEnd.getTime()))
            {
                result.add(sdf.format(dd));
                dd = exp.getNextValidTimeAfter(dd);
            }
            exp = null;
        }
        return true;
    }

    public static String translateToChinese(String cronExp)
    {
        if (cronExp == null || cronExp.length() < 1)
        {
            return "cron表达式为空";
        }
        CronExpression exp = null;
        // 初始化cron表达式解析器
        try
        {
            exp = new CronExpression(cronExp);
        }
        catch (ParseException e)
        {
            return cronExp;
        }
        String[] tmpCorns = cronExp.split(" ");
        StringBuffer sBuffer = new StringBuffer();
        if(tmpCorns.length == 6)
        {
            //解析月
            if(!"*".equals(tmpCorns[4]))
            {
                sBuffer.append(tmpCorns[4]).append("月");
            }
            else
            {
                sBuffer.append("每月");
            }
            //解析周
            if(!"*".equals(tmpCorns[5]) && !"?".equals(tmpCorns[5]))
            {
                char[] tmpArray =  tmpCorns[5].toCharArray();
                for(char tmp:tmpArray)
                {
                    switch (tmp)
                    {
                        case '1':
                            sBuffer.append("星期天");
                            break;
                        case '2':
                            sBuffer.append("星期一");
                            break;
                        case '3':
                            sBuffer.append("星期二");
                            break;
                        case '4':
                            sBuffer.append("星期三");
                            break;
                        case '5':
                            sBuffer.append("星期四");
                            break;
                        case '6':
                            sBuffer.append("星期五");
                            break;
                        case '7':
                            sBuffer.append("星期六");
                            break;
                        case '-':
                            sBuffer.append("至");
                            break;
                        default:
                            sBuffer.append(tmp);
                            break;
                    }
                }
            }

            //解析日
            if(!"?".equals(tmpCorns[3]))
            {
                if(!"*".equals(tmpCorns[3]))
                {
                    sBuffer.append(tmpCorns[3]).append("日");
                }
                else
                {
                    sBuffer.append("每日");
                }
            }

            //解析时
            if(!"*".equals(tmpCorns[2]))
            {
                sBuffer.append(tmpCorns[2]).append("时");
            }
            else
            {
                sBuffer.append("每时");
            }

            //解析分
            if(!"*".equals(tmpCorns[1]))
            {
                sBuffer.append(tmpCorns[1]).append("分");
            }
            else
            {
                sBuffer.append("每分");
            }

            //解析秒
            if(!"*".equals(tmpCorns[0]))
            {
                sBuffer.append(tmpCorns[0]).append("秒");
            }
            else
            {
                sBuffer.append("每秒");
            }
        }

        return sBuffer.toString();

    }
    static final String XIN="*";
    static final String WENHAO="?";
    static final String DAO="-";
    static final String MEI="/";
    static final String HUO=",";

    public static String descCorn(String cronExp) {
        String[] tmpCorns = cronExp.split(" ");
        StringBuffer sBuffer = new StringBuffer();
        if (tmpCorns.length != 6) {
            throw new RuntimeException("请补全表达式,必须标准的cron表达式才能解析");
        }
        // 解析月
        descMonth(tmpCorns[4], sBuffer);
        // 解析周
        descWeek(tmpCorns[5], sBuffer);

        // 解析日
        descDay(tmpCorns[3], sBuffer);

        // 解析时
        descHour(tmpCorns[2], sBuffer);

        // 解析分
        descMintue(tmpCorns[1], sBuffer);

        // 解析秒
        descSecond(tmpCorns[0], sBuffer);
        return sBuffer.toString();

    }

    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:01:09
     */
    private static void descSecond(String s, StringBuffer sBuffer) {
        String danwei="秒";
        desc(s, sBuffer, danwei);
    }
    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:30
     */
    private static void descWeek(String s, StringBuffer sBuffer) {
        //不解释 太麻烦
    }
    /**
     * 描述
     * @param s
     * @param sBuffer
     * @param danwei
     * @author Norton Lai
     * @created 2019-2-27 下午5:16:19
     */
    private static void desc(String s, StringBuffer sBuffer, String danwei) {
        if ("1/1".equals(s)) {
            s="*";
        }
        if ("0/0".equals(s)) {
            s="0";
        }
        if (XIN.equals(s)) {
            sBuffer.append("每"+danwei);
            return;
        }
        if (WENHAO.equals(s)) {
            return ;
        }
        if (s.contains(HUO)) {
            String[] arr = s.split(HUO);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length()!=0) {
                    sBuffer.append("第"+arr[i]+danwei+"和");
                }
            }
            sBuffer.deleteCharAt(sBuffer.length()-1);
            sBuffer.append("的");
            return;
        }
        if (s.contains(DAO)) {
            String[] arr = s.split(DAO);
            if (arr.length!=2) {
                throw new RuntimeException("表达式错误"+s);
            }
            sBuffer.append("从第"+arr[0]+danwei+"到第"+arr[1]+danwei+"每"+danwei);
            sBuffer.append("的");
            return;
        }

        if (s.contains(MEI)) {
            String[] arr = s.split(MEI);
            if (arr.length!=2) {
                throw new RuntimeException("表达式错误"+s);
            }
            if (arr[0].equals(arr[1])||"0".equals(arr[0])||"*".equals(arr[0])) {
                sBuffer.append("每"+arr[1]+danwei);
            }else {
                sBuffer.append("每"+arr[1]+danwei+"的第"+arr[0]+danwei);
            }
            return;
        }
        sBuffer.append("第"+s+danwei);
    }

    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:01:00
     */
    private static void descMintue(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "分钟");
    }

    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:50
     */
    private static void descHour(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "小时");
    }

    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:39
     */
    private static void descDay(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "天");
    }


    /**
     * 描述
     * @param sBuffer
     * @author Norton Lai
     * @created 2019-2-27 下午5:00:15
     */
    private static void descMonth(String s, StringBuffer sBuffer) {
        desc(s, sBuffer, "月");
    }


    public static String descCronChinese(String cronStr){
        String value = "";
        if(StringUtils.isEmpty(cronStr)){
            return cronStr;
        }
        if(cronStr.indexOf("/") > 0){
            value = CronExpParser.descCorn(cronStr);
        }else{
            value = CronExpParser.translateToChinese(cronStr);
        }
        return value;
    }

    //测试方法
    public static void main(String[] args)
    {
        String CRON_EXPRESSION = "0 */60 * * * ?";
//        String CRON_EXPRESSION = "0 0 1 * * ?";
        // 生成指定日期的CRON时间序列
        String CRON_DATE = "2016-04-26";
        System.out.println(CRON_EXPRESSION);
        System.out.println(CRON_EXPRESSION.indexOf("/"));
        if(CRON_EXPRESSION.indexOf("/") > 0){
            System.out.println(CronExpParser.descCorn(CRON_EXPRESSION));
        }else{
            System.out.println(CronExpParser.translateToChinese(CRON_EXPRESSION));
        }
    }
}
