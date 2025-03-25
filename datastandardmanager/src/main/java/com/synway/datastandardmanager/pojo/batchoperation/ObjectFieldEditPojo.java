package com.synway.datastandardmanager.pojo.batchoperation;

import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 字段可用状态、字段分类、敏感度分类、是否比对、是否订阅、是否查询。
 * 标准字段的批量操作
 * @author wangdongwei
 * @date 2021/7/21 10:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectFieldEditPojo implements Serializable {


    private static final long serialVersionUID = -7656858916829927715L;


    /**
     * 表tableId
     */
    @NotNull(message = "表tableId不能为空")
    private String tableId;

    /**
     * 表objectId
     */
    @NotNull(message = "表objectId不能为空")
    private Long objectId;

    /**
     * 需要更新的建表字段名称
     */
    @NotNull(message = "需要更新的建表字段不能为空")
    private List<String> columnList;


    /**
     * 表字段是否可用
     * 1 代表可用
     * 0 代表不可用
     * 2代表临时可用
     * 3代表ads可用，odps不可用
     */
    @ListValue(vals = {"0","1","2","3"},message = "[表字段是否可用]的值不能为空")
    private String columnNameState = "1";

    /**
     * 字段分类代码值
     */
    @NotNull(message = "字段分类代码值不能为空")
    private String fieldClassId = "";

    /**
     * 敏感度分类代码值
     */
    @NotNull(message = "敏感度分类代码值不能为空")
    private String sensitivityLevel = "";

    /**
     * 是否可查询  1：可查询  0：不可以查询
     */
    @NotNull
    @ListValue(vals = {"0","1"},message = "[是否查询]的值必须是[是/否]")
    private String query;

    /**
     * 是否订阅 1：可订阅   0：不可订阅
     */
    @NotNull
    @ListValue(vals = {"0","1"},message = "[是否订阅]的值必须是[是/否]")
    private String subscribe;




}
