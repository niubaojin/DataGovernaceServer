package com.synway.datastandardmanager.pojo;

/**
 * 查询样例数据的请求参数
 */
public class QueryExampleDataPojo {
    private String type;
    private String tableName;

    // ads必填参数
    private String username;
    private String password;
    private String url;
    private String schema;
    //odps必填参数
    private String odpsServer;
    private String accessId;
    private String accessKey;
    private String project;

    @Override
    public String toString() {
        return "QueryExampleDataPojo{" +
                "type='" + type + '\'' +
                ", tableName='" + tableName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", schema='" + schema + '\'' +
                ", odpsServer='" + odpsServer + '\'' +
                ", accessId='" + accessId + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", project='" + project + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getOdpsServer() {
        return odpsServer;
    }

    public void setOdpsServer(String odpsServer) {
        this.odpsServer = odpsServer;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
