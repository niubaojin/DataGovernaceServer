package com.synway.datastandardmanager.pojo.sourcedata;


import java.util.Date;

/**
 * @author wangdongwei
 * @ClassName FieldResourceInfoPojo
 * @description  数据项信息表对应的实体类
 * @date 2020/9/15 10:22
 */
@Deprecated
public class FieldResourceInfoPojo {
    // 数据项编号,元素编码，对应objectfield.FieldID内容
    private String sjxbh=" ";
    // 表协议编码，对应公共数据表主键
    private String tableId=" ";
    // 数据资源标识符
    private String sjzybsf=" ";
    // 是否标识项
    private Boolean sfbsx;
    // 数据项中文名称
    private String sjxzwmc=" ";
    // 数据项英文名称,建表字段英文名，对应objectfield.colname
    private String sjxywm=" ";
    // 数据元内部标识符,元素编码
    private String sjynbbsf=" ";
    // 数据项标识符
    private String sjxbsf=" ";
    // 备注
    private String bz=" ";
    // 是否查询条件
    private Boolean sfcxtj;
    // 是否订阅条件
    private Boolean sfdytj;
    //  查询/订阅匹配方式
    private String cxdyppfs=" ";
    //  是否必填
    private Boolean sfbt;
    //  字段性质分类代码
    private String zdfl=" ";
    //  字段敏感度分类代码
    private String zdmgdfldm=" ";
    // 注册时间
    private Date zcsj;
    // 更新时间
    private String gxsj="";
    // 操作人
    private String czr ="标准管理页面";

    //标准字段内部标识符
    private String gadsjFieldId="";

    public String getGadsjFieldId() {
        return gadsjFieldId;
    }

    public void setGadsjFieldId(String gadsjFieldId) {
        this.gadsjFieldId = gadsjFieldId;
    }

    public String getSjxbh() {
        return sjxbh;
    }

    public void setSjxbh(String sjxbh) {
        this.sjxbh = sjxbh;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getSjzybsf() {
        return sjzybsf;
    }

    public void setSjzybsf(String sjzybsf) {
        this.sjzybsf = sjzybsf;
    }

    public Boolean getSfbsx() {
        return sfbsx;
    }

    public void setSfbsx(Boolean sfbsx) {
        this.sfbsx = sfbsx;
    }

    public String getSjxzwmc() {
        return sjxzwmc;
    }

    public void setSjxzwmc(String sjxzwmc) {
        this.sjxzwmc = sjxzwmc;
    }

    public String getSjxywm() {
        return sjxywm;
    }

    public void setSjxywm(String sjxywm) {
        this.sjxywm = sjxywm;
    }

    public String getSjynbbsf() {
        return sjynbbsf;
    }

    public void setSjynbbsf(String sjynbbsf) {
        this.sjynbbsf = sjynbbsf;
    }

    public String getSjxbsf() {
        return sjxbsf;
    }

    public void setSjxbsf(String sjxbsf) {
        this.sjxbsf = sjxbsf;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Boolean getSfcxtj() {
        return sfcxtj;
    }

    public void setSfcxtj(Boolean sfcxtj) {
        this.sfcxtj = sfcxtj;
    }

    public Boolean getSfdytj() {
        return sfdytj;
    }

    public void setSfdytj(Boolean sfdytj) {
        this.sfdytj = sfdytj;
    }

    public String getCxdyppfs() {
        return cxdyppfs;
    }

    public void setCxdyppfs(String cxdyppfs) {
        this.cxdyppfs = cxdyppfs;
    }

    public Boolean getSfbt() {
        return sfbt;
    }

    public void setSfbt(Boolean sfbt) {
        this.sfbt = sfbt;
    }

    public String getZdfl() {
        return zdfl;
    }

    public void setZdfl(String zdfl) {
        this.zdfl = zdfl;
    }

    public String getZdmgdfldm() {
        return zdmgdfldm;
    }

    public void setZdmgdfldm(String zdmgdfldm) {
        this.zdmgdfldm = zdmgdfldm;
    }

    public Date getZcsj() {
        return zcsj;
    }

    public void setZcsj(Date zcsj) {
        this.zcsj = zcsj;
    }

    public String getGxsj() {
        return gxsj;
    }

    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }
}
