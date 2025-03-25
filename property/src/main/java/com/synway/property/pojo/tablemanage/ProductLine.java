package com.synway.property.pojo.tablemanage;

/**
 * @author 数据接入
 */
public class ProductLine {
    private String id="";
    private String lineName;
    private String responsiblePersonID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getResponsiblePersonID() {
        return responsiblePersonID;
    }

    public void setResponsiblePersonID(String responsiblePersonID) {
        this.responsiblePersonID = responsiblePersonID;
    }

    @Override
    public String toString() {
        return "ProductLine{" +
                "id='" + id + '\'' +
                ", lineName='" + lineName + '\'' +
                ", responsiblePersonID='" + responsiblePersonID + '\'' +
                '}';
    }
}
