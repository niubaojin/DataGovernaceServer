package com.synway.datastandardmanager.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 公共util
 */
@Slf4j
public class CommonUtil {

    // 判断列表是否为空
    public static boolean checkArrayIsNull(List<?> array){
        return array == null || array.isEmpty() || array.size() == 0;
    }

}
