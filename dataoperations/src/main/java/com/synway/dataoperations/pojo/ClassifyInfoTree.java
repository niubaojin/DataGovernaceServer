package com.synway.dataoperations.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ClassifyInfoTree {
    private String value;
    private String label;
    private String tableId;
    private List<ClassifyInfoTree> children;
}
