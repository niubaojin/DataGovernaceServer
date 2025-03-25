package com.synway.dataoperations.pojo.dataJitterMonitor;

import com.synway.dataoperations.pojo.ClassifyInfoTree;
import lombok.Data;

import java.util.List;

@Data
public class TreeAndCardJitter {
    private Integer todayDataClassSum;      // 今日数据种类
    private String todayAccessSum;          // 今日接入量
    private String todayAccessJitterRate;   // 今日接入抖动率
    private String todayHandleSum;          // 今日处理量
    private String todayHandleJitterRate;   // 今日处理抖动率
    private String todayInsertSum;          // 今日入库量
    private String todayInsertJitterRate;   // 今日入库抖动率
    private List<ClassifyInfoTree> leftTree;// 左侧树

}
