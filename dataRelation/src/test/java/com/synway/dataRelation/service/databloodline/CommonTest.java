//package com.synway.dataRelation.service.databloodline;
//
//import org.junit.Test;
//
//public class CommonTest {
//
//    @Test
//    public void xxx(){
//
//        String oldProjectName = "sys_code_生产环境";
//        String useHeatProjectName = "all";
//        boolean needHandleUseHeat = false;
//        //20230117 根据配置项 useHeatProjectName 和 数据的projectName信息 来判断是否进行热点逻辑处理。
//        if("all".equalsIgnoreCase(useHeatProjectName)){
//            needHandleUseHeat = true;
//        }
//        if(oldProjectName.contains(useHeatProjectName)){
//            needHandleUseHeat = true;
//        }
//        if(!needHandleUseHeat){
//            System.out.println("我不需要处理");
//        }else {
//            System.out.println("我需要处理");
//        }
//    }
//}
