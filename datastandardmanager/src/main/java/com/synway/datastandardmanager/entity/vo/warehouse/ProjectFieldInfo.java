package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectFieldInfo implements Serializable {
    private String projectName;
    private String tableNameEn;
    private String sourceCode;
    private List<FieldInfo> fieldInfos;

}
