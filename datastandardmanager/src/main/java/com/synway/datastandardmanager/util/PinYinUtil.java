package com.synway.datastandardmanager.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangdongwei
 */
@Slf4j
public class PinYinUtil {

    private static String REGEX_CHINES = "[^\u4e00-\u9fa5]";

    public static String getLockId(String chinese) {
        String lockId;
        try {
            lockId = PinYinUtil.getFirstSpell(chinese);
        } catch (Exception e) {
            lockId = UUIDUtil.getUUID();
            log.error("获取拼音首字母报错：", e);
        }
        return lockId;
    }

    /**
     * 获取中文拼音首字母小写
     *
     * @param chinese
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getFirstSpell(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        if (StringUtils.isBlank(chinese)) {
            return "";
        }
        chinese = chinese.replaceAll(REGEX_CHINES, "");
        if (StringUtils.isBlank(chinese)) {
            return "";
        }
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                if (null != temp) {
                    pybf.append(temp[0].charAt(0));
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }


    /**
     * 获取中文全拼
     *
     * @param chinese
     * @return
     */
    public static String getPySpell(String chinese) {
        try {
            StringBuilder pybf = new StringBuilder();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (null != temp) {
                        pybf.append(temp[0]);
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
            return pybf.toString().replaceAll("\\W", "").trim();
        } catch (Exception e) {
            log.error("获取中文全拼出错：", e);
        }
        return "";
    }

    public static Boolean isContainsPattern(String str, String matcherStr) {
        Pattern p = Pattern.compile(matcherStr);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


}
