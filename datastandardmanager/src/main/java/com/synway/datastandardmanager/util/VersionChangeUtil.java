//package com.synway.datastandardmanager.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.math.BigDecimal;
//
///**
// * @author obito
// * @version 1.0
// * @date
// */
//@Slf4j
//public class VersionChangeUtil {
//
//    public static String getVersion(String oldVersion){
//        try{
//            Double versions = Double.valueOf(oldVersion);
//            BigDecimal bigDecimalVersions = BigDecimal.valueOf(versions);
//            String newVersions = bigDecimalVersions.add(new BigDecimal("0.1")).toString();
//            return newVersions;
//        }catch(Exception e){
//            log.error("版本号中携带中文:"+oldVersion);
//        }
//        return oldVersion;
//    }
//}
