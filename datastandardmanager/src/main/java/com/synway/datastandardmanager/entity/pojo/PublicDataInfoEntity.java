package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 数据资源目录表(PUBLIC_DATA_INFO)
 */
@Data
@TableName("SYNLTE.PUBLIC_DATA_INFO")
public class PublicDataInfoEntity {
    //数据资源标识符（主键，数据资源的唯一标识，系统自动生成：R-数据资源管理单位机构代码-8位数字流水号）
    @TableId(type = IdType.NONE)
    @TableField("SJZYBSF")
    private String sjzybsf;

    //标准数据编码（同object表字段TABLEID）
    @TableField("SJXJBM")
    private String sjxjbm;

    //数据来源协议（同object表字段SOURCEID）
    @TableField("SOURCEID")
    private String sourceId;

    //数据资源事权单位代码（数据资源事权单位_事权单位代码）
    @TableField("SJZYSQDW_SQDWDM")
    private String sjzysqdwSqdwdm;

    //数据获取方式（01：侦控，02：管控，03：管理，04：公开。废弃）
    @TableField("SJHQFS")
    private String sjhqfs;

    //应用系统编号（与源应用系统编号重复？废弃）
    @TableField("YYXTBH")
    private String yyxtbh;

    //数据资源管理单位代码（公安机关机构代码）
    @TableField("SJZYGLDW_GAJGJGDM")
    private String sjzygldwGajgjgdm;

    //数据资源位置（01：部，02：省，03：市，04： 网站）
    @TableField("SJZYWZ")
    private String sjzywz;

    //数据资源存储分中心（数据仓库中数据中心中文名称）
    @TableField("SJZYCCFZX")
    private String sjzyccfzx;

    //数据资源目录编号（数据资源目录编号，参见GA DSJ-230-2020附录A.1）
    @TableField("SJZYMLBH")
    private String sjzymlbh;

    //英文名称（数据项集英文名称）
    @TableField("TABLENAME")
    private String tableName;

    //中文名称（数中文名称, 命名规则为“[省份名称] （[地市名称][县名称][科所队名称]）[资源名称]”）
    @TableField("SJXJZWMC")
    private String sjxjzwmc;

    //数据资源标签1（代码表参见GA DSJ-230-2020附录B的表B.1）
    @TableField("SJZYBQ1")
    private String sjzybq1;

    //数据资源标签2
    @TableField("SJZYBQ2")
    private String sjzybq2;

    //数据资源标签3
    @TableField("SJZYBQ3")
    private String sjzybq3;

    //数据资源标签4
    @TableField("SJZYBQ4")
    private String sjzybq4;

    //数据资源标签5
    @TableField("SJZYBQ5")
    private String sjzybq5;

    //数据资源标签6
    @TableField("SJZYBQ6")
    private String sjzybq6;

    //格式描述（数据接入是提供，如：结构化数据、半结构化数据、图片、文档等）
    @TableField("GSMS")
    private String gsms;

    //资源状态（停用/启用/注销 需要添加其他状态）
    @TableField("ZYZT")
    private String zyzt;

    //数据表创建时间：字段迁移到新表(object_store_info)中（废弃）
    @TableField("SJBCJSJ")
    private Date sjbcjsj;

    //最早数据时间（废弃）
    @TableField("ZZSJSJ")
    private Date zzsjsj;

    //更新说明（将数据历程的以文本方式存入）
    @TableField("GXSM")
    private String gxsm;

    //发布时间（资源服务平台保存）
    @TableField("FBSJ")
    private Date fbsj;

    //源应用系统建设公司（业务探查，源应用系统建设公司）
    @TableField("YYYXTJSGS")
    private String yyyxtjsgs;

    //源应用系统管理单位（业务探查，源应用系统管理单位名称）
    @TableField("YYYXTGLDW")
    private String yyyxtgldw;

    //源应用系统名称（业务探查，源应用系统名称）
    @TableField("YYYXTMC")
    private String yyyxtmc;

    //源应用系统编号（业务探查，源应用系统编号）
    @TableField("YYYXTMM")
    private String yyyxtmm;

    //事权单位联系人（业务探查，事权单位联系人）
    @TableField("SQDWLXR")
    private String sqdwlxr;

    //事权单位联系电话（业务探查，事权单位联系电话）
    @TableField("SQDWLXDH")
    private String sqdwlxdh;

