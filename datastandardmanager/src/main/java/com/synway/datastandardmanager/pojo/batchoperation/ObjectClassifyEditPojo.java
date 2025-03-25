package com.synway.datastandardmanager.pojo.batchoperation;

import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/15 16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectClassifyEditPojo implements Serializable {

    private static final long serialVersionUID = -6237964524241430060L;

    /**
     * 更新人
     */
    @NotNull
    private String updater="";

    /**
     * 需要修改的标准表中  tableId信息
     */
    @NotNull(message = "需要修改的表tableId不能为空")
    private List<String> tableIdList;

    /**
     * 数据获取方式 的码表  01：侦控  02：管控 03：管理  04：公开
     */
    @ListValue(vals = {"01","02","03","04"},message = "hqfsList里的值必须是[01/02/03/04]这几种")
    private String hqfs;


    /**
     * 数据来源分类的ids 2个之间用英文逗号分隔 如果只有一个不需要逗号
     */
    private String sourceClassIds="";

    /**
     * 数据组织分类的ids
     */
    private String classIds="";

    /**
     * 数据资源标签1
     */
    private String	sjzybq1 = "";
    /**
     * 数据资源标签2
     */
    private String	sjzybq2	= "";
    /**
     * 数据资源标签3
     */
    private String	sjzybq3	= "";
    /**
     * 数据资源标签4
     */
    private String	sjzybq4	= "";
    /**
     * 数据资源标签5
     */
    private String	sjzybq5	= "";
    /**
     * 数据资源标签6
     */
    private String	sjzybq6	= "";


    /**
     * 非前端传递
     * 数据组织一级分类 SJZZYJFL
     */
    private String sjzzyjfl="";
    /**
     * 非前端传递
     * 数据组织二级分类
     */
    private String sjzzejfl="";
    /**
     * 非前端传递
     * 数据资源来源类型分类  SJZYLYLX
     */
    private String sjzylylx="";


}
