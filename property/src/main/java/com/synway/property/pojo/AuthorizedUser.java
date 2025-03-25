package com.synway.property.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 权限用户的相关信息
 * @author wangdongwei
 * @date 2021/5/24 20:28
 */
@Data
@Setter
@Getter
@ToString
public class AuthorizedUser implements Serializable {
    private Integer userId;
    private String userName;
    private Boolean isAdmin;

    public AuthorizedUser(){

    }
    public AuthorizedUser(int userId,String userName,boolean isAdmin){
        this.userId = userId;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

}