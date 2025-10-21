package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据标准结构定义(OBJECTFIELD)(源数据表字段定义)
 * @author
 * @Date 2025年5月8日15:59:30
 */
@Data
@TableName("SYNLTE.OBJECTFIELD")
public class ObjectFieldEntity {
    //表对象ID
    @TableField("OBJECTID")
    private Integer objectId;

    //表中字段顺序
    @TableField("RECNO")
    private Integer recno;

    //数据元唯一编码
    @TableField("FIELDID")
    private String fieldId;

    //标准中项英文描述
    @TableField("FIELDNAME")
    private String fieldName;

    //标准中的字段中文名称
    @TableField("FIELDCHINEENAME")
    private String fieldChineseName;

    //标准中的字段描述
    @TableField("FIELDDESCRIBE")
    private String fieldDescribe;

    //字段类型
    @TableField("FIELDTYPE")
    private Integer fieldType;

    @TableField(exist = false)
    private String fieldTypeCh;

    //字段长度
    @TableField("FIELDLEN")
    private Integer fieldLen;

    //字段默认值
    @TableField("DEFAULTVALUE")
    private String defaultValue;

    //索引类型
    @TableField("INDEXTYPE")
    private Integer indexType;

    //标准中是否存在索引
    @TableField("ISINDEX")
    private Integer isIndex;
    @TableField(exist = false)
    private String isIndexStr;

    //是否必填项
    @TableField("NEEDVALUE")
    private Integer needValue;
    @TableField(exist = false)
    private String needValueStr;

    @TableField(exist = false)
    private String Needv;

    //是否为布控条件
    @TableField("ISCONTORL")
    private Integer isContorl;
    @TableField(exist = false)
    private String isContorlStr;

    //表中的字段名称
    @TableField("COLUMNNAME")
    private String columnName;

    //备注
    @TableField("MEMO")
    private String memo;

    //表索引
    @TableField("TABLEINDEX")
    private Integer tableIndex;

    //
    @TableField("DELETED")
    private Integer deleted;

    //是否可查询
    @TableField("ISQUERY")
    private Integer isQuery;

    //表字段是否可用
    @TableField("COLUMNNAME_STATE")
    private Integer columnNameState;

    //是否参与MD5运算
    @TableField("MD5_INDEX")
    private Integer md5Index;

    //版本日期
    @TableField("VERSIONS")
    private Integer versions;

    //是否标准协议
    @TableField("ISPRIVATE")
    private Integer isPrivate;

    //标准中序列顺序
    @TableField("STANDARD_RECNO")
    private Integer standardRecno;

    //主键顺序
    @TableField("PK_RECNO")
    private Integer pkRecno;

    //分区列标示
    @TableField("PARTITION_RECNO")
    private Integer partitionRecno;

    //聚集列
    @TableField("CLUST_RECNO")
    private Integer clustRecno;

    //是否近线显示
    @TableField("ORA_SHOW")
    private Integer oraShow;
    @TableField(exist = false)
    private String oraShowStr;

    //
    @TableField("NEEDV")
    private String needV;

    //字段类型
    @TableField("FIELDTYPES")
    private String fieldtypes;

    //
    @TableField("NEEDVALUES")
    private String needValues;

    //ODPS的分区列
    @TableField("ODPS_PATTITION")
    private Integer odpsPattition;

    //属性类型
    @TableField("PRO_TYPE")
    private Integer proType;

    //
    @TableField("ISDERIVANT")
    private Integer isDerivant;

    //是否原始字段
    @TableField("FIELDSOURCETYPE")
    private Integer fieldSourceType;

    //限定词ID
    @TableField("DETERMINERID")
    private String determinerId;

    //限定词名称
    @TableField(exist = false)
    private String determinerName = "";

    //字段分类代码
    @TableField("ZDFL")
    private String fieldClassId = "";   // 字段分类代码值

    @TableField(exist = false)
    private String fieldClassCh = "";   // 字段分类中文名

    //安全级别
    @TableField("ZDMGDFLDM")
    private String zdmgdfldm;

    //202100915 数据安全分级中文
    @TableField(exist = false)
    private String securityLevelCh = "";

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime = new Date();

    //最新修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime = new Date();

    //创建人
    @TableField("CREATOR")
    private String creator;

    //更新人
    @TableField("UPDATER")
    private String updater;

    //版本号
    @TableField("VERSION_0")
    private String version0;

    //内部标识符
    @TableField("GADSJ_FIELDID")
    private String gadsjFieldId;

    //字段敏感级别
    @TableField("SECRETLEVEL")
    private Integer secretLevel;

    //版本日期
    @TableField("VERSION")
    private Integer version;

    //数据项来源构成分类
    @TableField("SJLYGCFL")
    private Integer sjlygcfl;

    //1表示对账标识字段 2表示对账时间字段
    @TableField("RECONCILIATIONTYPE")
    private Integer reconciliationType;

    //数据元的内部标识符(用来数据元回限)
    @TableField(exist = false)
    private String label;
    @TableField(exist = false)
    private String synlteFieldMemo;

    // 在添加字段的页面中，需要新增几个字段 20191023
    // 如果选择的是，则需要获取已经存在的index，
    @TableField(exist = false)
    private Boolean clustRecnoStatus;   //是否为聚集列
    @TableField(exist = false)
    private Boolean pkRecnoStatus;      //是否为主键列
    @TableField(exist = false)
    private Boolean md5IndexStatus;     //是否参与MD5运算
    @TableField(exist = false)
    private String codeText = "";      //页面显示：代码中文名
    @TableField(exist = false)
    private String codeid = "";  //

    // 20200914 新增字段分类中文名 字段分类代码值 敏感度分类代码值
    @TableField(exist = false)
    private String sameWordType = "";   // 语义类型
    @TableField(exist = false)
    private String elementName = "";    // 数据要素名称
    @TableField(exist = false)
    private String sensitivityLevelCh = "";
    @TableField(exist = false)
    private String sensitivityLevel = "";

    // 因为需要先将表字段信息插入到页面中，所以需要判断这个字段是已经被修改/新增
    // 0：没有变化，1：新增数据，2：修改数据
    @TableField(exist = false)
    private Integer updateStatus = 0;

    //建表字段类型
    @TableField(exist = false)
    private String createColumnType;

    // 在odps和ads建表时的字段长度
    @TableField(exist = false)
    private int createColumnLen;

    /**
     * 在建表中presto需要以下几个参数
     */
    //是否过滤列,0否 1是
    @NotNull
    @TableField(exist = false)
    private Byte isFilter = 0;
    //是否保存source，0否1是
    @NotNull
    @TableField(exist = false)
    private Byte isSource = 0;
    //是否存为storefield
    @NotNull
    @TableField(exist = false)
    private Byte isStore = 0;
    //是否存为docval
    @NotNull
    @TableField(exist = false)
    private Byte isDocval = 0;
    //是否作为业务字段 0否 1是
    @NotNull
    @TableField(exist = false)
    private Byte isRowkey = 1;
    @NotNull
    @TableField(exist = false)
    private Boolean isIndexs = false;


}
