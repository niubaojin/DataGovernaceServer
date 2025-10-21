package com.synway.datastandardmanager.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName PublicDataInfo
 * @description 数据资产目录表 存储在数据库 synlte
 * @date 2020/9/7 19:57
 */
@Data
public class PublicDataInfoVO implements Serializable {
    // 标准数据项集编码(TABLEID)
    private String SJXJBM = "";
    // 数据的SOURCEID
    private String SOURCEID = "";
    // 数据资源事权单位_事权单位代码
    private String SJZYSQDW_SQDWDM = "";
    // 数据获取方式 01侦控 02管控 03管理 04公开
    private String SJHQFS = "";
    private String SJHQFS_CH = "";
    // 应用系统编号
    private String YYXTBH = "";
    // 数据资源管理单位_公安机关机构代码
    private String SJZYGLDW_GAJGJGDM = "";
    // 数据资源位置 01部 02省 03市 04 网站
    private String SJZYWZ = "";
    private String SJZYWZ_CH = "";
    // 数据资源存储分中心
    private String SJZYCCFZX = "";
    // 数据资源标识符 R-数据资源管理单位机构代码-8位数字流水号
    private String SJZYBSF = "";
    // 数据资源目录编号
    private String SJZYMLBH = "";
    // 数据项集英文名称
    private String TABLENAME = "";
    // 数据项集中文名称
    private String SJXJZWMC = "";
    // 数据资源标签1
    private String SJZYBQ1 = "";
    private String SJZYBQ2 = "";
    private String SJZYBQ3 = "";
    private String SJZYBQ4 = "";
    private String SJZYBQ5 = "";
    private String SJZYBQ6 = "";
    // 格式描述
    private String GSMS = "";
    //资源状态（停用/启用）
    private String ZYZT = "";
    // 数据表创建时间
    private String SJBCJSJ = "";
    // 最早数据时间
    private String ZZSJSJ = "";
    // 更新说明
    private String GXSM = "";
    // 发布时间
    private String FBSJ = "";
    // 源应用系统建设公司
    private String YYYXTJSGS = "";
    // 源应用系统管理单位
    private String YYYXTGLDW = "";
    // 源应用系统名称
    private String YYYXTMC = "";
    // 源应用系统编号
    private String YYYXTMM = "";
    // 事权单位联系人
    private String SQDWLXR = "";
    // 事权单位联系电话
    private String SQDWLXDH = "";
    // 接入服务方
    private String JRFWF = "";
    // 提供（接入）方式
    private String TGJRFS = "";
    // 接入服务人
    private String JRFWR = "";
    // 接入服务联系电话
    private String JRFWLXDH = "";
    // 数据接入说明
    private String SJJRSM = "";
    // 最早数据更新时间
    private String ZZGXSJ = "";
    // 数据资源管理单位名称
    private String SJZYGLMC = "";
    // 数据资源事权单位名称
    private String SJZYSQDWMC = "";
    // 数据资源来源类型
    private String SJZYLYLX = "";
    // 数据组织一级分类  代码
    private String SJZZYJFL = "";
    // 数据组织二级分类   代码
    private String SJZZEJFL = "";
    // 敏感级别
    private String SJFL = "";
    // 更新时间
    private String GXSJ = "";
    // 更新操作人
    private String GXCZR = "";
    // 数据组织一级分类  中文名
    private String SJZZYJFL_CN = "";
    //数据组织二级分类   中文名
    private String SJZZEJFL_CN = "";
    // 数据资源来源类型  中文名  二级分类的中文名
    private String SJZYLYLX_CN = "";
    // 数据资源来源类型  一级分类的中文名
    private String SJZYLYYJLX_CN = "";

    /**
     * 数据资源更新周期 20210510使用
     */
    private String SJZYGXZQ = "";

    /**
     * 20210510 新增的内容
     * 数据量规模
     */
    private String dataNumberScale = "";
    /**
     * 数据存储规模
     */
    private String dataStoreScale = "";
    /**
     * 数据更新方式
     */
    private String updateType = "";