    //接入服务方（接入方式探查，手动输入，默认“杭州三汇”）
    @TableField("JRFWF")
    private String jrfwf;

    //提供（接入）方式（接入方式探查，默认数据类型，可选择）
    @TableField("TGJRFS")
    private String tgjrfs;

    //接入服务人（接入方式探查，默认当前用户名称，可手动输入）
    @TableField("JRFWR")
    private String jrfwr;

    //接入服务联系电话（接入方式探查，默认当前用户联系方式，可手动输入）
    @TableField("JRFWLXDH")
    private String jrfwlxdh;

    //数据接入说明（接入方式探查）
    @TableField("SJJRSM")
    private String sjjrsm;

    //数据资源更新周期（内容符合GA DSJ-230-2020附录B的表B.7）
    @TableField("SJZYGXZQ")
    private String sjzygxzq;

    //最早数据更新时间
    @TableField("ZZGXSJ")
    private Date zzgxsj;

    //数据资源管理单位名称（业务探查，数据资源管理单位名称）
    @TableField("SJZYGLMC")
    private String sjzyglmc;

    //数据资源事权单位名称（业务探查，数据资源事权单位名称）
    @TableField("SJZYSQDWMC")
    private String sjzysqdwmc;

    //数据资源来源类型（业务探查，数据资源来源类型（codeid））
    @TableField("SJZYLYLX")
    private String sjzylylx;

    //数据组织一级分类（一级分类CodeId）
    @TableField("SJZZYJFL")
    private String sjzzyjfl;

    //数据组织二级分类（二级分类codeID）
    @TableField("SJZZEJFL")
    private String sjzzejfl;

    //数据分级（从object.SECRETLEVE获取;符合GA/DSJ 231-2020的要求，1：公开数据，02：一般数据，03：重要数据，04：特殊数据）
    @TableField("SJFL ")
    private String sjfl;

    //更新时间（数据定义，标准更新时间）
    @TableField("GXSJ")
    private Date gxsj;

    //更新操作人（数据定义，标准更新操作人）
    @TableField("GXCZR")
    private String gxczr;

    //数据更新方式（如：增量、全量等）
    @TableField("SJGXFS")
    private String sjgxfs;

    //数据更新时间点
    @TableField("SJGXSJ")
    private String sjgxsj;

    //数据资源来源描述（描述数据资源的来源、用途、主要属性等信息）
    @TableField("SJZYMS")
    private String sjzyms;

    //存量数据记录规模（数据资产统计表关联得到）
    @TableField("CLSJJLGM")
    private String clsjjlgm;

    //存量数据存储规模（数据资产统计表关联得到）
    @TableField("CLSJCCGM")
    private String clsjccgm;

    //数据资源来源类型码值（参见GA DSJ-230-2020附 录B.1	数据资源来源类型代码表）
    @TableField("SJZYLYLXVALUE")
    private String sjzylylxValue;

    //数据组织一级分类码值（参见GA DSJ-230-2020附录B的表B.2）
    @TableField("SJZZYJFLVALLUE")
    private String sjzzyjflVallue;

    //数据组织二级分类码值（参见GA DSJ-230-2020附录B的表B.2）
    @TableField("SJZZEJFLVALUE")
    private String sjzzejflValue;

    //存储表主键ID（OBJECT_STORE_INFO表主键ID，可关联数据存储ID）
    @TableField("TABLEINFOID")
    private String tableInfoId;

    //项目空间名称（物理表所在的项目空间）
    @TableField("PROJECT_NAME")
    private String projectName;

    //记录创建时间（默认值Sysdate）
    @TableField("CREATETIME")
    private Date createTime;

    //记录最后一次更新时间（默认值Sysdate；本表数据有修改时，调整本字段内容）
    @TableField("UPDATETIME")
    private Date updateTime;

    //数据来源系统种类（各业务警种根据自身实际业务自定义）
    @TableField("SJLYXTZL")
    private String sjlyxtzl;

    //数据管理机构ID（数据资源的管理机构，从门户的机构信息表中获取）
    @TableField("MANAGE_ORGAN_ID ")
    private String manageOrganId;

    //数据使用机构ID（数据资源的使用机构，从门户的机构信息表中获取）
    @TableField("USE_ORGAN_ID")
    private String useOrganId;
}
