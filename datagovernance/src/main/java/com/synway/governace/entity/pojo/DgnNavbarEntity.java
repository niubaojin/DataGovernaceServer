package com.synway.governace.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("DGN_NAVBAR")
public class DgnNavbarEntity {
    //id，不重复即可
    @TableField("NAV_ID")
    private String navId;

    //导航栏名字（需与统一认证管理平台上资源管理配置数据工厂下的资源别名需对应）
    @TableField("NAV_NAME")
    private String navName;

    //一级菜单的类型，用于图片
    @TableField("NAV_CLASS")
    private String navClass;

    //'菜单导向的uri'
    @TableField("NAV_LINK")
    private String navLink;

    //是否展示，0为不展示，1为展示
    @TableField("NAV_SHOW")
    private String navShow;

    //0页面内跳转，1打开新页面
    @TableField("NAV_BLANK")
    private String navBlank;

    //菜单项等级
    @TableField("NAV_LEVEL")
    private String navLevel;

    //父级英文名
    @TableField("NAV_PARENT_NAME")
    private String navParentName;

    //菜单栏排序
    @TableField("NAV_ORDER")
    private String navOrder;

    //英文名
    @TableField("NAV_NAME_EN")
    private String navNameEn;

    //菜单项的ip,默认值为配置中心的nginx地址，如需改变地址，则按照格式填入即可
    @TableField("NAV_IP")
    private String navIp;

    //[可选]跳转路径
    @TableField("NAV_REDIRECT")
    private String navRedirect;

    //[可选]是否隐藏，默认为false
    @TableField("NAV_HIDDEN")
    private String navHidden;

}
