package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.entity.vo.ReceiveTagVO;
import com.synway.datastandardmanager.valid.ListValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 数据集管理页面入参
 * @author wangdongwei
 * @date 2020/11/27 15:59
 */
@Data
public class DataSetManageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final  String CREATE_TIME_TYPE="createTimeType";
    public static final  String UPDATE_TIME_TYPE="updateTimeType";
    public static final  String MORE_THAN="大于";
    public static final  String LESS_THAN="小于";
    public static final  String EQUAL="等于";

    /**
     * 所有选择的  数据组织分类/数据来源分类的 分类信息
     * [{type: "organizationClassify", label: "公安执法与执勤数据(142)", mainClassify: "dataOrganizationClassify"
     * ,"primaryClassifyCh":"原始库","secondaryClassifyCh":"公安执法与执勤数据"，“type“："organizationClassify"}]
     */
    private List<ReceiveTagVO> classifyTags;            //数据组织分类/数据来源分类的 分类信息
    private List<ReceiveTagVO> usingTags;               //资源状态的 筛选内容  启用/停用
    private String sortOrder = "desc";                  //排序的相关参数，字段顺序desc/asc
    private String sort;                                //字段名称
    private String tableId;                             //表协议id
    private String searchCode;                          //搜索框中输入的查询内容 like
    private String timeTypeSummary;                     //createTimeType:创建时间，updateTimeType:最后更新时间
    private String startTimeText;                       //起始时间：2020-10-01
    private String endTimeText;                         //截止时间：2020-10-02

    private List<String> dataSourceFilter;              //应用系统的筛选内容  这个是应用系统中文名
    private List<String> dataOrganizationClassifyFilter;//数据组织分类的筛选
    private List<String> dataSourceClassifyFilter;      //数据来源分类的筛选
    private List<String> objectStatesFilter;            //数据状态的筛选
    private List<String> creatorFilter;                 //创建人的筛选
    private List<String> updaterFilter;                 //最后修改人的筛选

    //已建物理表的条件  等于/大于/小于
    @NotNull
    @ListValue(vals = {"等于","大于","小于"},message = " createTableCondition的值必须是[等于/大于/小于]")
    private String createTableCondition;

    //已建物理表的数量  为空时表示不使用这个查询条件
    @Pattern(regexp = "[1-9]\\d*|0|\\s*" ,message = " 已建物理表的数量必须是正整数、0或空字符串")
    private String createTableTotal;

}
