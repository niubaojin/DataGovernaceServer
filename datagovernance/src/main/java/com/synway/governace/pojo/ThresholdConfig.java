package com.synway.governace.pojo;

import lombok.Data;

@Data
public class ThresholdConfig {
    private String id;
    private String parent_id;
    private String name;
    private String logical_judgment;
    private String threshold_value;
    private String tree_type;
    private String is_active;
    private String page_url;
}