    /**
     * 数据来源描述
     */
    private String avgStore = "";

    /**
     * 数据更新时间点
     */
    private String avgRecord = "";

    /**
     * 标签数据
     */
    private List<LabelsEntity> labels;

    /**
     * 20210610 新增 码值 SJZYLYLXVALUE
     * 数据资源来源类型码值
     */
    private String SJZYLYLXVALUE = "";
    /**
     * 数据组织一级分类码值
     */
    private String SJZZYJFLVALLUE = "";
    /**
     * 数据组织二级分类码值
     */
    private String SJZZEJFLVALUE = "";


    @JsonProperty("SJZYGXZQ")
    @JSONField(name = "SJZYGXZQ")
    public String getSJZYGXZQ() {
        return SJZYGXZQ;
    }

    public void setSJZYGXZQ(String SJZYGXZQ) {
        this.SJZYGXZQ = SJZYGXZQ;
    }

    @JsonProperty("SJZYLYYJLX_CN")
    @JSONField(name = "SJZYLYYJLX_CN")
    public String getSJZYLYYJLX_CN() {
        return SJZYLYYJLX_CN;
    }

    public void setSJZYLYYJLX_CN(String SJZYLYYJLX_CN) {
        this.SJZYLYYJLX_CN = SJZYLYYJLX_CN;
    }

    @JsonProperty("SJHQFS_CH")
    @JSONField(name = "SJHQFS_CH")
    public String getSJHQFS_CH() {
        return SJHQFS_CH;
    }

    public void setSJHQFS_CH(String SJHQFS_CH) {
        this.SJHQFS_CH = SJHQFS_CH;
    }

    @JsonProperty("SJZYWZ_CH")
    @JSONField(name = "SJZYWZ_CH")
    public String getSJZYWZ_CH() {
        return SJZYWZ_CH;
    }

    public void setSJZYWZ_CH(String SJZYWZ_CH) {
        this.SJZYWZ_CH = SJZYWZ_CH;
    }

    @JsonProperty("SJZZYJFL_CN")
    @JSONField(name = "SJZZYJFL_CN")
    public String getSJZZYJFL_CN() {
        return SJZZYJFL_CN;
    }

    public void setSJZZYJFL_CN(String SJZZYJFL_CN) {
        this.SJZZYJFL_CN = SJZZYJFL_CN;
    }

    @JsonProperty("SJZZEJFL_CN")
    @JSONField(name = "SJZZEJFL_CN")
    public String getSJZZEJFL_CN() {
        return SJZZEJFL_CN;
    }

    public void setSJZZEJFL_CN(String SJZZEJFL_CN) {
        this.SJZZEJFL_CN = SJZZEJFL_CN;
    }

    @JsonProperty("SJZYLYLX_CN")
    @JSONField(name = "SJZYLYLX_CN")
    public String getSJZYLYLX_CN() {
        return SJZYLYLX_CN;
    }

    public void setSJZYLYLX_CN(String SJZYLYLX_CN) {
        this.SJZYLYLX_CN = SJZYLYLX_CN;
    }

    @JsonProperty("SJXJBM")
    @JSONField(name = "SJXJBM")
    public String getSJXJBM() {
        return SJXJBM;
    }

    public void setSJXJBM(String SJXJBM) {
        this.SJXJBM = SJXJBM;
    }

    @JsonProperty("SOURCEID")
    @JSONField(name = "SOURCEID")
    public String getSOURCEID() {
        return SOURCEID;
    }

    public void setSOURCEID(String SOURCEID) {
        this.SOURCEID = SOURCEID;
    }

    @JsonProperty("SJZYSQDW_SQDWDM")
    @JSONField(name = "SJZYSQDW_SQDWDM")
    public String getSJZYSQDW_SQDWDM() {
        return SJZYSQDW_SQDWDM;
    }

