package com.synway.datastandardmanager.pojo;

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
@Data
@Slf4j
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


    public void initUserLevel() {
        if(userRolesName.contains("数汇系统管理员")){
            this.userLevel = 1;
        }else if (userRolesName.contains("数汇机构管理员")){
            this.userLevel = 2;
        }else if (userRolesName.contains("数汇普通用户")){
            this.userLevel = 3;
        }else{
            this.userLevel = -1;
        }
    }


    public boolean isAdmin(){
        return this.userLevel==1;
    }

    /**
     * 将数据权限解析后构建 In所需语句
     * @return
     */
    public String getFormatDataAuthIds(String dsType){
        try {
            if (StringUtils.isBlank(this.dataAuth)) {
                return "('')";
            }
            String dataAuthIds = JSONArray.parseArray(this.dataAuth).stream().map(e -> ((JSONObject) e).getString("organId")).collect(Collectors.joining("'),(0,'", "((0,'", "'))"));
            if (!dsType.equalsIgnoreCase("oracle")){
                dataAuthIds = JSONArray.parseArray(this.dataAuth).stream().map(e -> ((JSONObject) e).getString("organId")).collect(Collectors.joining("','", "('", "')"));
            }
            return dataAuthIds;
        }catch (Exception ex){
            log.error("在解析数据权限时出错，错误信息为:",ex);
        }
        return "('')";
    }

}
