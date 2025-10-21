package com.synway.datastandardmanager.entity.vo.importDownload;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ObjectFieldSheetVO {
    @ExcelIgnore
    private Integer recno;                  //表中字段顺序
    @ExcelProperty(value = "字段名称",index = 1)
    private String columnName = "";         //表中的字段名称
    @Size(max = 50, message = "[字段中文名]长度不能超过40")
    @ExcelProperty(value = "字段描述",index = 2)
    private String fieldChineseName = "";   //标准中的字段中文名称
    @ExcelProperty(value = "字段类型",index = 3)
    private String fieldType = "";          //字段类型
    @ExcelProperty(value = "长度",index = 5)
    private Integer fieldLen;               //字段长度
    @ExcelProperty(value = "允许空",index = 6)
    private String needValueStr;            //是否必填项
    @ExcelIgnore
    private Integer needValue;              //是否必填项
    @Size(max = 40, message = "[元素编码]长度不能超过40")
    @ExcelProperty(value = "数据元编码",index = 8)
    private String fieldId = "";            //元素编码
    @ExcelProperty(value = "数据元内部标识符",index = 13)
    private String gadsjFieldId = "";       //数据元内部标识符
    @ExcelIgnore
    private String determinerId = "";       //限定词
    @ExcelIgnore
    private String fieldClassCh = "";       //字段分类
    @ExcelIgnore
    private String sameWordType = "";       //语义类型
    @ExcelIgnore
    private String elementName = "";        //数据要素
    @ExcelIgnore
    private String securityLevelCh = "";    //数据安全级别
    @ExcelIgnore
    private Integer isIndex;                //标准中是否存在索引
    @ExcelIgnore
    private String codeText = " ";          //页面显示 代码中文名
    @Size(max = 50, message = "[标准列名]长度不能超过40")
    @ExcelIgnore
    private String fieldName = "";          //标准中数据项英文描述
    @ExcelIgnore
    private int FieldSourceType = 0;        //是否属于原始库  1：是  0：否  默认值：0
    @ExcelIgnore
    private Integer partitionRecno;         //分区列标示
    @ExcelIgnore
    private String memo = "";               //备注


    @ExcelIgnore
    private Integer objectId;//表对象ID
    @Size(max = 300, message = "[字段描述]长度不能超过40")
    @ExcelIgnore
    private String fieldDescribe = " ";//标准中的字段描述
    @ExcelIgnore
    private String defaultValue = " ";//字段默认值
    @ExcelIgnore
    private Integer indexType;//索引类型
    @ExcelIgnore
    private Integer isContorl;//是否为布控条件
    @ExcelIgnore
    private Integer tableIndex;//表索引
    @ExcelIgnore
    private Integer deleted;//
    @ExcelIgnore
    private Integer isQuery;//是否可查询
    @ExcelIgnore
    private Integer columnNameState = 1;//表字段是否可用
    @ExcelIgnore
    private Integer md5Index;//是否参与MD5运算
    @ExcelIgnore
    private Integer versions;//
    @ExcelIgnore
    private String isPrivate = "";//是否标准协议
    @ExcelIgnore
    private Integer standardRecno;//标准中序列顺序
    @ExcelIgnore
    private Integer pkRecno;//主键顺序
    @ExcelIgnore
    private Integer clustRecno;//聚集列
    @ExcelIgnore
    private Integer oraShow;//是否近线显示
    @ExcelIgnore
    private String needv = " ";
    @ExcelIgnore
    private String fieldtypes = " ";
    @ExcelIgnore
    private String needvalues = " ";
    @ExcelIgnore
    private String odpsPattition = " ";
    @ExcelIgnore
    private String proType = " ";
    @ExcelIgnore
    private Integer secretLevel;//字段敏感级别
    @ExcelIgnore
    private String codeid = "";  //

    // 在添加字段的页面中，需要新增几个字段 20191023
    //  如果选择的是，则需要获取已经存在的index，
    @ExcelIgnore
    private Boolean clustRecnoStatus;//是否为聚集列
    @ExcelIgnore
    private Boolean pkRecnoStatus;//是否为主键列
    @ExcelIgnore
    private Boolean md5IndexStatus;//是否参与MD5运算
    @ExcelIgnore
    private String type = " ";    //0:来源字段    1：标准字段  为空时表示是标准字段
    @ExcelIgnore
    private String createColumnType = "";  //在odps和ads建表时的字段类型
    @ExcelIgnore
    private Integer createColumnLen;  // 在odps和ads建表时的字段长度

    // 在建表中presto需要以下几个参数
    /**
     * 是否过滤列,0否 1是
     */
    @NotNull
    @ExcelIgnore
    private Byte isFilter = 0;

    /**
     * 是否保存source，0否1是
     */
    @NotNull
    @ExcelIgnore
    private Byte isSource = 0;

    /**
     * 是否存为storefield
     */
    @NotNull
    @ExcelIgnore
    private Byte isStore = 0;

    /**
     * 是否存为docval
     */
    @NotNull
    @ExcelIgnore
    private Byte isDocval = 0;

    /**
     * 是否作为业务字段 0否 1是
     */
    @NotNull
    @ExcelIgnore
    private Byte isRowkey = 1;

    @NotNull
    @ExcelIgnore
    private Boolean isIndexs = false;

    //  因为需要先将表字段信息插入到页面中，所以需要判断这个字段是已经被修改/新增
    //  0：没有变化   1：新增数据  2：修改数据
    @ExcelIgnore
    private Byte updateStatus = 0;

    //  字段分类代码值
    @ExcelIgnore
    private String fieldClassId = "";
    // 敏感度分类代码值
    @ExcelIgnore
    private String sensitivityLevelCh = "";
    @ExcelIgnore
    private String sensitivityLevel = "";

    //数据元的内部标识符(用来数据元回限)
    @ExcelIgnore
    private String label;
    @ExcelIgnore
    private String synlteFieldMemo;

    //20210802 标准表新增的6个字段
    /**
     * 标准新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "【创建时间】不能为空")
    @ExcelIgnore
    private Date createTime;

    /**
     * 最新修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "【修改时间】不能为空")
    @ExcelIgnore
    private Date updateTime;

    /**
     * 标准创用户名称
     */
    @NotNull(message = "【创建人用户名称】不能为空")
    @ExcelIgnore
    private String creator;

    /**
     * 标准最新修改用户名称
     */
    @NotNull(message = "【修改用户名称】不能为空")
    @ExcelIgnore
    private String updater;

    /**
     * 大版本号
     */
    @ExcelIgnore
    private String version0;

    /**
     * 标准表版本主键
     */
    @ExcelIgnore
    private String objectIdVersion;

    /**
     * 数据要素id
     */
    @ExcelIgnore
    private String elementId;

    /**
     * 202100915 数据安全分级
     */
    @ExcelIgnore
    private String securityLevel = "";

}
