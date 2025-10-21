package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据元定义备份
 * @author nbj
 * @date 2025年5月13日09:33:39
 */
@Data
@TableName("SYNLTE.SYNLTEFIELD_HISTORY")
public class SynlteFieldHisEntity {
    //标准表版本主键
    @TableField("FIELDID_VERSION")
    private String fieldIdVersion;

    //数据元唯一编码
    @TableField("FIELDID")
    private String fieldId;

    //数据库字段名称
    @TableField("COLUMNNAME")
    private String columnName;

    //标准中元素项英文描述
    @TableField("FIELDNAME")
    private String fieldName;

    //中文名称
    @TableField("FIELDCHINESENAME")
    private String fieldChineseName;

    //元素项字段描述
    @TableField("FIELDDESCRIBE")
    private String fieldDescribe;

    //字段类型
    @TableField("FIELDTYPE")
    private Integer fieldType;

    //字段长度
    @TableField("FIELDLEN")
    private String fieldLen;

    //默认值
    @TableField("DEFAULTVALUE")
    private String defaultValue;

    //备注信息
    @TableField("MEMO")
    private String memo;

    //元数据代码表ID
    @TableField("CODEID")
    private String codeId;

    //语义类型
    @TableField("SAMEID")
    private String sameId;

    //是否530新增
    @TableField("IS530NEW")
    private Integer is530New;

    //版本
    @TableField("VERSIONS")
    private String versions;

    //版本发布日期
    @TableField("RELEASEDATE")
    private Integer releaseDate;

    //中文全拼
    @TableField("FULL_CHINSEE")
    private String fullChinsee;

    //标识符
    @TableField("SIM_CHINESE")
    private String simChinese;

    //同义名称
    @TableField("SYNONY_NAME")
    private String synonyName;

    //对象类型
    @TableField("OBJECT_TYPE")
    private String objectType;

    //表示词
    @TableField("EXPRESSION_WORD")
    private String expressionWord;

    //值域描述信息
    @TableField("CODEID_DETAIL")
    private String codeIdDetail;

    //归一化标识
    @TableField("ISNORMAL")
    private Integer isNormal;

    //关系
    @TableField("RELATE")
    private String relate;

    //计量单位
    @TableField("UNIT")
    private String unit;

    //融合单位类型
    @TableField("FUSE_TYPE")
    private String fuseType;

    //融合单位数据元内部标识符
    @TableField("FUSE_FIELDID")
    private String fuseFieldId;

    //状态
    @TableField("STATUS")
    private String status;

    //提交机构
    @TableField("SUB_ORG")
    private String subOrg;

    //主要起草人
    @TableField("SUB_AUTHOR")
    private String subAuthor;

    //批准日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("ON_DATE")
    private Date onDate;

    //注册机构
    @TableField("REG_ORG")
    private String regOrg;

    //字段分类
    @TableField("FIELD_CLASS")
    private String fieldClass;

    //字段敏感度分类
    @TableField("SECRET_CLASS")
    private String secretClass;

    //是否属于部标
    @TableField("FIELD_STANDARD")
    private Integer fieldStandard;

    //厂商信息
    @TableField("FACTURER")
    private String facturer;

    //语境
    @TableField("CONTEXT")
    private String context;

    //应用约束
    @TableField("CONSTRAINT")
    private String constraint;

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

    //内部标识符
    @TableField("GADSJ_FIELDID")
    private String gadsjFieldId;

    //浮点位数
    @TableField("FIELDLEN2")
    private Integer fieldLen2;

    //ID
    @TableField("ID")
    private Integer id;

    //软删除标识
    @TableField("DELETED")
    private Integer deleted;

    //关系关联数据元
    @TableField("FIELD_RELATES")
    private String fieldRelates;
}
