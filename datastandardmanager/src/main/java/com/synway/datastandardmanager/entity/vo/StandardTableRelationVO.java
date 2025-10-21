package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

/**
 * 分级分类发生变化，目前只有数据组织分类
 */
@Data
public class StandardTableRelationVO {
    private String mainClassifyId = "";
    private String mainClassifyCh = "数据组织分类";
    private String primaryClassifyId;              // 一级分类id号
    private String primaryClassifyCh;              // 一级分类中文名
    private String secondaryClassifyId;            // 二级分类id号
    private String secondaryClassifyCh;            // 二级分类中文名
    private String threeClassifyId;                // 三级分类id号
    private String threeClassifyCh;                // 三级分类中文名
    private String tableId;                        // 表id
    private String tableNameEn;                    //表英文名
    private String tableNameCh;                    //表中文名
    private String newTableProject = "";
    private String newTableNameEn = "";

}
