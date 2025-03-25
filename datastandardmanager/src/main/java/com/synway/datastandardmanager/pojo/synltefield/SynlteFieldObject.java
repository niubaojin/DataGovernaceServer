package com.synway.datastandardmanager.pojo.synltefield;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.valid.IntListValue;
import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 数据元管理的相关实体类
 * @author wangdongwei
 * @date 2021/7/21 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynlteFieldObject implements Serializable {

    private static final long serialVersionUID = 7311145684133100786L;

    /**
     * 元素编码  即唯一编码
     */
    @NotBlank(message="[唯一编码]不能为空")
    @Size(max=40,message = "[唯一编码]长度不能超过40")
    @Pattern(regexp = "^[a-zA-Z0-9]+",message = "[数据库字段]只能是字母和数字组成")
    private String fieldId;

    /**
     * 数据库字段名称
     */
    @NotBlank(message="[数据库字段]不能为空")
    @Size(max=100,message = "[数据库字段]长度不能超过100")
    @Pattern(regexp = "^[a-zA-Z0-9_]+",message = "[数据库字段]只能是字母，数字和下划线组成")
    private String columnName;


    /**
     * 标准中元素项英文描述
     */
    @NotBlank(message="[元素项英文描述]不能为空")
    @Size(max=50,message = "[内部标识符]长度不能超过50")
    @Pattern(regexp = "^[a-zA-Z0-9_]+",message = "[元素项英文描述]只能是字母，数字和下划线组成")
    private String fieldName;


    /**
     * 中文名称
     */
    @NotBlank(message="[中文名称]不能为空")
    @Size(max=100,message = "[中文名称]长度不能超过100")
    @Pattern(regexp = ".*?[\u4e00-\u9fa5]+.*?",message = "[中文名称]中至少存在一个中文")
    private String fieldChineseName;


    /**
     * 元素项字段描述
     */
    @Size(max=300,message = "[元素项字段描述]长度不能超过300")
    private String fieldDescribe="";


    /**
     * 字段类型
     */
    @IntListValue(vals = {0,1,2,3,4,6,7},message = "[字段类型]不符合标准")
    private Integer fieldType;

    private String fieldTypeCh;
    /**
     * 字段长度
     */
    @NotBlank(message="[字段长度]不能为空")
    @Size(max=20,message = "[字段长度]长度不能超过20")
    @Pattern(regexp = "^[0-9]+",message = "[字段长度]只能是数字组成")
    private String fieldLen;

    /**
     * 浮点位数
     */
    private String fieldLen2;

    /**
     * 默认值
     */
    @Size(max=20,message = "[默认值]长度不能超过20")
    private String defaultValue="";

    /**
     * 备注信息
     */
    @Size(max=300,message = "[备注信息]长度不能超过300")
    private String memo="";
    /**
     * 元数据代码表ID
     */
    @Size(max=50,message = "[元数据代码表ID]长度不能超过50")
    private String codeId="";


    /**
     * 代码表id的中文翻译值
     */
    private String codeIdStr="";

    /**
     * 关联表  数量
     */
    private int relatedTablesCount;

    /**
     * 语义类型
     */
    @Size(max=50,message = "[语义类型]长度不能超过50")
    private String sameId="";

    /**
     * 特性词
     */
    @Size(max=50,message = "[特性词长度不能超过50]")
    private String attributeWord="";
    /**
     * 是否530新增  以前存在，冗余字段
     */
    @Size(max=2,message = "[是否530新增]长度不能超过2")
    private String is530New="";
    /**
     * 数据元版本应建议向下兼容。
     * 版本是由阿拉伯数字字符和小数点字符组成的字符串
     * 目前为默认值
     */
//    @NotBlank(message="[数据元版本]不能为空")
    @Size(max=10,message = "[数据元版本]长度不能超过10")
    private String versions="";
    /**
     * 版本发布日期
     */
    private Integer releaseDate;
    /**
     * 中文全拼
     */
    @Size(max=200,message = "[中文全拼]长度不能超过200")
    private String fullChinese="";

    /**
     * 标识符
     */
    @NotBlank(message="[标识符]不能为空")
    @Size(max=110,message = "[标识符]长度不能超过110")
    private String simChinese="";
    /**
     * 同义名称
     */
    @Size(max=200,message = "[同义名称]长度不能超过200")
    private String synonyName="";
    /**
     * 对象类词
     */
    @Size(max=50,message = "[对象类词]长度不能超过50")
    private String objectType="";
    /**
     * 表示词
     */
    @Size(max=50,message = "[表示词]长度不能超过50")
    private String expressionWord="";
    /**
     * 值域描述信息
     */
    @Size(max=200,message = "[值域描述信息]长度不能超过200")
    private String codeIdDetail="";
    /**
     * 归一化标识
     */
    @IntListValue(vals = {0,1},message = "归一化标识的值只能为0/1")
    private int isNorMal;
    /**
     * 关系
     */
//    @NotNull(message="[关系]不能为空")
    @Size(max=50,message = "[关系]长度不能超过50")
    private String relate="";
    /**
     * 计量单位
     */
//    @NotNull(message="[计量单位]不能为空")
    @Size(max=50,message = "[计量单位]长度不能超过50")
    private String unit="";
    /**
     * 融合单位类型
     */
    @Size(max=20,message = "[融合单位类型]长度不能超过20")
    private String fuseType="";

    /**
     * 融合单位元素编码
     */
    @Size(max=200,message = "[融合单位元素编码]长度不能超过200")
    private String fuseFieldId="";

    /**
     * 状态
     * 目前只会有 01：新建  05：发布  07：废止
     */
    @NotNull(message="[状态]不能为空")
    @Size(max=10,message = "[状态]长度不能超过10")
    @ListValue(vals = {"01","02","03","04","05","06","07"},
            message = "[状态]的值只能为01/02/03/04/05/06/07")
    private String statusNum="";

    /**
     * 状态的翻译 中文
     */
    private String status="";
    /**
     * 提交机构
     */
    @NotNull(message="[提交机构]不能为空")
    @Size(max=50,message = "[提交机构]长度不能超过50")
    private String subOrg="";
    /**
     * 主要起草人
     */
    @Size(max=500,message = "[主要起草人]长度不能超过500")
    private String subAuthor="";

    /**
     * 批准日期
     */
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "GMT+8")
    @DateTimeFormat(pattern ="dd-MM-yyyy")
    private Date onDate;
    /**
     * 注册机构
     */
    @Size(max=50,message = "[注册机构]长度不能超过50")
    private String regOrg="";
    /**
     * 字段分类代码值
     */
    @Size(max=10,message = "[字段分类代码值]长度不能超过10")
    private String fieldClass="";
    /**
     * 字段分类中文名称
     */
    private String fieldClassCh="";
    /**
     * 字段数据安全分级
     */
    @Size(max=10,message = "[字段安全分级]长度不能超过10")
    private String secretClass="";

    /**
     * 字段数据安全分级中文
     */
    private String secretClassCh="";
    /**
     * 是否属于部标  1：是   0：否
     */
    @IntListValue(vals = {0,1},message = "[是否属于部标]的值只能为0/1")
    private int fieldStandard;
    /**
     * 厂商名称
     */
    @Size(max=50,message = "[厂商名称]长度不能超过50")
    private String facturer="";
    /**
     * 语境
     */
    @Size(max=200,message = "[语境]长度不能超过200")
    private String context="";
    /**
     * 应用约束
     */
    @Size(max=200,message = "[应用约束]长度不能超过200")
    private String constraint="";

    //20210803 数据元新增的4个字段

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建人
     */
    @NotBlank(message="[创建人]不能为空")
    private String creator;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 数据元历史表主键
     */
    private String fieldIdVersion;

//    private Integer field_standard;

    /**
     * 数据元内部标识符
     */
//    @NotBlank(message = "[数据元内部标识符不能为空]")
    private String gadsjFieldId;

    /**
     * 关系关联数据元
     */
    private String fieldRelates;


}
