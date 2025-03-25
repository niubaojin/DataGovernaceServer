package com.synway.governace.pojo;

import lombok.Data;

/**
 * 对账概要信息
 */

@Data
public class ReconInfo {
    private String dataTypeCount;   // 数据种类
    private String successCount;    // 对账成功数量
    private String failedCount;     // 对账失败数量
    private String unReconCount;    // 未对账数量
}
