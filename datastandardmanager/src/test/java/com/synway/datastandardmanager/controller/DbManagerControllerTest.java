//import java.text.DecimalFormat;
//
////package com.synway.datastandardmanager.controller;
////
////
////import com.synway.datastandardmanager.pojo.Sameword;
////import org.apache.commons.lang3.StringUtils;
////
////import java.lang.reflect.Field;
////import java.util.*;
////import java.util.stream.Collectors;
////
////
//class DbManagerControllerTest {
////
////    public static int singleNumber(int[] nums){
////        int single = 0;
////        for(int num: nums){
////            single ^= num;
////        }
////        return single;
////    }
////
////    public static boolean  canPartition(int[]  nums)  {
////        //计算数组中所有元素的和
////        int  sum  =  0;
////        for  (int  num  :  nums)
////            sum  +=  num;
////        //如果sum是奇数，说明数组不可能分成完全相等的两份
////        if  ((sum  &  1)  ==  1)
////            return  false;
////        //sum除以2
////        int  target  =  sum  >>  1;
////        int  length  =  nums.length;
////        boolean[][]dp  =  new  boolean[length  +  1][target+1];
////        dp[0][0]  =  true;//base  case
////        for  (int  i  =  1;  i  <=  length;  i++)  {
////            for  (int  j  =  1;  j  <=  target;  j++)  {
////                //递推公式
////                if  (j  >=  nums[i  -  1])  {
////                    dp[i][j]  =  (dp[i  -  1][j]  ||  dp[i-1][j  -  nums[i  -  1]]);
////                }  else  {
////                    dp[i][j]  =  dp[i  -  1][j];
////                }
////            }
////        }
////        return  dp[length][target];
////    }
////
////
////
////
//private static String replaceInvaildSymbol(String oldStr){
//    return  oldStr.replaceAll("\\r","").replaceAll("\\n","")
//            .replaceAll("\\t","").replaceAll(",","，");
//}
//
//    public static void main(String[] args) throws Exception{
////        Double.parseDouble(df.format(
////                Double.parseDouble(usedCapacity/bareCapacity))
//        Double dd = 30.70000000001;
//        Double ff = 2.09;
//        DecimalFormat df = new DecimalFormat("0.00");
//
//        System.out.println(String.format("%.2f",Double.parseDouble(df.format(ff/dd))));
//
////        System.out.println(aaa.replaceAll("\\+","").replaceAll("@",""));
////        List<Sameword> list = new ArrayList<>();
////        Sameword sameword = new Sameword();
////        sameword.setId("1111");
////        list.add(sameword);
////        List<Object> list1 = new ArrayList<>(list);
////        List<Object> list2 = getList(list1,"id");
////        List<Sameword> list3 = list2.stream().map(d -> (Sameword) d).collect(Collectors.toList());
////        System.out.println(list3);
////        int[]  nums = new int[]{1,5,11,5};
////
////        canPartition(nums);
//    }
//////
//////    public String LCS(String str1,String str2){
//////        // 记录最长公共子串的长度
//////        int maxLength = 0;
//////        // 记录最长公共子串最后一个元素在str1中的位置
//////        int maxLastIndex = 0;
//////        int[][] dp = new int[][];
//////        return "";
//////    }
////
////    private static List<Object> getList(List<Object> list , String params) throws Exception{
////        Field field =  list.get(0).getClass().getDeclaredField(params);
////        field.setAccessible(true);
////        String aa = (String) field.get(list.get(0));
////        System.out.println(aa);
////        return list;
//////        list.parallelStream().filter( d->{
//////            try{
//////                Field field =  d.getClass().getField(params);
//////                field.toString()
//////            }catch (Exception e){
//////
//////            }
//////
//////        })
////
////    }
////
////
////    private List<Object> filterSynlteFieldVersionList(List<Object> list,VersionManageParameter parameter,
////                                                      String filterName){
////        list.parallelStream().filter(d -> {
////            try {
////                Field field =  d.getClass().getDeclaredField(filterName);
////                field.setAccessible(true);
////                Date dbTime = (Date) field.get(d);
////                if(StringUtils.isNotBlank(parameter.getStartTimeText())){
////                    if(StringUtils.isBlank(dbTime.toString())){
////                        return false;
////                    }else if(dbTime.before(DateUtil.parseDate(parameter.getStartTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
////                        return false;
////                    }
////                }
////                if(StringUtils.isNotBlank(parameter.getEndTimeText())){
////                    if(StringUtils.isBlank(dbTime.toString())){
////                        return false;
////                    }else if(dbTime.after(DateUtil.parseDate(parameter.getEndTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
////                        return false;
////                    }
////                }
////                return true;
////            } catch (Exception e) {
////                logger.error("数据筛选报错"+e.getMessage());
////                return false;
////            }
////        }).collect(Collectors.toList());
////        return list;
////    }
//}