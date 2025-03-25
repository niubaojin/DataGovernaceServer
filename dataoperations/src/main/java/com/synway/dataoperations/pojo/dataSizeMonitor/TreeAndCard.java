package com.synway.dataoperations.pojo.dataSizeMonitor;

import com.synway.dataoperations.pojo.ClassifyInfoTree;
import lombok.Data;

import java.util.List;

/**
 * 左侧树及上方卡片
 */
@Data
public class TreeAndCard {
    private Integer todayDataClassSum;         // 今日数据种类
    private String todayAccessSum;             // 今日接入量
    private String todayAccessAbnormalSum;     // 今日接入异常量
    private String todayHandleSum;             // 今日处理量
    private String todayHandleAbnormalSum;     // 今日处理异常量
    private String todayInsertSum;             // 今日入库量
    private String todayInsertAbnormalSum;     // 今日入库异常量
    private List<ClassifyInfoTree> leftTree;   // 左侧树

}
