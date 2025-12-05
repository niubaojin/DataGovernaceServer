package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.entity.pojo.DsmStandardTableCreatedEntity;
import lombok.Data;

import java.util.List;

/**
 * @author nbj
 * @date 2025年8月8日14:23:18
 */
@Data
public class CreateTableInfoDTO {
    private String tableId;                         // tableId
    private String status;                          // 状态(0:初始化;1:审批中;2:退回;3:终止)
    private String userId;                          // 用户id
    private String localIp;                         // 浏览器上的ip地址
    private String approvalId;                      // 审批信息标识
    private boolean createdTableIsPartition;        // 是否分区
    private DsmStandardTableCreatedEntity createdTableData;
    private List<CreateTableColumnDTO> columnList;
}
