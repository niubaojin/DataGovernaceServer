package com.synway.datastandardmanager.entity.vo;

import com.synway.datastandardmanager.entity.vo.warehouse.FieldInfo;
import lombok.Data;

import java.util.List;

/**
 * @description 获取仓库字段返回信息
 */
@Data
public class DetectedFieldInfoVO {
    boolean existTableInfoId;   // 表是否已创建，如果为true则表示此表已创建
    List<FieldInfo> fieldInfos;
}
