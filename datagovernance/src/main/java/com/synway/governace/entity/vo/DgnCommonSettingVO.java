package com.synway.governace.entity.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class DgnCommonSettingVO {
    private String id;
    private String parentId;
    private String name;
    private String logicalJudgment;
    private String treeType;
    private String isActive;
    private String pageUrl;
    private JSONObject thresholdValue;
}
