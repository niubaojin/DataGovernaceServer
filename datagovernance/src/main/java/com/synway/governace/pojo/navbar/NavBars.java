package com.synway.governace.pojo.navbar;

import java.util.List;

public class NavBars {
    private String navId;//导航栏id
    private String navIp;//导航栏ip
    private String navName;//导航栏名字
    private String navClass;//导航栏class
    private String navLink="";//导航栏url
    private String navShow;//导航栏展示，0为不展示，1为展示
    private String navBlank;//导航栏弹出或当前页面转向，0为当前页面转向，1为弹出页面
    private Integer navLevel;//导航栏层级
    private String navParentName;//导航栏父级名称
    private int navOrder;//导航栏顺序
    private String navNameEn;//导航栏英文名
    private List<NavBars> diretory;//导航栏下级
    /**
     * 重定向地址
     */
    private String redirect;
    /**
     * 导航栏上是否展示
     */
    private boolean navHidden;

    public boolean isNavHidden() {
        return navHidden;
    }

    public void setNavHidden(boolean navHidden) {
        this.navHidden = navHidden;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getNavNameEn() {
        return navNameEn;
    }

    public void setNavNameEn(String navNameEn) {
        this.navNameEn = navNameEn;
    }

    public String getNavId() {
        return navId;
    }

    public void setNavId(String navId) {
        this.navId = navId;
    }

    public String getNavIp() {
        return navIp;
    }

    public void setNavIp(String navIp) {
        this.navIp = navIp;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public String getNavClass() {
        return navClass;
    }

    public void setNavClass(String navClass) {
        this.navClass = navClass;
    }

    public String getNavLink() {
        return navLink;
    }

    public void setNavLink(String navLink) {
        this.navLink = navLink;
    }

    public String getNavShow() {
        return navShow;
    }

    public void setNavShow(String navShow) {
        this.navShow = navShow;
    }

    public String getNavBlank() {
        return navBlank;
    }

    public void setNavBlank(String navBlank) {
        this.navBlank = navBlank;
    }

    public Integer getNavLevel() {
        return navLevel;
    }

    public void setNavLevel(Integer navLevel) {
        this.navLevel = navLevel;
    }

    public String getNavParentName() {
        return navParentName;
    }

    public void setNavParentName(String navParentName) {
        this.navParentName = navParentName;
    }

    public int getNavOrder() {
        return navOrder;
    }

    public void setNavOrder(int navOrder) {
        this.navOrder = navOrder;
    }

    public List<NavBars> getDiretory() {
        return diretory;
    }

    public void setDiretory(List<NavBars> diretory) {
        this.diretory = diretory;
    }
}
