package com.synway.datastandardmanager.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * 数据元字段分类的 枚举类
 * 数据元字段分类，用于字段值回填
 * @author obito
 * @date 2022/03/15 15:13
 */
public enum SynlteFieldClassEnum {
    ZSSFXXL("01","真实身份信息类"),
    SHTZXXL("02","社会特征信息类"),
    QTYSXXL("03","其他隐私信息类"),
    WPSBXXL("04","物品识别信息类"),
    WPTZXXL("05","物品特征信息类"),
    ZZJGSBXXL("06","组织机构识别信息类"),
    ZZJGTZXXL("07","组织机构特征信息类"),
    WLSFXXL("08","网络身份信息类"),
    WLSFTZXXL("09","网络身份特征信息类"),
    TXFWDXSFXXL("10","通讯服务对象身份信息类"),
    JRFWDXSFXXL("11","金融服务对象身份信息类"),
    SWTZXXL("12","生物特征信息类"),
    RYTPYXXXL("13","人员图片影像信息类"),
    WPTPYXXXL("14","物品图片影像信息类"),
    ZZJGTPYXXXL("15","组织机构图片影像信息类"),
    MMXXL("16","密码信息类"),
    IPDZXXL("17","IP地址信息类"),
    DZXXL("18","地址信息类"),
    QXDWXXL("19","区域定位信息类"),
    JQDWXXL("20","精确定位信息类"),
    XZGLXXXXL("21","行政管理行为信息类"),
    TXLXXXL("22","通讯联系信息类"),
    SHGLXWSJDQTSMSHHDXXL("23","社会管理行为涉及的其他实名社会活动信息类"),
    ZCDCXWXXL("24","侦查调查行为信息类"),
    WFFZXXXXL("25","违法犯罪行为信息类"),
    NBGLXXXXL("26","内部管理行为信息类"),
    SHFWXXXXL("27","社会服务行为信息类"),
    YLXXL("28","医疗信息类"),
    CCXXL("29","财产信息类"),
    HLWHDXXL("30","互联网活动信息类"),
    SHSWXWSJDQTSMSHHDXXL("31","社会事务行为涉及的其他实名社会活动信息类"),
    GRTXNRL("32","个人通信内容类"),
    QZTXNRL("33","群组通信内容类"),
    MXSHFBNRXXL("34","面向社会发布内容信息类"),
    TDNRTZXXL("35","特定内容特征信息类"),
    JSGLXXL("36","技术管理信息类"),
    XXLYXXL("37","信息来源信息类"),
    TJXXL("38","统计信息类"),
    QTWGLXX("90","其他未归类信息");

    private String id;
    private String value;

    SynlteFieldClassEnum(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (SynlteFieldClassEnum element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }
}
