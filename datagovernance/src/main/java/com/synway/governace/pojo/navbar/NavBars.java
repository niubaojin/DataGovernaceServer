package com.synway.governace.pojo.navbar;

import lombok.Data;

import java.util.List;

@Data
public class NavBars {
    private String navId;           //导航栏id
    private String navIp;           //导航栏ip
    private String navName;         //导航栏名字
    private String navClass;        //导航栏class
    private String navLink="";      //导航栏url
    private String navShow;         //导航栏展示，0：隐藏，1：展示
    private String navBlank;        //导航栏弹出或当前页面转向，0为当前页面转向，1为弹出页面
    private Integer navLevel;       //导航栏层级
    private String navParentName;   //导航栏父级名称
    private int navOrder;           //导航栏顺序
    private String navNameEn;       //导航栏英文名
    private List<NavBars> diretory; //导航栏下级
    private String redirect;        //重定向地址
    private boolean navHidden;      //导航栏上是否展示，0展示，1：隐藏
}
