package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.valid.ListValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/15 9:40
 */
@Data
public class BatchOperationDTO implements Serializable {

    private static final long serialVersionUID = 7398549734703339314L;

    /*批量更新表的使用状态*/
    @ListValue(vals = {"0", "1"}, message = "status的值必须是[0/1]")
    private String status;              // 状态为 1：启用  0:停用

    @NotNull(message = "需要修改的表tableId不能为空")
    private List<String> tableIdList;   // 需要修改的标准表中  tableId信息

    /*批量设置框，对获取方式、组织分类、来源分类、资源标签等分类属性进行批量修改*/
    @NotNull
    private String updater = "";        // 更新人
    @ListValue(vals = {"01", "02", "03", "04"}, message = "hqfsList里的值必须是[01/02/03/04]这几种")
    private String hqfs;                // 数据获取方式的码表，01：侦控，02：管控，03：管理，04：公开
    private String sourceClassIds = ""; // 数据来源分类的ids 2个之间用英文逗号分隔 如果只有一个不需要逗号
    private String classIds = "";       // 数据组织分类的ids
    private String sjzybq1 = "";        // 数据资源标签1
    private String sjzybq2 = "";        // 数据资源标签2
    private String sjzybq3 = "";        // 数据资源标签3
    private String sjzybq4 = "";        // 数据资源标签4
    private String sjzybq5 = "";        // 数据资源标签5
    private String sjzybq6 = "";        // 数据资源标签6
    private String sjzzyjfl = "";       // 数据组织一级分类 SJZZYJFL
    private String sjzzejfl = "";       // 数据组织二级分类
    private String sjzylylx = "";       // 数据资源来源类型分类  SJZYLYLX

    /*标准字段的批量操作*/
    @NotNull(message = "表tableId不能为空")
    private String tableId;             // 表tableId
    @NotNull(message = "表objectId不能为空")
    private Long objectId;              // 表objectId
    @NotNull(message = "需要更新的建表字段不能为空")
    private List<String> columnList;    // 需要更新的建表字段名称
    @ListValue(vals = {"0", "1", "2", "3"}, message = "[表字段是否可用]的值不能为空")
    private String columnNameState = "1";// 表字段是否可用，0：代表不可用，1：代表可用，2：代表临时可用，3：代表ads可用、odps不可用
    @NotNull(message = "字段分类代码值不能为空")
    private String fieldClassId = "";   // 字段分类代码值
    @NotNull(message = "敏感度分类代码值不能为空")
    private String sensitivityLevel = "";// 敏感度分类代码值
    @NotNull
    @ListValue(vals = {"0", "1"}, message = "[是否查询]的值必须是[是/否]")
    private String query;               // 是否可查询，1：可查询，0：不可以查询
    @NotNull
    @ListValue(vals = {"0", "1"}, message = "[是否订阅]的值必须是[是/否]")
    private String subscribe;           // 是否订阅，1：可订阅，0：不可订阅

}
