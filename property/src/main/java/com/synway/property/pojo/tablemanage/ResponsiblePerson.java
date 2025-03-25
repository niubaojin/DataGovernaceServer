package com.synway.property.pojo.tablemanage;

/**
 * @author 数据接入
 */
public class ResponsiblePerson {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ResponsiblePerson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
