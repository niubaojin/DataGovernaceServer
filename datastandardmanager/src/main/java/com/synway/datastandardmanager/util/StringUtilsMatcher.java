package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.service.impl.DbManageServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangdongwei
 */
public class StringUtilsMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtilsMatcher.class);

    private  static final String[] parsePatterns = {"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd HH:mm","yyyy/MM/dd HH:mm","yyyyMMdd","yyyy/MM/dd","yyyyMMddHHmmss"};

    public static Boolean isContainsPattern(String str,String matcherStr){

        Pattern p = Pattern.compile(matcherStr);
        Matcher m = p.matcher(str);
        if(m.find()){
            return true;
        }
        return false;
    }

    /**
     * 判断这个时间是否为时间类型的字符串
     * @param str
     * @return
     */
    public static boolean isParseDate(String str){
        try{
            if(StringUtils.isBlank(str)){
                return false;
            }
            Date date = DateUtils.parseDate(str,parsePatterns);
            return true;
        }catch (Exception e){
            LOGGER.error("判断这个字符串是否为时间字符串报错："+e.getMessage());
            return false;
        }
    }

//    public static String numberToLetter(int num){
//        if(num <= 0){
//            return "A";
//        }
//        String letter = "";
//        num--;
//        do{
//            if(letter.length() >0){
//                num--;
//            }
//            letter = ((char) (num %26 +(int) 'A'))+letter;
//            num = (int) ((num - num %26) /26);
//        }while(num >0);
//        return letter;
//    }

    /**
     * 检查是否为 A-Z的字母
     * @param str
     * @return
     */
//    public static boolean checkFirstIsAZ(String str){
//        if(StringUtils.isBlank(str)){
//            return false;
//        }
//        char c = str.charAt(0);
//        if((c >='A' && c<='Z')){
//            return true;
//        }else{
//            return false;
//        }
//    }

    /**
     * 获取字符串的长度（包括中文）
     * @param str
     * @return
     */
    public static int getStringLength(String str){
        int length = 0;
        if (StringUtils.isBlank(str)) return length;
        for (int i = 0; i < str.length(); i++){
            int ascii = Character.codePointAt(str, i);
            if (ascii >= 0 && ascii <= 55){
                length++;
            }else {
                length+=2;
            }
        }
        return length;
    }

}
