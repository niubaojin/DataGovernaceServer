package com.synway.property.pojo.datastoragemonitor;


import java.util.Date;

/**
 * 资源服务平台所有资源
 * @author 数据接入
 */
public class ClassifyInterfaceData {
    // 数据资源事权单位_事权单位代码
    private String	SJZYSQDW_SQDWDM;
    // 数据获取方式 01侦控 02管控 03管理 04公开
    private String	SJHQFS;
    // 应用系统编号
    private String	YYXTBH;
    // 数据资源管理单位_公安机关机构代码
    private String	SJZYGLDW_GAJGJGDM;
    // 数据资源位置 01部 02省 03市 04 网站
    private String	SJZYWZ;
    // 数据资源存储分中心
    private String	SJZYCCFZX;
    // 数据资源标识符 R-数据资源管理单位机构代码-8位数字流水号
    private String	SJZYBSF	;
    // 数据资源目录编号
    private String	SJZYMLBH;
    // 对应标准数据项集编码
    private String	SJXJBM;
    // 数据项集中文名称
    private String	SJXJZWMC;
    // 数据资源标签1 1人 2物 3组织 4虚拟标识 5时空
    private String	SJZYBQ1;
    //
    private String	SJZYBQ2	;
    private String	SJZYBQ3	;
    private String	SJZYBQ4	;
    private String	SJZYBQ5	;
    //数据资源更新周期
    private String	SJZYGXZQ;
    // 存量数据记录规模
    private String	CLSJJLGM;
    // 存量数据存储规模
    private String	CLSJCCGM;
    // 增量数据记录规模
    private String	ZLSJJLGM;
    // 增量数据存储规模
    private String	ZLSJCCGM;
    // 数据资源存储周期
    private String	SJZYCCZQ;
    // 数据资源来源
    private String	SJZYLY;
    // 数据组织一级分类
    private String	SJYJFL;
    //  数据组织二级分类
    private String	SJEJFL;
    //  数据名称
    private String	NAME;
    // 数据资源描述
    private String	MEMO;
    // 该行数据插入oracle库的时间
    private Date INSERT_DATE;
    private String	TABLEID;
    // 表名
    private String TABLENAME;

    private String DATASTATUS;


    public String getTABLEID() {
        return TABLEID;
    }

