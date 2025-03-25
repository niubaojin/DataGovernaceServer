package com.synway.reconciliation.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 日期时间转换格式工具类
 * @author zhz
 * @since  2009-10-15
 */
public class DateUtil {
private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	public final static String	DEFAULT_PATTERN_DATETIME				= "yyyy-MM-dd HH:mm:ss";

	public final static String	DEFAULT_PATTERN_DATETIME_FULL			= "yyyy-MM-dd HH:mm:ss.SSS";

	public final static String	DEFAULT_PATTERN_DATETIME_SIMPLE			= "yyyyMMddHHmmss";

	public final static String	DEFAULT_PATTERN_DATETIME_SIMPLE_FULL	= "yyyyMMddHHmmssSSS";

	public final static String	DEFAULT_PATTERN_DATE					= "yyyy-MM-dd";

	public final static String	DEFAULT_PATTERN_DATE_SIMPLE			    = "yyyyMMdd";
	
	public final static String	DEFAULT_PATTERN_TIME					= "HH:mm:ss";
	
	public final static String	DEFAULT_PATTERN_TIME_SIMPLE				= "HHmmss";

	public final static int YEAR = 1;
	public final static int MONTH = 2;
	public final static int DAY = 3;
	public final static int HOUR = 4;
	public final static int MINUTE = 5;
	public final static int SECOND = 6;
	public final static int MILLISECOND = 7;

