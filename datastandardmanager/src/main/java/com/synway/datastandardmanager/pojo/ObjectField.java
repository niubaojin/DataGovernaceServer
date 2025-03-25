package com.synway.datastandardmanager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表结构定义（OBJECTFIELD）（源数据表字段定义）
 *
 * @author admin
 */
@Data
public class ObjectField implements Serializable {
    private Long objectId;//表对象ID
    private int recno;//表中字段顺序
    @Size(max = 40, message = "[元素编码]长度不能超过40")
    private String fieldId = " ";//元素编码
    @Size(max = 50, message = "[标准列名]长度不能超过40")
    private String fieldName = " ";//标准中数据项英文描述
    @Size(max = 50, message = "[字段中文名]长度不能超过40")
    private String fieldChineseName = " ";//标准中的字段中文名称
    @Size(max = 300, message = "[字段描述]长度不能超过40")
    private String fieldDescribe = " ";//标准中的字段描述
    private String fieldType = " ";//字段类型
    private Integer fieldLen;//字段长度
    private String defaultValue = " ";//字段默认值
    private Integer indexType;//索引类型
    private Integer isIndex;//标准中是否存在索引
    private Integer needValue; //是否必填项
    private Integer isContorl;//是否为布控条件
    private String columnName = " ";//表中的字段名称
    private String memo = " ";//备注
    private Integer tableIndex;//表索引
    private int deleted;//
    private Integer isQuery;//是否可查询
    private Integer columnNameState = 1;//表字段是否可用
    private Integer md5Index;//是否参与MD5运算
    private Integer versions;//
    private String isPrivate = "";//是否标准协议
    private Integer standardRecno;//标准中序列顺序
    private Integer pkRecno;//主键顺序
    private Integer partitionRecno;//分区列标示
    private Integer clustRecno;//聚集列
    private Integer oraShow;//是否近线显示
    private String needv = " ";
    private String fieldtypes = " ";
    private String needvalues = " ";
    private String odpsPattition = " ";
    private String proType = " ";
    private int secretLevel;//字段敏感级别

    private String codeText = " ";//页面显示  代码中文名
    private String codeid = "";  //

    // 在添加字段的页面中，需要新增几个字段 20191023
    //  如果选择的是，则需要获取已经存在的index，
    private Boolean clustRecnoStatus;//是否为聚集列
    private Boolean pkRecnoStatus;//是否为主键列
    private Boolean md5IndexStatus;//是否参与MD5运算

    private String type = " ";    //0:来源字段    1：标准字段  为空时表示是标准字段

    private String createColumnType = "";  //在odps和ads建表时的字段类型
    private int createColumnLen;  // 在odps和ads建表时的字段长度

    // 在建表中presto需要以下几个参数
    /**
     * 是否过滤列,0否 1是
     */
    @NotNull
    private Byte isFilter = 0;

    /**
     * 是否保存source，0否1是
     */
    @NotNull
    private Byte isSource = 0;

    /**
     * 是否存为storefield
     */
    @NotNull
    private Byte isStore = 0;

    /**
     * 是否存为docval
     */
    @NotNull
    private Byte isDocval = 0;

    /**
     * 是否作为业务字段 0否 1是
     */
    @NotNull
    private Byte isRowkey = 1;

    @NotNull
    private Boolean isIndexs = false;

    //  因为需要先将表字段信息插入到页面中，所以需要判断这个字段是已经被修改/新增
    //  0：没有变化   1：新增数据  2：修改数据
    private Byte updateStatus = 0;

    //  20200229 标准表新增了一个字段
    //  是否属于原始库  1：是  0：否  默认值：0
    private int FieldSourceType = 0;

    // 20200914 新增字段分类中文名 字段分类代码值  敏感度分类代码值
    // 字段分类中文名
    private String fieldClassCh = "";
    //  字段分类代码值
    private String fieldClassId = "";
    // 敏感度分类代码值
    private String sensitivityLevelCh = "";
    private String sensitivityLevel = "";
    // 语义类型
    private String sameWordType = "";

    //数据元的内部标识符(用来数据元回限)
    private String label;
    private String synlteFieldMemo;


    //20210802 标准表新增的6个字段
    /**
     * 标准新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "【创建时间】不能为空")
    private Date createTime;

    /**
     * 最新修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "【修改时间】不能为空")
    private Date updateTime;

    /**
     * 标准创用户名称
     */
    @NotNull(message = "【创建人用户名称】不能为空")
    private String creator;

    /**
     * 标准最新修改用户名称
     */
    @NotNull(message = "【修改用户名称】不能为空")
    private String updater;

    /**
     * 大版本号
     */
    private String version0;

    /**
     * 标准表版本主键
     */
    private String objectIdVersion;

    /**
     * 数据要素id
     */
    private String elementId;

    /**
     * 数据要素名称
     */
    private String elementName = "";

    /**
     * 限定词Id
     */
    private String determinerId = "";
    // 限定词名称
    private String determinerName = "";


    /**
     * 202100915 数据安全分级
     */
    private String securityLevel = "";

    /**
     * 202100915 数据安全分级中文
     */
    private String securityLevelCh = "";

    /**
     * 2021.10.27新增的字段
     */
    //内部标识符
    private String gadsjFieldId = "";

    // 数据集管理页面导出导出功能优化，新加字段
    private Integer version;        // 版本号
    private String needValueStr;    // 是否必填：0否，1是
    private String isIndexStr;      // 是否索引：0否，1是
    private String isContorlStr;    // 是否可布控：0否，1是
    private String oraShowStr;      // 是否近线显示：1表示显示 0 不是不显示

    private Integer sjlygcfl;       // 数据来源构成分类（数据项类型）

    // 对账字段标识：1、表示对账标识字段，2、表示对账时间字段
    private String reconciliationType;

}