package com.synway.datastandardmanager.entity.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.valid.IntListValue;
import com.synway.datastandardmanager.valid.ListValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据元定义
 * @author nbj
 * @date 2025年5月13日09:24:55
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
@Data
@TableName("SYNLTE.SYNLTEFIELD")
public class SynlteFieldEntity {

    //数据元唯一编码
    @NotBlank(message="[唯一编码]不能为空")
    @ExcelProperty("唯一标识符")
    @Size(max=40,message = "[唯一编码]长度不能超过40")
    @Pattern(regexp = "^[a-zA-Z0-9]+",message = "[数据库字段]只能是字母和数字组成")
    @TableField("FIELDID")
    private String fieldId;

    //标准中元素项英文描述
    @NotBlank(message="[元素项英文描述]不能为空")
    @ExcelProperty("标准英文名")
    @Size(max=50,message = "[内部标识符]长度不能超过50")
    @Pattern(regexp = "^[a-zA-Z0-9_]+",message = "[元素项英文描述]只能是字母，数字和下划线组成")
    @TableField("FIELDNAME")
    private String fieldName;

    //中文名称
    @NotBlank(message="[中文名称]不能为空")
    @ExcelProperty("标准中文名")
    @Size(max=100,message = "[中文名称]长度不能超过100")
    @Pattern(regexp = ".*?[\u4e00-\u9fa5]+.*?",message = "[中文名称]中至少存在一个中文")
    @TableField("FIELDCHINESENAME")
    private String fieldChineseName;

    //元素项字段描述
    @ExcelProperty("字段描述")
    @Size(max=300,message = "[元素项字段描述]长度不能超过300")
    @TableField("FIELDDESCRIBE")
    private String fieldDescribe;

    //字段类型
    @ExcelProperty("字段类型")
    @IntListValue(vals = {0,1,2,3,4,6,7},message = "[字段类型]不符合标准")
    @TableField("FIELDTYPE")
    private Integer fieldType;
    // 类型的中文名称
    @ExcelIgnore
    @TableField(exist = false)
    private  String fieldtypeName;
    @ExcelIgnore
    @TableField(exist = false)
    private String FieldTypeCh;

    //字段长度
    @ExcelProperty("字段长度")
    @NotBlank(message="[字段长度]不能为空")
    @Size(max=20,message = "[字段长度]长度不能超过20")
    @Pattern(regexp = "^[0-9]+",message = "[字段长度]只能是数字组成")
    @TableField("FIELDLEN")
    private Integer fieldLen;

    //默认值
    @ExcelProperty("默认值")
    @Size(max=20,message = "[默认值]长度不能超过20")
    @TableField("DEFAULTVALUE")
    private String defaultValue = "";

    //备注信息
    @ExcelProperty("备注")
    @Size(max=300,message = "[备注信息]长度不能超过300")
    @TableField("MEMO")
    private String memo = "";

    //数据库字段名称
    @ExcelProperty("建表字段英文名称")
    @NotBlank(message="[数据库字段]不能为空")
    @Size(max=100,message = "[数据库字段]长度不能超过100")
    @Pattern(regexp = "^[a-zA-Z0-9_]+",message = "[数据库字段]只能是字母，数字和下划线组成")
    @TableField("COLUMNNAME")
    private String columnName;

    //软删除标识
    @ExcelIgnore
    @TableField("DELETED")
    private Integer deleted;

    //元数据代码表ID
    @ExcelIgnore
    @Size(max=50,message = "[元数据代码表ID]长度不能超过50")
    @TableField("CODEID")
    private String codeId = "";
    @ExcelIgnore
    @TableField(exist = false)
    private String codeIdStr = "";

    //语义类型
    @ExcelProperty("语义类型")
    @Size(max=50,message = "[语义类型]长度不能超过50")
    @TableField("SAMEID")
    private String sameId = "";

    //是否530新增
    @ExcelIgnore
    @Size(max=2,message = "[是否530新增]长度不能超过2")
    @TableField("IS530NEW")
    private Integer is530New = 0;

    //浮点位数
    @ExcelIgnore
    @TableField("FIELDLEN2")
    private Integer fieldLen2;

    //ID
    @ExcelIgnore
    @TableField("ID")
    private Integer id;

    //特性词
    @ExcelProperty("特性词")
    @Size(max=50,message = "[特性词长度不能超过50]")
    @TableField("ATTRIBUTE_WORD")
    private String attributeWord = "";

    //版本
    @ExcelProperty("版本")
    @Size(max=10,message = "[数据元版本]长度不能超过10")
    @TableField("VERSIONS")
    private String versions = "";

    //版本发布日期
    @ExcelIgnore
    @TableField("RELEASEDATE")
    private Integer releaseDate;

    //中文全拼
    @ExcelProperty("中文全拼")
    @Size(max=200,message = "[中文全拼]长度不能超过200")
    @TableField("FULL_CHINSEE")
    private String fullChinese = "";

    //标识符
    @ExcelProperty("标识符")
    @NotBlank(message="[标识符]不能为空")
    @Size(max=110,message = "[标识符]长度不能超过110")
    @TableField("SIM_CHINESE")
    private String simChinese = "";

    //同义名称
    @ExcelProperty("同一名称")
    @Size(max=200,message = "[同义名称]长度不能超过200")
    @TableField("SYNONY_NAME")
    private String synonyName = "";

