package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

@Data
public class FieldInfo {
    //字段序号
    private int no;
    //字段名
    private String fieldName;
    //字段类型
    private String type;
    //字段长度
    private int length;
    //是否是主键，0：否（默认）  1：是
    private int isPrimaryKey;
    //是否是外键，0：否（默认）  1：是
    private int isForeignKey;
    //是否是索引，0：否（默认）  1：是
    private int isIndex;
    //是否是为空，0：否（默认）  1：是
    private int nullAble;
    //空值字段个数
    private int numNulls;
    //空置率
    private double numPercent;
    //取值范围
    private String valueRange;
    //原始字典名
    private String originalDxnry;
    //限定词Id
    private String determinerId;
    //限定词名称
    private String determiner;
    //数据元名Id
    private String synFieldId;
    //数据元名
    private String synFieldName;
    //数据元-字段类型
    private String synFieldType;
    //数据元-字段长度
    private String synFieldLength;
    //数据元-数据要素Id
    private String elementId;
    //数据元-数据要素
    private String elementName;
    //数据元-命名实体Id
    private String entityId;
    //数据元-命名实体
    private String entityName;
    //数据元-数据字典Id
    private String dataDxnryId;
    //数据元-数据字典
    private String dataDxnry;
    //字段名或备注
    private String comments;
    //数据元内部标识符
    private String gadsjFieldId = "";
    // 分区级别：一级分区、二级分区、（非分区列：null）
    private String partitionLevel;

}
