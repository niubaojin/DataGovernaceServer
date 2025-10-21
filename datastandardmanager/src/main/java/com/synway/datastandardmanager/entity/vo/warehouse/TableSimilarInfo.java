package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data
public class TableSimilarInfo implements Serializable {
    private String resId;
    private String projectName;
    private String tableNameEn;
    private String protocolCode;
    private String objectName;
    private double score;
    private String matchRate;
    private String sourceMatchRate;
    private String standardMatchRate;
    private String matchInfo;
    private Date updateTime;
    private MappingSimilarInfo mappingSimilarInfo;
    /**
     * 探查ID
     */
    private String detectedId;
    /**
     * 源数据名称
     */
    private String sourceName;

}