    public void setSJZYSQDW_SQDWDM(String SJZYSQDW_SQDWDM) {
        this.SJZYSQDW_SQDWDM = SJZYSQDW_SQDWDM;
    }

    @JsonProperty("SJHQFS")
    @JSONField(name = "SJHQFS")
    public String getSJHQFS() {
        return SJHQFS;
    }

    public void setSJHQFS(String SJHQFS) {
        this.SJHQFS = SJHQFS;
    }

    @JsonProperty("YYXTBH")
    @JSONField(name = "YYXTBH")
    public String getYYXTBH() {
        return YYXTBH;
    }

    public void setYYXTBH(String YYXTBH) {
        this.YYXTBH = YYXTBH;
    }

    @JsonProperty("SJZYGLDW_GAJGJGDM")
    @JSONField(name = "SJZYGLDW_GAJGJGDM")
    public String getSJZYGLDW_GAJGJGDM() {
        return SJZYGLDW_GAJGJGDM;
    }

    public void setSJZYGLDW_GAJGJGDM(String SJZYGLDW_GAJGJGDM) {
        this.SJZYGLDW_GAJGJGDM = SJZYGLDW_GAJGJGDM;
    }

    @JsonProperty("SJZYWZ")
    @JSONField(name = "SJZYWZ")
    public String getSJZYWZ() {
        return SJZYWZ;
    }

    public void setSJZYWZ(String SJZYWZ) {
        this.SJZYWZ = SJZYWZ;
    }

    @JsonProperty("SJZYCCFZX")
    @JSONField(name = "SJZYCCFZX")
    public String getSJZYCCFZX() {
        return SJZYCCFZX;
    }

    public void setSJZYCCFZX(String SJZYCCFZX) {
        this.SJZYCCFZX = SJZYCCFZX;
    }

    @JsonProperty("SJZYBSF")
    @JSONField(name = "SJZYBSF")
    public String getSJZYBSF() {
        return SJZYBSF;
    }

    public void setSJZYBSF(String SJZYBSF) {
        this.SJZYBSF = SJZYBSF;
    }

    @JsonProperty("SJZYMLBH")
    @JSONField(name = "SJZYMLBH")
    public String getSJZYMLBH() {
        return SJZYMLBH;
    }

    public void setSJZYMLBH(String SJZYMLBH) {
        this.SJZYMLBH = SJZYMLBH;
    }

    @JsonProperty("TABLENAME")
    @JSONField(name = "TABLENAME")
    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    @JsonProperty("SJXJZWMC")
    @JSONField(name = "SJXJZWMC")
    public String getSJXJZWMC() {
        return SJXJZWMC;
    }

    public void setSJXJZWMC(String SJXJZWMC) {
        this.SJXJZWMC = SJXJZWMC;
    }

    @JsonProperty("SJZYBQ1")
    @JSONField(name = "SJZYBQ1")
    public String getSJZYBQ1() {
        return SJZYBQ1;
    }

    public void setSJZYBQ1(String SJZYBQ1) {
        this.SJZYBQ1 = SJZYBQ1;
    }

    @JsonProperty("SJZYBQ2")
    @JSONField(name = "SJZYBQ2")
    public String getSJZYBQ2() {
        return SJZYBQ2;
    }

    public void setSJZYBQ2(String SJZYBQ2) {
        this.SJZYBQ2 = SJZYBQ2;
    }

    @JsonProperty("SJZYBQ3")
    @JSONField(name = "SJZYBQ3")
    public String getSJZYBQ3() {
        return SJZYBQ3;
    }

    public void setSJZYBQ3(String SJZYBQ3) {
        this.SJZYBQ3 = SJZYBQ3;
    }

    @JsonProperty("SJZYBQ4")
    @JSONField(name = "SJZYBQ4")
    public String getSJZYBQ4() {
        return SJZYBQ4;
    }

