package com.synway.reconciliation.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 权限用户的相关信息
 */
@Data
public class AuthorizedUser implements Serializable {

    @NotNull
    @Size(max = 255)
    private String userId;

    @NotNull
    @Size(max = 255)
    private String userName;

    private Boolean isAdmin;

    public AuthorizedUser(){

    }

    public AuthorizedUser(String userId, String userName, boolean isAdmin){
        this.userId = userId;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

}