	private final static Pattern P8 = Pattern.compile("^\\d{8}$");
	private final static Pattern P10 = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");
	private final static Pattern P14 = Pattern.compile("^\\d{14}$");
	private final static Pattern P17 = Pattern.compile("^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)0229)((0[0-9])|(1[0-9])|(2[0-3]))[0-5][0-9][0-5][0-9]{1}\\d{3}");
	private final static Pattern P19 = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$");
	private final static Pattern P23 = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}\\.\\d{3}$");

	private final static int LEN4 = 4;
	private final static int LEN8 = 8;
	private final static int LEN10 = 10;
	private final static int LEN14 = 14;
	private final static int LEN17 = 17;
	private final static int LEN19 = 19;
	private final static int LEN23 = 23;

	/**
	 *  获取当前时间
	 */
	public static String getNow(){
		Date date=new Date();
		return formatDateTime(date);
	}

	/**
	 *  获取当前时间 自定义格式
	 */
	public static String getNowFormat(String format){
		Date date=new Date();
		return formatDateTime(date, format);
	}
	
	
	/**
	 *  以整数的形式得到精确到秒的时间
	 */
	public static int getNowByInteger(){
		long date=System.currentTimeMillis()/1000;
		return (int)date;
	}
	/**
	 *  以整数的形式得到精确到秒的时间
	 */
	public static int getDateByInteger(Date date){
		return (int)(date.getTime()/1000);
	}
	
	public static String getDateByInteger(int date){
		Date d=new Date(date*1000L);
		return formatDateTime(d);
	}
	public static String formatToDefaultDateTime(Object obj){
		String result=null;
		if(obj instanceof Date){
			result=DateUtil.formatDateTime((Date)obj);
		}
		return result;
	}
	
	
	/**
	 * 格式化时间串。
	 */
	public static String formatDateTime(Date date) {
		return formatDateTime(date, DEFAULT_PATTERN_DATETIME);
	}

	/**
	 * 格式化时间串。
	 */
	public static String formatDateTime(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化时间串。
	 */
	public static String formatDate(Date date) {
		String s = formatDateTime(date, DEFAULT_PATTERN_DATE);
		return s;
	}
	/**
	 * 格式化时间串。
	 */
	public static String formatDate(Date date, String pattern) {
		String s = formatDateTime(date, pattern);
		return s;
	}
	/**
	 * 格式化时间串。
	 */
	public static String formatTime(Date date) {
		return formatDateTime(date, DEFAULT_PATTERN_TIME);
	}

	/**
	 * 格式化时间串。
	 */
	public static String formatDateTimeFull(Date date) {
		return formatDateTime(date, DEFAULT_PATTERN_DATETIME_FULL);
	}

	/**
	 * 格式化时间串。
	 */
	public static String formatDateTimeSimple(Date date) {
		return formatDateTime(date, DEFAULT_PATTERN_DATETIME_SIMPLE);
	}

	/**
	 * 格式化时间串。
	 */
	public static String formatDateTimeSimpleFull(Date date) {
		return formatDateTime(date, DEFAULT_PATTERN_DATETIME_SIMPLE_FULL);
	}

	/**
	 * 格式化时间串。
	 */
	public static Date parseDateTime(String dateString, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}

	/**
	 * 解析日期格式。
	 */
	public static Date parseDateTime(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN_DATETIME);
		return sdf.parse(dateString);
	}

	/**
	 * 解析日期格式。
	 */
	public static Date parseDate(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN_DATE);
		return sdf.parse(dateString);
	}
	/**
	 * 解析日期格式,以字符串的形式输出
	 */
	public static String parseDateString(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN_DATE);
		Date tempDate = sdf.parse(dateString);
		return formatDate(tempDate,DEFAULT_PATTERN_DATE_SIMPLE);
	}
	
	/**
	 * 解析日期格式。
	 */
	public static Date parseDate(String dateString, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}

	/**
	 * 解析日期格式。
	 */
	public static Date parseTime(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN_TIME);
		return sdf.parse(dateString);
	}

	/**
	 * 前推后推日期。
	 * days可正可负
	 */
	public static Date addDay(Date oldDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	/**
	 * 前推后推日期。
	 * months可正可负
	 */
	public static Date addMonth(Date oldDate, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
	
	/**
	 * 前推后推日期。
	 * years可正可负
	 */	
	public static Date addYear(Date oldDate, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}
	
	/**
	 * 前推后推日期。
	 * hours可正可负
	 */	
	public static Date addHour(Date oldDate, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * 前推后推日期。
	 * minutes可正可负
	 */	
	public static Date addMinute(Date oldDate, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 前推后推日期。
	 * seconds可正可负
	 */	
	public static Date addSecond(Date oldDate, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	/**
	 * 计算时间差，返回毫秒数。
	 * date1-date2 ,in millionseconds
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getSpan(Date date1, Date date2) {
		return date1.getTime() - date2.getTime();
	}

	/**
	 * 计算时间差，返回毫秒数。
	 * now() - date;
	 * @return
	 */
	public static long getSpan(Date date) {
		return getSpan(new Date(), date);
	}
	
	/**
	 * 计算当前日期加n月后的月未日期，如累加后日期不到月未，取前一月月未日期
	 * 2009-12-22 23:59:59  + 2 ：2010-01-31 23:59:59
	 * 2009-12-22 23:59:59  + 1 ：2009-12-31 23:59:59
	 * @author:      lht
	 * @date:        2011-9-23
	 * @param oldDate
	 * @param months
	 * @return
	 * @return:		 Date
	 */
	public static Date getMonthEnd(Date oldDate, int months) {
		//判断旧日期是否是月未
		boolean isMonthEnd=false;
		
		Calendar oldCal = Calendar.getInstance();
		oldCal.setTime(oldDate);
		
		int maxDay=oldCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int maxHour=oldCal.getActualMaximum(Calendar.HOUR_OF_DAY);
		int maxMinute=oldCal.getActualMaximum(Calendar.MINUTE);
		int maxSecond=oldCal.getActualMaximum(Calendar.SECOND);
		if((oldCal.get(Calendar.DAY_OF_MONTH))==maxDay && (oldCal.get(Calendar.HOUR_OF_DAY)== maxHour)
				&& (oldCal.get(Calendar.MINUTE))==maxMinute && (oldCal.get(Calendar.SECOND))==maxSecond){
			isMonthEnd=true;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.MONTH, months);
		Calendar newCal =null;
		if(isMonthEnd){
			newCal = Calendar.getInstance();
			newCal.setTime(cal.getTime());
			newCal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			newCal.set(Calendar.HOUR_OF_DAY, 23);
			newCal.set(Calendar.MINUTE, 59);
			newCal.set(Calendar.SECOND, 59);
		}else{
			newCal = Calendar.getInstance();
			newCal.setTime(cal.getTime());
			newCal.set(Calendar.DAY_OF_MONTH, 1);
			newCal.set(Calendar.HOUR_OF_DAY, 0);
			newCal.set(Calendar.MINUTE, 0);
			newCal.set(Calendar.SECOND, 0);
			newCal.add(Calendar.SECOND, -1);
		}
		return newCal.getTime();
	}
	/**
	 * 取月份的最后一天
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	/**
	 * 取月份的第一天
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getMonthBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 取年的最后一天
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getYearEnd(Date date) {
		Date d = null;
		String dateString=formatDateTime(date, "yyyy")+"1231235959";
		try {
			d=parseDateTime(dateString, DEFAULT_PATTERN_DATETIME_SIMPLE);
		} catch (Exception e) {
			log.error(ExceptionUtil.getExceptionTrace(e));
		}
		return d;
	}
	/**
	 * 取年的第一天
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getYearBegin(Date date) {
		Date d = null;
		String dateString=formatDateTime(date, "yyyy")+"0101000000";
		try {
			d=parseDateTime(dateString, DEFAULT_PATTERN_DATETIME_SIMPLE);
		} catch (Exception e) {
			log.error(ExceptionUtil.getExceptionTrace(e));
		}
		return d;
	}
	/**
	 * 取天的结束时间
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getDayEnd(Date date) {
		Date d = null;
		String dateString=formatDateTime(date, "yyyyMMdd")+"235959";
		try {
			d=parseDateTime(dateString, DEFAULT_PATTERN_DATETIME_SIMPLE);
		} catch (Exception e) {
			log.error(ExceptionUtil.getExceptionTrace(e));
		}
		return d;
	}
	/**
	 * 取天的开始时间
	 * @author:      lht
	 * @date:        2011-10-9
	 * @param date
	 * @return
	 * @return:		 Date
	 */
	public static Date getDayBegin(Date date) {
		Date d = null;
		String dateString=formatDateTime(date, "yyyyMMdd")+"000000";
		try {
			d=parseDateTime(dateString, DEFAULT_PATTERN_DATETIME_SIMPLE);
		} catch (Exception e) {
			log.error(ExceptionUtil.getExceptionTrace(e));
		}
		return d;
	}
	/**
	 * 格式化时间值
	 * <br/>输入：DateUtil.formatTimes((2*86400+3*3600+15*60+55)*1000, DateUtil.MILLISECOND)
	 * <br/>输出：2天3小时15分55秒
	 * @author:      lht
	 * @date:        2015-9-14 下午12:57:02
	 * @param times 时长
	 * @param unit 单位
	 * @return
	 * @return:		 String
	 */
	public static String formatTimes(long times,int unit) {
		String str="";
		long year=0;
		long month=0;
		long day=0;
		long hour=0;
		long minute=0;
		long second=0;
		switch (unit) {
			case YEAR:
				str=times+"年";
				break;
			case MONTH:
				year=Math.abs(times/12);
				month=times%12;
				str=year+"年"+month+"月";
				break;
			case DAY:
				year=Math.abs(times/365);
				month=Math.abs((times%365)/30);
				day=(times%365)%30;
				str=year+"年"+month+"月"+day+"天";
				break;
			case HOUR:
				day=Math.abs(times/24);
				hour=Math.abs(times%24);
				str=day+"天"+hour+"小时";
				break;
			case MINUTE:
				day=Math.abs(times/1440);
				hour=Math.abs((times%1440)/60);
				minute=(times%1440)%60;
				str=day+"天"+hour+"小时"+minute+"分";
				break;
			case SECOND:
				day=Math.abs(times/86400);
				hour=Math.abs((times%86400)/3600);
				minute=Math.abs(((times%86400)%3600)/60);
				second=((times%86400)%3600)%60;
				str=day+"天"+hour+"小时"+minute+"分"+second+"秒";
				break;
			case MILLISECOND:
				times=times/1000;
				day=Math.abs(times/86400);
				hour=Math.abs((times%86400)/3600);
				minute=Math.abs(((times%86400)%3600)/60);
				second=((times%86400)%3600)%60;
				str=day+"天"+hour+"小时"+minute+"分"+second+"秒";
				break;

			default:
				break;
		}
		return str;
	}
	/**
	 * 检查日期字符串格式正确性：yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyyMMddHHmmss,yyyyMMdd
	 * @author:      lht
	 * @date:        2015-12-1 下午07:30:54
	 * @param str
	 * @return
	 * @return:		 String
	 */
	public static String checkDatePattern(String str,int length){
		if(str==null || str.trim().length()==0){
			return null;
		}
		str=str.trim();
		if(str.length()>length || str.length()<length){
			return null;
		}
		String pattern=null;
		if(length==LEN4 && P8.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATE_SIMPLE;
		}
		if(length==LEN10 && P10.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATE;
		}
		if(length==LEN14 && P14.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATETIME_SIMPLE;
		}
		if(length==LEN17 && P17.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATETIME_SIMPLE_FULL;
		}
		if(length==LEN19 && P19.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATETIME;
		}
		if(length==LEN23 && P23.matcher(str).matches()){
			pattern=DEFAULT_PATTERN_DATETIME_FULL;
		}
		return pattern;
	}
	/**
	 * 检查日期时间字符串格式正确性
	 * @author:      lht
	 * @date:        2015-12-1 下午07:30:54
	 * @param str
	 * @return
	 * @return:		 String
	 */
	public static String checkDatePattern(String str){
		if(str==null || str.trim().length()==0){
			return null;
		}
		str=str.trim();
		if(str.length()>LEN23 || str.length()<LEN4){
			return null;
		}
		String pattern=null;
		if(checkDatePattern(str,LEN8)!=null){
			pattern=DEFAULT_PATTERN_DATE_SIMPLE;
		}else if(checkDatePattern(str,LEN10)!=null){
			pattern=DEFAULT_PATTERN_DATE;
		}else if(checkDatePattern(str,LEN14)!=null){
			pattern=DEFAULT_PATTERN_DATETIME_SIMPLE;
		}else if(checkDatePattern(str,LEN17)!=null){
			pattern=DEFAULT_PATTERN_DATETIME_SIMPLE_FULL;
		}else if(checkDatePattern(str,LEN19)!=null){
			pattern=DEFAULT_PATTERN_DATETIME;
		}else if(checkDatePattern(str,LEN23)!=null){
			pattern=DEFAULT_PATTERN_DATETIME_FULL;
		}
		return pattern;
	}
	/**
	 * 2个字符串求时间差（min）
	 * @author:      lht
	 * @date:        2015-12-1 下午07:30:54
	 * @return
	 * @return:		 String
	 */
	public static String timeDifferent(String time1,String time2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startTime1 = null;
		Date endTime2 = null;
		try {
			startTime1 = sdf.parse(time1);
			endTime2 = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}
		long startL = null == startTime1 ? 0L : startTime1.getTime();
		long endTL = null == endTime2 ? 0L : endTime2.getTime();
		String differentMinu = (endTL - startL) / 60000 + "(min)";
		return differentMinu;
	}
	
	/**
	 * 2个字符串求时间差（min）
	 * @author:      lht
	 * @date:        2015-12-1 下午07:30:54
	 * @return
	 * @return:		 String
	 */
	public static long timeDifferentLong(String time1,String time2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startTime1 = null;
		Date endTime2 = null;
		try {
			startTime1 = sdf.parse(time1);
			endTime2 = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}
		long startL = null == startTime1 ? 0L : startTime1.getTime();
		long endTL = null == endTime2 ? 0L : endTime2.getTime();
		long differentMinu = (endTL - startL) / 60000;
		return differentMinu;
	}
	/**
	 * 返回时间秒数
	 * @return String
	 */
	synchronized public static String getDateTime(){
		try {
			DateUtil.class.wait(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return formatDateTime(new Date(),"yyyyMMddHHmmssSSSS");
	}
	
	public static void sleep(long time){
		try{
			Thread.sleep(time);
		}catch(Exception e){
			log.error(e.toString());
		}
	}

	/**
	 * 获取指定日期，如果指定的日期为空，则默认获取当前日期
	 * @param datetimeStr 指定日期
	 * @param pattern 指定日期的格式
	 * @return Date
	 */
	public static Date getDate(String datetimeStr,String pattern){
		Date date=null;
		//如果前端没有设置日期，则默认为今天
		if(datetimeStr==null || datetimeStr==""){
			date=new Date();
		}else{
			try {
				date = DateUtil.parseDate(datetimeStr,pattern);
			}catch (Exception ignore){
			}
		}
		return date;
	}

}