    public void setSJZYBQ4(String SJZYBQ4) {
        this.SJZYBQ4 = SJZYBQ4;
    }

    @JsonProperty("SJZYBQ5")
    @JSONField(name = "SJZYBQ5")
    public String getSJZYBQ5() {
        return SJZYBQ5;
    }

    public void setSJZYBQ5(String SJZYBQ5) {
        this.SJZYBQ5 = SJZYBQ5;
    }

    @JsonProperty("SJZYBQ6")
    @JSONField(name = "SJZYBQ6")
    public String getSJZYBQ6() {
        return SJZYBQ6;
    }

    public void setSJZYBQ6(String SJZYBQ6) {
        this.SJZYBQ6 = SJZYBQ6;
    }

    @JsonProperty("GSMS")
    @JSONField(name = "GSMS")
    public String getGSMS() {
        return GSMS;
    }

    public void setGSMS(String GSMS) {
        this.GSMS = GSMS;
    }

    @JsonProperty("ZYZT")
    @JSONField(name = "ZYZT")
    public String getZYZT() {
        return ZYZT;
    }

    public void setZYZT(String ZYZT) {
        this.ZYZT = ZYZT;
    }

    @JsonProperty("SJBCJSJ")
    @JSONField(name = "SJBCJSJ")
    public String getSJBCJSJ() {
        return SJBCJSJ;
    }

    public void setSJBCJSJ(String SJBCJSJ) {
        this.SJBCJSJ = SJBCJSJ;
    }

    @JsonProperty("ZZSJSJ")
    @JSONField(name = "ZZSJSJ")
    public String getZZSJSJ() {
        return ZZSJSJ;
    }

    public void setZZSJSJ(String ZZSJSJ) {
        this.ZZSJSJ = ZZSJSJ;
    }

    @JsonProperty("GXSM")
    @JSONField(name = "GXSM")
    public String getGXSM() {
        return GXSM;
    }

    public void setGXSM(String GXSM) {
        this.GXSM = GXSM;
    }

    @JsonProperty("FBSJ")
    @JSONField(name = "FBSJ")
    public String getFBSJ() {
        return FBSJ;
    }

    public void setFBSJ(String FBSJ) {
        this.FBSJ = FBSJ;
    }

    @JsonProperty("YYYXTJSGS")
    @JSONField(name = "YYYXTJSGS")
    public String getYYYXTJSGS() {
        return YYYXTJSGS;
    }

    public void setYYYXTJSGS(String YYYXTJSGS) {
        this.YYYXTJSGS = YYYXTJSGS;
    }

    @JsonProperty("YYYXTGLDW")
    @JSONField(name = "YYYXTGLDW")
    public String getYYYXTGLDW() {
        return YYYXTGLDW;
    }

    public void setYYYXTGLDW(String YYYXTGLDW) {
        this.YYYXTGLDW = YYYXTGLDW;
    }

    @JsonProperty("YYYXTMC")
    @JSONField(name = "YYYXTMC")
    public String getYYYXTMC() {
        return YYYXTMC;
    }

    public void setYYYXTMC(String YYYXTMC) {
        this.YYYXTMC = YYYXTMC;
    }

    @JsonProperty("YYYXTMM")
    @JSONField(name = "YYYXTMM")
    public String getYYYXTMM() {
        return YYYXTMM;
    }

    public void setYYYXTMM(String YYYXTMM) {
        this.YYYXTMM = YYYXTMM;
    }

    @JsonProperty("SQDWLXR")
    @JSONField(name = "SQDWLXR")
    public String getSQDWLXR() {
        return SQDWLXR;
    }

    public void setSQDWLXR(String SQDWLXR) {
        this.SQDWLXR = SQDWLXR;
    }

    @JsonProperty("SQDWLXDH")
    @JSONField(name = "SQDWLXDH")
    public String getSQDWLXDH() {
        return SQDWLXDH;
    }