    public void setTABLEID(String TABLEID) {
        this.TABLEID = TABLEID;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getDATASTATUS() {
        return DATASTATUS;
    }

    public void setDATASTATUS(String DATASTATUS) {
        this.DATASTATUS = DATASTATUS;
    }

    public String getSJZYSQDW_SQDWDM() {
        return SJZYSQDW_SQDWDM;
    }

    public void setSJZYSQDW_SQDWDM(String SJZYSQDW_SQDWDM) {
        this.SJZYSQDW_SQDWDM = SJZYSQDW_SQDWDM;
    }

    public String getSJHQFS() {
        return SJHQFS;
    }

    public void setSJHQFS(String SJHQFS) {
        this.SJHQFS = SJHQFS;
    }

    public String getYYXTBH() {
        return YYXTBH;
    }

    public void setYYXTBH(String YYXTBH) {
        this.YYXTBH = YYXTBH;
    }

    public String getSJZYGLDW_GAJGJGDM() {
        return SJZYGLDW_GAJGJGDM;
    }

    public void setSJZYGLDW_GAJGJGDM(String SJZYGLDW_GAJGJGDM) {
        this.SJZYGLDW_GAJGJGDM = SJZYGLDW_GAJGJGDM;
    }

    public String getSJZYWZ() {
        return SJZYWZ;
    }

    public void setSJZYWZ(String SJZYWZ) {
        this.SJZYWZ = SJZYWZ;
    }

    public String getSJZYCCFZX() {
        return SJZYCCFZX;
    }

    public void setSJZYCCFZX(String SJZYCCFZX) {
        this.SJZYCCFZX = SJZYCCFZX;
    }

    public String getSJZYBSF() {
        return SJZYBSF;
    }

    public void setSJZYBSF(String SJZYBSF) {
        this.SJZYBSF = SJZYBSF;
    }

    public String getSJZYMLBH() {
        return SJZYMLBH;
    }

    public void setSJZYMLBH(String SJZYMLBH) {
        this.SJZYMLBH = SJZYMLBH;
    }

    public String getSJXJBM() {
        return SJXJBM;
    }

    public void setSJXJBM(String SJXJBM) {
        this.SJXJBM = SJXJBM;
    }

    public String getSJXJZWMC() {
        return SJXJZWMC;
    }

    public void setSJXJZWMC(String SJXJZWMC) {
        this.SJXJZWMC = SJXJZWMC;
    }

    public String getSJZYBQ1() {
        return SJZYBQ1;
    }

    public void setSJZYBQ1(String SJZYBQ1) {
        this.SJZYBQ1 = SJZYBQ1;
    }

    public String getSJZYBQ2() {
        return SJZYBQ2;
    }

    public void setSJZYBQ2(String SJZYBQ2) {
        this.SJZYBQ2 = SJZYBQ2;
    }

    public String getSJZYBQ3() {
        return SJZYBQ3;
    }

    public void setSJZYBQ3(String SJZYBQ3) {
        this.SJZYBQ3 = SJZYBQ3;
    }

    public String getSJZYBQ4() {
        return SJZYBQ4;
    }

    public void setSJZYBQ4(String SJZYBQ4) {
        this.SJZYBQ4 = SJZYBQ4;
    }

    public String getSJZYBQ5() {
        return SJZYBQ5;
    }

    public void setSJZYBQ5(String SJZYBQ5) {
        this.SJZYBQ5 = SJZYBQ5;
    }

    public String getSJZYGXZQ() {
        return SJZYGXZQ;
    }

    public void setSJZYGXZQ(String SJZYGXZQ) {
        this.SJZYGXZQ = SJZYGXZQ;
    }

    public String getCLSJJLGM() {
        return CLSJJLGM;
    }

    public void setCLSJJLGM(String CLSJJLGM) {
        this.CLSJJLGM = CLSJJLGM;
    }

    public String getCLSJCCGM() {
        return CLSJCCGM;
    }

    public void setCLSJCCGM(String CLSJCCGM) {
        this.CLSJCCGM = CLSJCCGM;
    }

    public String getZLSJJLGM() {
        return ZLSJJLGM;
    }

    public void setZLSJJLGM(String ZLSJJLGM) {
        this.ZLSJJLGM = ZLSJJLGM;
    }

    public String getZLSJCCGM() {
        return ZLSJCCGM;
    }

    public void setZLSJCCGM(String ZLSJCCGM) {
        this.ZLSJCCGM = ZLSJCCGM;
    }

    public String getSJZYCCZQ() {
        return SJZYCCZQ;
    }

    public void setSJZYCCZQ(String SJZYCCZQ) {
        this.SJZYCCZQ = SJZYCCZQ;
    }

    public String getSJZYLY() {
        return SJZYLY;
    }

    public void setSJZYLY(String SJZYLY) {
        this.SJZYLY = SJZYLY;
    }

    public String getSJYJFL() {
        return SJYJFL;
    }

    public void setSJYJFL(String SJYJFL) {
        this.SJYJFL = SJYJFL;
    }

    public String getSJEJFL() {
        return SJEJFL;
    }

    public void setSJEJFL(String SJEJFL) {
        this.SJEJFL = SJEJFL;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMEMO() {
        return MEMO;
    }

    public void setMEMO(String MEMO) {
        this.MEMO = MEMO;
    }

    public Date getINSERT_DATE() {
        return INSERT_DATE;
    }

    public void setINSERT_DATE(Date INSERT_DATE) {
        this.INSERT_DATE = INSERT_DATE;
    }

    @Override
    public String toString() {
        return "ClassifyInterfaceData{" +
                "SJZYSQDW_SQDWDM='" + SJZYSQDW_SQDWDM + '\'' +
                ", SJHQFS='" + SJHQFS + '\'' +
                ", YYXTBH='" + YYXTBH + '\'' +
                ", SJZYGLDW_GAJGJGDM='" + SJZYGLDW_GAJGJGDM + '\'' +
                ", SJZYWZ='" + SJZYWZ + '\'' +
                ", SJZYCCFZX='" + SJZYCCFZX + '\'' +
                ", SJZYBSF='" + SJZYBSF + '\'' +
                ", SJZYMLBH='" + SJZYMLBH + '\'' +
                ", SJXJBM='" + SJXJBM + '\'' +
                ", SJXJZWMC='" + SJXJZWMC + '\'' +
                ", SJZYBQ1='" + SJZYBQ1 + '\'' +
                ", SJZYBQ2='" + SJZYBQ2 + '\'' +
                ", SJZYBQ3='" + SJZYBQ3 + '\'' +
                ", SJZYBQ4='" + SJZYBQ4 + '\'' +
                ", SJZYBQ5='" + SJZYBQ5 + '\'' +
                ", SJZYGXZQ='" + SJZYGXZQ + '\'' +
                ", CLSJJLGM='" + CLSJJLGM + '\'' +
                ", CLSJCCGM='" + CLSJCCGM + '\'' +
                ", ZLSJJLGM='" + ZLSJJLGM + '\'' +
                ", ZLSJCCGM='" + ZLSJCCGM + '\'' +
                ", SJZYCCZQ='" + SJZYCCZQ + '\'' +
                ", SJZYLY='" + SJZYLY + '\'' +
                ", SJYJFL='" + SJYJFL + '\'' +
                ", SJEJFL='" + SJEJFL + '\'' +
                ", NAME='" + NAME + '\'' +
                ", MEMO='" + MEMO + '\'' +
                ", INSERT_DATE=" + INSERT_DATE +
                ", TABLEID='" + TABLEID + '\'' +
                ", TABLENAME='" + TABLENAME + '\'' +
                ", DATASTATUS='" + DATASTATUS + '\'' +
                '}';
    }
}
