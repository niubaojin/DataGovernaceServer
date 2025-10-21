package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("SYNLTE.FIELD_RESOURCE_INFO")
public class FieldResourceInfoEntity {

    //数据项编号
    @TableId(type = IdType.NONE)
    @TableField("SJXBH")
    private String sjxbh;

    //数据资源标识符
    @TableField("SJZYBSF")
    private String sjzybsf;

    //是否标识项
    @TableField("SFBSX")
    private Integer sfbsx;

    //数据项中文名称
    @TableField("SJXZWMC")
    private String sjxzwmc;

    //数据项英文名称（建表字段英文名，对应object_store_fieldinfo.columnname）
    @TableField("SJXYWMC")
    private String sjxywmc;

    //数据元唯一编码（数据元编码, 对应object_store_fieldinfo表中的FieldID）
    @TableField("SJYNBBSF")
    private String sjynbbsf;

    //限定词ID
    @TableField("DeterminerID")
    private String determinerId;
    //限定词
    @TableField(exist = false)
    private String determiner;

    //数据项标识符（辅助理解：类似于别名，该表所包含的记录中，具有相同“数据资源标识符”的数据记录，该字段唯一）
    //数据项标识符（2023.06.14新需求：标准英文名改为‘标识符’，值为字段中文名首字符拼音）
    @TableField("SJXBSF")
    private String sjxbsf;

    //数据项类型（0：原始数据项；1：标签数据项；2：关联数据项；3:标准化数据项；4:回溯数据项；5:公共数据项）
    //说明：0-2是部标已定义数据项类型，3-5是公司根据原始库技术要求补充定义的数据项类型。（数据定义设置，objectfield增加相应字段“数据项来源构成分类”）
    @TableField("SJXLX")
    private Integer sjxlx;

    //备注
    @TableField("BZ")
    private String bz;

    //是否查询条件（0：否；1：是（标准定义））
    @TableField("SFCXTJ")
    private Integer sfcxtj;

    //是否订阅条件（0：否；1：是）
    @TableField("SFDYTJ")
    private Integer sfdytj;

    //查询/订阅匹配方式（01：等值匹配；02模糊匹配：03正则表达式匹配；04：逻辑表达式匹配；05：样本匹配。可多值，半角逗号分隔（注册时，默认空，空值时作为全部可选））
    @TableField("CXDYPPFS")
    private String cxdyppfs;

    //是否必填
    @TableField("SFBT")
    private Integer sfbt;

    //字段性质分类代码
    //值域符合GA/DSJ 231-2020的要求
    //仅存两位部标代码值，由程序判断一级分类。
    //部标二级分类说明：01~11（基本信息），12（生物特征），13~15（图片影像），16（文档资料），17~20（地址位置），21~31（行为信息），32~35（内容信息），36~38（系统信息）
    @TableField("ZDFL")
    private String zdfl;

    //数据安全级别代码
    @TableField("ZDMGDFLDM")
    private String zdmgdfldm;

    //注册时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("ZCSJ")
    private Date zcsj;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("GXSJ")
    private Date gxsj;

    //操作人
    @TableField("CZR")
    private String czr;

    //字段顺序
    @TableField("RECNO")
    private Integer recno;

    //内部标识符（存放部标数据元标识，将内部数据元标识映射到该部标数据元标识上用于和部标靠齐，该字段将与SYNLTEFIELD表中GADSJ_FIELDID的保持一致，来源于表SYNLTEFIELD中的FieldID）
    @TableField("GADSJ_FIELDID")
    private String gadsjFieldId;

    //字段类型（参照FIELDTYPE取值说明）
    @TableField("FIELDTYPE")
    private Integer fieldType;
    //字段类型名称
    @TableField(exist = false)
    private String fieldTypeCh;

    //字段长度
    @TableField("FIELDLEN")
    private Integer fieldLen;

    //字段是否注册，0：不注册，1：注册
    @TableField(exist = false)
    private int zdzc;
    //已注册字段名
    @TableField(exist = false)
    private String yzczdYwmc;

}