    public void setSQDWLXDH(String SQDWLXDH) {
        this.SQDWLXDH = SQDWLXDH;
    }

    @JsonProperty("JRFWF")
    @JSONField(name = "JRFWF")
    public String getJRFWF() {
        return JRFWF;
    }

    public void setJRFWF(String JRFWF) {
        this.JRFWF = JRFWF;
    }

    @JsonProperty("TGJRFS")
    @JSONField(name = "TGJRFS")
    public String getTGJRFS() {
        return TGJRFS;
    }

    public void setTGJRFS(String TGJRFS) {
        this.TGJRFS = TGJRFS;
    }

    @JsonProperty("JRFWR")
    @JSONField(name = "JRFWR")
    public String getJRFWR() {
        return JRFWR;
    }

    public void setJRFWR(String JRFWR) {
        this.JRFWR = JRFWR;
    }

    @JsonProperty("JRFWLXDH")
    @JSONField(name = "JRFWLXDH")
    public String getJRFWLXDH() {
        return JRFWLXDH;
    }

    public void setJRFWLXDH(String JRFWLXDH) {
        this.JRFWLXDH = JRFWLXDH;
    }

    @JsonProperty("SJJRSM")
    @JSONField(name = "SJJRSM")
    public String getSJJRSM() {
        return SJJRSM;
    }

    public void setSJJRSM(String SJJRSM) {
        this.SJJRSM = SJJRSM;
    }

    @JsonProperty("ZZGXSJ")
    @JSONField(name = "ZZGXSJ")
    public String getZZGXSJ() {
        return ZZGXSJ;
    }

    public void setZZGXSJ(String ZZGXSJ) {
        this.ZZGXSJ = ZZGXSJ;
    }

    @JsonProperty("SJZYGLMC")
    @JSONField(name = "SJZYGLMC")
    public String getSJZYGLMC() {
        return SJZYGLMC;
    }

    public void setSJZYGLMC(String SJZYGLMC) {
        this.SJZYGLMC = SJZYGLMC;
    }

    @JsonProperty("SJZYSQDWMC")
    @JSONField(name = "SJZYSQDWMC")
    public String getSJZYSQDWMC() {
        return SJZYSQDWMC;
    }

    public void setSJZYSQDWMC(String SJZYSQDWMC) {
        this.SJZYSQDWMC = SJZYSQDWMC;
    }

    @JsonProperty("SJZYLYLX")
    @JSONField(name = "SJZYLYLX")
    public String getSJZYLYLX() {
        return SJZYLYLX;
    }

    public void setSJZYLYLX(String SJZYLYLX) {
        this.SJZYLYLX = SJZYLYLX;
    }

    @JsonProperty("SJZZYJFL")
    @JSONField(name = "SJZZYJFL")
    public String getSJZZYJFL() {
        return SJZZYJFL;
    }

    public void setSJZZYJFL(String SJZZYJFL) {
        this.SJZZYJFL = SJZZYJFL;
    }

    @JsonProperty("SJZZEJFL")
    @JSONField(name = "SJZZEJFL")
    public String getSJZZEJFL() {
        return SJZZEJFL;
    }

    public void setSJZZEJFL(String SJZZEJFL) {
        this.SJZZEJFL = SJZZEJFL;
    }

    @JsonProperty("SJFL")
    @JSONField(name = "SJFL")
    public String getSJFL() {
        return SJFL;
    }

    public void setSJFL(String SJFL) {
        this.SJFL = SJFL;
    }

    @JsonProperty("GXSJ")
    @JSONField(name = "GXSJ")
    public String getGXSJ() {
        return GXSJ;
    }

    public void setGXSJ(String GXSJ) {
        this.GXSJ = GXSJ;
    }

    @JsonProperty("GXCZR")
    @JSONField(name = "GXCZR")
    public String getGXCZR() {
        return GXCZR;
    }

    public void setGXCZR(String GXCZR) {
        this.GXCZR = GXCZR;
    }
}