    //对象类型
    @ExcelProperty("对象类词")
    @Size(max=50,message = "[对象类词]长度不能超过50")
    @TableField("OBJECT_TYPE")
    private String objectType = "";

    //表示词
    @ExcelProperty("表示词")
    @Size(max=50,message = "[表示词]长度不能超过50")
    @TableField("EXPRESSION_WORD")
    private String expressionWord = "";

    //值域描述信息
    @ExcelProperty("值域描述信息")
    @Size(max=200,message = "[值域描述信息]长度不能超过200")
    @TableField("CODEID_DETAIL")
    private String codeIdDetail = "";

    //归一化标识
    @ExcelProperty("归一化标识")
    @IntListValue(vals = {0,1},message = "归一化标识的值只能为0/1")
    @TableField("ISNORMAL")
    private Integer isNorMal;

    //关系
    @ExcelProperty("关系")
    @Size(max=50,message = "[关系]长度不能超过50")
    @TableField("RELATE")
    private String relate = "";

    //计量单位
    @ExcelProperty("计量单位")
    @Size(max=50,message = "[计量单位]长度不能超过50")
    @TableField("UNIT")
    private String unit = "";

    //融合单位类型
    @ExcelProperty("融合单位类型")
    @Size(max=20,message = "[融合单位类型]长度不能超过20")
    @TableField("FUSE_TYPE")
    private String fuseType = "";

    //融合单位数据元内部标识符
    @ExcelProperty("融合单位元素编码")
    @Size(max=200,message = "[融合单位元素编码]长度不能超过200")
    @TableField("FUSE_FIELDID")
    private String fuseFieldId = "";

    //状态
    @ExcelIgnore
    @TableField(exist = false)
    private String status;

    //状态，目前只会有 01：新建  05：发布  07：废止
    @ExcelProperty("状态")
    @NotNull(message="[状态]不能为空")
    @Size(max=10,message = "[状态]长度不能超过10")
    @ListValue(vals = {"01","02","03","04","05","06","07"}, message = "[状态]的值只能为01/02/03/04/05/06/07")
    @TableField("STATUS")
    private String statusNum= "";

    //提交机构
    @ExcelProperty("提交机构")
    @NotNull(message="[提交机构]不能为空")
    @Size(max=50,message = "[提交机构]长度不能超过50")
    @TableField("SUB_ORG")
    private String subOrg = "";

    //主要起草人
    @ExcelProperty("主要起草人")
    @Size(max=500,message = "[主要起草人]长度不能超过500")
    @TableField("SUB_AUTHOR")
    private String subAuthor = "";

    //批准日期
    @ExcelProperty("批准日期")
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "GMT+8")
    @DateTimeFormat(pattern ="dd-MM-yyyy")
    @TableField("ON_DATE")
    private Date onDate;

    //注册机构
    @ExcelProperty("注册机构")
    @Size(max=50,message = "[注册机构]长度不能超过50")
    @TableField("REG_ORG")
    private String regOrg = "";

    //字段分类
    @ExcelProperty("字段分类")
    @Size(max=10,message = "[字段分类代码值]长度不能超过10")
    @TableField("FIELD_CLASS")
    private String fieldClass = "";
    //字段分类中文名称
    @ExcelIgnore
    @TableField(exist = false)
    private String FieldClassCh = "";

    //字段敏感度分类
    @ExcelProperty("字段安全分级")
    @Size(max=10,message = "[字段安全分级]长度不能超过10")
    @TableField("SECRET_CLASS")
    private String secretClass = "";
    // 字段数据安全分级中文
    @ExcelIgnore
    @TableField(exist = false)
    private String secretClassCh = "";

    //是否属于部标，1：是，0：否
    @ExcelProperty("是否部标")
    @IntListValue(vals = {0,1},message = "[是否属于部标]的值只能为0/1")
    @TableField("FIELD_STANDARD")
    private Integer fieldStandard;

    //厂商信息
    @ExcelProperty("厂商名称")
    @Size(max=50,message = "[厂商名称]长度不能超过50")
    @TableField("FACTURER")
    private String facturer = "";

    //语境
    @ExcelProperty("语境")
    @Size(max=200,message = "[语境]长度不能超过200")
    @TableField("CONTEXT")
    private String context = "";

    //应用约束
    @ExcelProperty("应用约束")
    @Size(max=200,message = "[应用约束]长度不能超过200")
    @TableField("'CONSTRAINT'")
    private String constraint = "";

    //创建时间
    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime;

    //最新修改时间
    @ExcelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime;

    //创建人
    @ExcelProperty("创建人")
    @NotBlank(message="[创建人]不能为空")
    @TableField("CREATOR")
    private String creator;

    //更新人
    @ExcelProperty("更新人")
    @TableField("UPDATER")
    private String updater;

    //内部标识符
    @ExcelProperty("内部标识符")
    @TableField("GADSJ_FIELDID")
    private String gadsjFieldId;

    //关系关联数据元
    @ExcelProperty("关系关联数据元")
    @TableField("FIELD_RELATES")
    private String fieldRelates;

    //数据元来源标准规范，描述当前数据元出自哪份标准规范文件
    @ExcelIgnore
    @TableField(exist = false)
    private String fromFile;

    @ExcelIgnore
    @TableField(exist = false)
    private Integer relatedTablesCount;

}
