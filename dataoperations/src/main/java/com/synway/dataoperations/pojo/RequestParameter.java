package com.synway.dataoperations.pojo;

import lombok.Data;

@Data
public class RequestParameter {
    // 告警中心
    private String searchTime;          // 查询时间
    private String searchName;          // 查询名称
    private String alarmModule;         // 告警模块
    private String alarmStatus;         // 告警状态
    private String alarmStatusFilter;   // 告警状态筛选
    private String alarmModuleFilter;   // 告警模块筛选

    // 治理跟踪
    private String linkFilter;          // 环节筛选
    private String sponsorFilter;       // 发起人员筛选
    private String managerFilter;       // 治理人员筛选
    // 治理审核小对话框
    private String id;                  // 主键id
    private String title;               // 治理操作/审核操作
    private String pass;                // 通过/不通过
    private String conclusion;          // 结论
    private String status;              // 跟踪状态

    private Integer pageNum;
    private Integer pageSize;
    private String sortName;
    private String sortBy;

}
