package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据标准结构备份表
 * @author nbj
 * @date 2025年5月12日17:18:36
 */
@Data
@TableName("SYNLTE.OBJECTFIELD_HISTORY")
public class ObjectFieldHisEntity {
    //表对象ID
    @TableField("OBJECTID_VERSION")
    private String objectIdVersion;

    //表对象ID
    @TableField("OBJECTID")
    private Integer objectId;

    //表中字段顺序
    @TableField("RECNO")
    private Integer recno;

    //数据元唯一编码
    @TableField("FIELDID")
    private String fieldId;

    //表中的字段名称
    @TableField("COLUMNNAME")
    private String columnName;

    //标准中项英文描述
    @TableField("FIELDNAME")
    private String fieldName;

    //标准中的字段中文名称
    @TableField("FIELDCHINEENAME")
    private String fieldChineseName;

    //标准中的字段描述
    @TableField("FIELDDESCRIBE")
    private String fileldDescribe;

    //字段类型
    @TableField("FIELDTYPE")
    private Integer fieldType;

    //字段长度
    @TableField("FIELDLEN")
    private Integer fieldLen;

    //字段默认值
    @TableField("DEFAULTVALUE")
    private String defaultValue;

    //表索引
    @TableField("TABLEINDEX")
    private Integer tableIndex;

    //标准中是否存在索引
    @TableField("ISINDEX")
    private Integer isIndex;

    //索引类型
    @TableField("INDEXTYPE")
    private Integer indexType;

    //是否必填项
    @TableField("NEEDVALUE")
    private Integer needValue;

    //是否为布控条件
    @TableField("ISCONTORL")
    private Integer isContorl;

    //是否可查询
    @TableField("ISQUERY")
    private Integer isQuery;

    //备注
    @TableField("MEMO")
    private String memo;

    //字段敏感级别
    @TableField("SECRETLEVEL")
    private Integer secretLevel;

    //表字段是否可用
    @TableField("COLUMNNAME_STATE")
    private Integer columnNameState;

    //是否参与MD5运算
    @TableField("MD5_INDEX")
    private Integer md5Index;

    //是否标准协议
    @TableField("ISPRIVATE")
    private Integer isPrivate;

    //版本日期
    @TableField("VERSION")
    private Integer version;

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

    //ODPS的分区列
    @TableField("ODPS_PATTITION")
    private Integer odpsPattition;

    //属性类型
    @TableField("PRO_TYPE")
    private Integer proType;

    //是否原始字段
    @TableField("FIELDSOURCETYPE")
    private Integer fieldSourceType;

    //限定词ID
    @TableField("DETERMINERID")
    private String determinerId;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime;

    //最新修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime;

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

}
