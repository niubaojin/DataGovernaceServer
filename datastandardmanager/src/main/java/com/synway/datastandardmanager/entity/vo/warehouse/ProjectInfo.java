package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectInfo implements Serializable {
    private String projectName;
    private String comment;
    private List<TableInfo> tableInfos;

}
