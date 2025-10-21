package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

/**
 * 数据集管理
 */

@Data
public class DataSetStandardVO {
    String message;                 // 保存结果信息
    String tableId;                 // 保存的tableId
    String approvalInfo = "false";  // 是否审批（功能停用，默认false）
}
