package com.synway.governace.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author wangdongwei
 */
public class PinYinUtil {

    /**
     *  获取中文拼音
     * @param chinese
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPySpell(String chinese) throws BadHanyuPinyinOutputFormatCombination {
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
    }
}
