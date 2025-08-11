package com.synway.property.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * 用户信息
 * @author pj
 * @since 2021/5/20
 */
@Slf4j
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -529499952482778914L;

    /** 用户id */
    private String userId;
    /** 用户名称 */
    private String userName;
    /** 用登陆用户机构名 */
    private String organName;
    /** 用户的行政编码 */
    private String symbol;
    /** 用户所属机构id */
    private String organId;
    /** 系统代号 */
    private String systemCode;
    /** 操作员警号 */
    private String userPoliceNo;
    /** 12位机构编码 */
    private String symbol12;
    /** 身份证号码 */
    private String idCard;
    /** 岗位信息 */
    private String positionName;
    /** 角色 */
    private String userRolesName;
    /** 手机号 */
    private String mobit;
    /** 城市区号 */
    private String cityindex;
    /** 用户ip */
    private String ip;
    /** 用户等级 1表示超级管理员，2表示管理员，3表示普通用户 */
    private int userLevel;
    /** 数据权限 [{"organId": "27", "organName": "测试部"}]*/
    private String dataAuth;
    /** 操作权限内容 */
    private String operateAuth;

    public boolean isAdmin(){
        return this.userLevel==1;
    }

}
