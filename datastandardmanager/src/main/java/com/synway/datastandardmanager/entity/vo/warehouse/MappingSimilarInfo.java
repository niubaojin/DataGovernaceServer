package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class MappingSimilarInfo implements Serializable {
    private String resId;
    private String projectName;
    private String tableNameEn;
    private String protocolCode;
    private String sourceFields;
    private List<FieldInfo> sourceFieldList;
    private String targetFields;
    private List<FieldInfo> targetFieldList;
    private String sourceTargetMapping;
    private String sourceName;
    private String targetName;
    private Date updateTime;

}

