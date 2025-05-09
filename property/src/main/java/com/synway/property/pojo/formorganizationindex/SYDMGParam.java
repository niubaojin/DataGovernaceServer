package com.synway.property.pojo.formorganizationindex;

import lombok.Data;

import java.util.Objects;

/**
 * @author majia
 */
@Data
public class SYDMGParam {

    private String TABLENAME;       // 表名
    private String TABLEPROJECT;    // 项目名
    private String TABLECOMMENT;    // 表注释
    private String TABLETYPE;       // 平台类型
    //hbase的参数
    private String LIFECYCL;
    //hive的参数
    private String LIFECYCLE;       // 生命周期
    private String TABLESIZE;       // 表大小
    private String TABLELASTDATAMODIFIEDTIME;   // 最后修改时间
    private String TABLELASTMETAMODIFIEDTIME;   // 最后修改时间
    private String PARTITIONDATE;               // 分区
    private String INSERTDATATIME;              //
    private String PARTITIONCOUNT;              // 分区记录数
    private String ISPARTITION;                 // 是否分区表
    private String TABLECREATEDTIME;            // 表创建时间
    private String TABLEALLCOUNT;               // 表总记录数
    private String PARTITIONSIZE;               // 分区大小
    private String ERROR;
    private String DATAID = "dataid";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SYDMGParam)) {
            return false;
        }
        SYDMGParam that = (SYDMGParam) o;
        return getTABLENAME().toLowerCase().equals(that.getTABLENAME().toLowerCase()) &&
                getTABLEPROJECT().toLowerCase().equals(that.getTABLEPROJECT().toLowerCase()) &&
                Objects.equals(getTABLETYPE(), that.getTABLETYPE()) &&
                Objects.equals(getPARTITIONDATE(), that.getPARTITIONDATE());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTABLENAME(), getTABLEPROJECT(), getTABLETYPE(), getPARTITIONDATE());
    }


}
