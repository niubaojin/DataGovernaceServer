package com.synway.datastandardmanager.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class InputObjectCreate {
    public Integer inputSysId;
    public String inputObjEngName;
    public String inputObjChiName;
    public Integer inputIobjSource;
    public Integer iorStatus;
    public Integer outSysId;
    public String outObjEngName;
    public String outObjChiName;
    public Integer outOobjSource;
    // 新增一个 英文表名这个参数
    public String tableRealName;
    public String objGuid;
    // 数据仓库的 data_id 和数据仓库的 table_id
    public String dataId;
    public String tableId;
    public String centerId;
    // 20200519 新增数据的插入时间
    private Date insertDate;

    // 20231202:源协议表名
    private String sourceTableName;

}
