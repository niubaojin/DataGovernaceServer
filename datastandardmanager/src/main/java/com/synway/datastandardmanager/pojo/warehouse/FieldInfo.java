package com.synway.datastandardmanager.pojo.warehouse;

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
    private String gadsjFieldId="";
    // 分区级别：一级分区、二级分区、（非分区列：null）
    private String partitionLevel;

    public String getPartitionLevel() {
        return partitionLevel;
    }

    public void setPartitionLevel(String partitionLevel) {
        this.partitionLevel = partitionLevel;
    }


    public String getGadsjFieldId() {
        return gadsjFieldId;
    }

    public void setGadsjFieldId(String gadsjFieldId) {
        this.gadsjFieldId = gadsjFieldId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(int isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public int getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(int isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public int getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(int isIndex) {
        this.isIndex = isIndex;
    }

    public int getNullAble() {
        return nullAble;
    }

    public void setNullAble(int nullAble) {
        this.nullAble = nullAble;
    }

    public int getNumNulls() {
        return numNulls;
    }

    public void setNumNulls(int numNulls) {
        this.numNulls = numNulls;
    }

    public double getNumPercent() {
        return numPercent;
    }

    public void setNumPercent(double numPercent) {
        this.numPercent = numPercent;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    public String getOriginalDxnry() {
        return originalDxnry;
    }

    public void setOriginalDxnry(String originalDxnry) {
        this.originalDxnry = originalDxnry;
    }

    public String getDeterminerId() {
        return determinerId;
    }

    public void setDeterminerId(String determinerId) {
        this.determinerId = determinerId;
    }

    public String getDeterminer() {
        return determiner;
    }

    public void setDeterminer(String determiner) {
        this.determiner = determiner;
    }

    public String getSynFieldId() {
        return synFieldId;
    }

    public void setSynFieldId(String synFieldId) {
        this.synFieldId = synFieldId;
    }

    public String getSynFieldName() {
        return synFieldName;
    }

    public void setSynFieldName(String synFieldName) {
        this.synFieldName = synFieldName;
    }

    public String getSynFieldType() {
        return synFieldType;
    }

    public void setSynFieldType(String synFieldType) {
        this.synFieldType = synFieldType;
    }

    public String getSynFieldLength() {
        return synFieldLength;
    }

    public void setSynFieldLength(String synFieldLength) {
        this.synFieldLength = synFieldLength;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDataDxnryId() {
        return dataDxnryId;
    }

    public void setDataDxnryId(String dataDxnryId) {
        this.dataDxnryId = dataDxnryId;
    }

    public String getDataDxnry() {
        return dataDxnry;
    }

    public void setDataDxnry(String dataDxnry) {
        this.dataDxnry = dataDxnry;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
