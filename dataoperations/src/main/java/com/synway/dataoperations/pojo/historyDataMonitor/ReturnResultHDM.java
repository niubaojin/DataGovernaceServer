package com.synway.dataoperations.pojo.historyDataMonitor;

import com.alibaba.fastjson.JSONArray;
import com.synway.dataoperations.pojo.ClassifyInfoTree;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReturnResultHDM {
    private List<ClassifyInfoTree> leftTree;        // 左侧树
    private BigDecimal yesterdaySum;                // 昨日数据量
    private int yesterdayDataCategory;              // 昨日数据种类
    private String yesterdayLinkRelativeRatio;      // 昨日数据环比
    private String yesterdayFluctuateRate;          // 昨日数据抖动
    private JSONArray areaMenu;                     // 区域下拉菜单
    private JSONArray operatorMenu;                 // 运行商下拉菜单
    private JSONArray networkTypeMenu;              // 网络类型下拉菜单
    private List<DataCountTrend> dataCountTrends;   // 数据量趋势图
    private DataAreaCount dataAreaCount;            // 数据区域分布
    private List<DataClass> dataClass;              // 数据种类
    private List<DataClass> eventClass;             // 事件种类
    private List<DataClass> appClass;               // 应用种类
}
