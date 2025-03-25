package com.synway.datastandardmanager.pojo.fielddeterminermanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 维护管理限定词信息
 * @author wdw
 * @version 1.0
 * @date 2021/7/5 10:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDeterminer implements Serializable {

    private static final long serialVersionUID = 3677103325829268008L;
    private int num;
    /**
     * 限定词内部编码
     */
    @NotNull(message = "【主键ID】不能为空")
    @Size(max=50,message = "【主键ID】长度不能超过50")
    private String determinerId;

    /**
     * 限定词中文名称
     */
    @NotNull(message = "【限定词中文名称】不能为空")
    @Size(max=200,message = "【限定词中文名称】长度不能超过50")
    private String dChinseName;

    /**
     * 限定词标示符
     * 中文名首字母，有重复的情况加1位流水号
     */
//    @NotNull(message = "【限定词标示符】不能为空")
    @Size(max=32,message = "【限定词标示符】长度不能超过32")
    private String dName="";

    /**
     * 说明信息
     */
    @Size(max=800,message = "【说明信息】长度不能超过800")
    private String memo="";

    /**
     * 修订人
     */
    @NotNull(message = "【修订人】不能为空")
    @Size(max=50,message = "【修订人】长度不能超过50")
    private String author;

    /**
     * 修订时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date modDate;

    /**
     * 限定词状态  与数据元保持一致  01：新建原始状态  05：发布   07：废纸禁用
     * 前端必传
     */
    private String determinerStateNum = "05";

    /**
     * 限定词状态翻译
     */
    private String determinerState;


    /**
     * 限定词类型 前端必传
     * 1：公安标准  0：非标准
     */
    private int determinerTypeNum;

    /**
     * 限定词类型翻译
     */
    private String determinerType;

    /**
     * 大版本
     * 目前没有相关规范 默认为 1.0
     */
    @Size(max=10,message = "【版本信息】长度不能超过10")
    private String versions = "";


    /**
     * 版本发布日期，日期的表示采用YYYYMMDD的格式
     * 数据库中是int类型
     */
    private Integer releaseDate;

    /**
     * 制作厂商  FACTURER
     * 厂商标准码表代码值
     */
    @NotNull(message = "【制作厂商】不能为空")
    @Size(max=100,message = "【制作厂商】长度不能超过100")
    private String facturer;


    /**
     * 提交机构  REG_ORG
     */
    @NotNull(message = "【提交机构】不能为空")
    @Size(max=50,message = "【提交机构】长度不能超过50")
    private String regOrg;


    /**
     * 审核时间  提交创建日期，采用YYYYMMDD的格式
     */
    @JsonFormat(pattern = "yyyyMMdd",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyyMMdd")
    private Date onDate;

    /**
     * 历史表主键
     */
    private String fieldDeterminerVersion;

}
