package com.synway.datastandardmanager.pojo;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(max = 254)
    private String userName;
    private Boolean isAdmin;

    private String requestUuid="";


    public AuthorizedUser(){

    }
    public AuthorizedUser(int userId,String userName,boolean isAdmin){
        this.userId = userId;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

}
