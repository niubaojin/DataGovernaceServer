package com.synway.governace.pojo.largeScreen.ForXJ;

import lombok.Data;

/**
 * 告警数据
 */

@Data
public class WarnData {
    String warnName;    // 告警任务名称
    String warnContent; // 告警内容（统一展示：执行失败）
    String warnTime;    // 告警时间
}
