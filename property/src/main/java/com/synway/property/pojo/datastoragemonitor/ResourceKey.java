package com.synway.property.pojo.datastoragemonitor;

/**
 * 数据仓库的key对象
 * @author majia
 */
public class ResourceKey {
    private String resourceId;
    private String url;
    private String username;
    private String password;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
