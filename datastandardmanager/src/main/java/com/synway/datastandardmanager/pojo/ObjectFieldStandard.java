package com.synway.datastandardmanager.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 数据库表结构定义（OBJECTFIELD）（源数据表字段定义）
 *
 * @author admin
 */
@ToString
@Data
public class ObjectFieldStandard {
    private Long objectId;//表对象ID
    private int recno;//表中字段顺序
    private String fieldId;//元素编码
    private String fieldName;//标准中数据项英文描述
    private String fieldChineseName;//标准中的字段中文名称
    private String fieldDescribe;//标准中的字段描述
    private Integer fieldType;//字段类型
    private Integer fieldLen;//字段长度
    private String defaultValue;//字段默认值
    private Integer indexType;//索引类型
    private Integer isIndex;//标准中是否存在索引
    private int needValue; //是否必填项
    private Integer isContorl;//是否为布控条件
    private String columnName;//表中的字段名称
    private String memo;//备注
    private Integer tableIndex;//表索引
    private int deleted;//软标识 1.8和1.9弃用
    private Integer isQuery;//是否可查询
    private Integer columnNameState = 1;//表字段是否可用
    private Integer md5Index;//是否参与MD5运算
    private Integer version;//  小版本 版本日期
    private Integer isPrivate;//是否标准协议
    private Integer standardRecno;//标准中序列顺序
    private Integer pkRecno;//主键顺序
    private Integer partitionRecno;//分区列标示
    private Integer clustRecno;//聚集列
    private Integer oraShow;//是否近线显示
    private String needv = "";
    private String fieldtypes = "";
    private String needvalues = "";
    private String odpsPattition = "";
    private String proType = "";
    private String fieldClassId = "";
    private String version0; // 大版本
    private String determinerId = "";// 限定词Id
    private int secretLevel;//字段敏感级别

    // 在添加字段的页面中，需要新增几个字段 20191023
    //  如果选择的是，则需要获取已经存在的index，
    private Boolean clustRecnoStatus = false;//是否为聚集列
    private Boolean pkRecnoStatus = false;//是否为主键列
    private Boolean md5IndexStatus = false;//是否参与MD5运算

    //  20200229 标准表新增了一个字段
    //  是否属于原始库  1：是  0：否  默认值：0
    private int FieldSourceType = 0;

    /**
     * 2021.10.27新增的字段
     */
    //内部标识符
    private String gadsjFieldId = "";

    /**
     * 202100915 数据安全分级
     */
    private String securityLevel = "";

    /**
     * 202100915 数据安全分级中文
     */
    private String securityLevelCh = "";
    private String updater;
    private String creator;

    // 20230615 标准表新增了一个字段
    private Integer sjlygcfl;       // 数据来源构成分类（数据项类型）

    // 对账字段标识
    private String reconciliationType;

    public ObjectFieldStandard() {
    }

    public ObjectFieldStandard(Long objectId, int recno, String fieldId, String fieldName, String fieldChineseName, String fieldDescribe
            , Integer fieldType, int fieldLen, int indexType, int isIndex, int needValue, int isContorl, String columnName,
                               String memo, int tableIndex, int deleted, int isQuery, int columnNameState, int version,
                               Integer isPrivate, int standardRecno, Integer pkRecno, int oraShow, String needv,
                               String fieldtypes, String needvalues, String odpsPattition, String proType, String defaultValue,
                               Integer md5Index, Integer partitionRecno, Integer clustRecno, String version0, String securityLevel) {
        this.objectId = objectId;
        this.recno = recno;
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.fieldChineseName = fieldChineseName;
        this.fieldDescribe = fieldDescribe;
        this.fieldType = fieldType;
        this.fieldLen = fieldLen;
        this.indexType = indexType;
        this.isIndex = isIndex;
        this.needValue = needValue;
        this.isContorl = isContorl;
        this.columnName = columnName;
        this.tableIndex = tableIndex;
        this.deleted = deleted;
        this.isQuery = isQuery;
        this.columnNameState = columnNameState;
        this.version = version;
        this.isPrivate = isPrivate;
        this.standardRecno = standardRecno;
        this.memo = memo;
        this.pkRecno = pkRecno;
        this.oraShow = oraShow;
        this.needv = needv;
        this.fieldtypes = fieldtypes;
        this.needvalues = needvalues;
        this.odpsPattition = odpsPattition;
        this.proType = proType;
        this.defaultValue = defaultValue;
        this.md5Index = md5Index;
        this.partitionRecno = partitionRecno;
        this.clustRecno = clustRecno;
        this.version0 = version0;
        this.securityLevel = securityLevel;
    }

}