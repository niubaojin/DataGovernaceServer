package com.synway.governace.pojo;

/**
 * @ClassName RequestParameter
 * @Descroption TODO
 * @Author majia
 * @Date 2020/5/14 12:57
 * @Version 1.0
 **/
public class RequestParameter {

    private String id;
    private String content;
    private String treeName;

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
