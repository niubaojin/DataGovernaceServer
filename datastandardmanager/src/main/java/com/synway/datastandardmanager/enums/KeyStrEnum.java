package com.synway.datastandardmanager.enums;

import com.synway.datastandardmanager.constants.Common;

/**
 * 公共枚举类：key类型为string
 *
 * @author nbj
 * @date 2025年6月26日14:03:20
 */
public enum KeyStrEnum {

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据元定义状态>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    /**
     * 状态指数据元在其注册的全生存期（即生命周期）内所处的状态。数据元在其注册的全部生存期内存在七种状态：
     */
    YS("01", "新建", Common.SYNLTEFIELDSTATUS),          //已经创建数据元并提交。提交新的数据需求和对现行数据元的修改建议都从本状态开始。
    CA("02", "草案", Common.SYNLTEFIELDSTATUS),          //经过数据元注册机构形式审查后，等待技术审查
    ZQYJ("03", "征求意见", Common.SYNLTEFIELDSTATUS),    //经过技术初审后，正在征求意见中。
    BP("04", "报批", Common.SYNLTEFIELDSTATUS),          //经过技术终审后，等待审批
    BZ("05", "发布", Common.SYNLTEFIELDSTATUS),          //新增或变更的数据元，经过标准化过程的协调和审查，已得到数据元管理机构批准
    WPZ("06", "未批准", Common.SYNLTEFIELDSTATUS),       //在新增或变更数据元的流程中，在任何一个阶段未能通过审查或批准
    FZ("07", "停用", Common.SYNLTEFIELDSTATUS),          //不再需要其支持信息需求，经数据元管理机构批准，该数据元的内容即将从标准中删去

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>限定词的相关码表>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    XJ("01","新建", Common.DETERMINER_ENUM),      //新建
    FB("05","发布", Common.DETERMINER_ENUM),      //发布
    FQ("07","停用", Common.DETERMINER_ENUM),      //停用
    GABZ("2_1","是", Common.DETERMINER_ENUM),    //公安标准
    FBZ("2_0","否", Common.DETERMINER_ENUM),     //非标准
    YES("1_1","是", Common.DETERMINER_ENUM),     //数据元编码
    NO("1_0","否", Common.DETERMINER_ENUM),      //数据元+数据字典

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据安全分级>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    PUBLICITY("1_01","公开", Common.DATASECURITYLEVEL),

    COMMON("1_02","一般", Common.DATASECURITYLEVEL),

    IMPORTANCE("1_03","重要", Common.DATASECURITYLEVEL),

    SPECIAL("1_04","特殊", Common.DATASECURITYLEVEL),

    FIRST_LEVEL("2_01","第一级", Common.DATASECURITYLEVEL),

    SECOND_LEVEL("2_02","第二级", Common.DATASECURITYLEVEL),

    THIRD_LEVEL("2_03","第三级", Common.DATASECURITYLEVEL),

    FOUR_LEVEL("2_04","第四级", Common.DATASECURITYLEVEL),

    FIVE_LEVEL("2_05","第五级", Common.DATASECURITYLEVEL),

    SIX_LEVEL("2_06","第六级", Common.DATASECURITYLEVEL),

    SEVEN_LEVEL("2_07","第七级", Common.DATASECURITYLEVEL),

    EIGHT_LEVEL("2_08","第八级", Common.DATASECURITYLEVEL),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据组织分类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    YSK("01","原始库", Common.ORGANIZATIONCLASS),
    ZYK("02","资源库", Common.ORGANIZATIONCLASS),
    ZTK("03","主题库", Common.ORGANIZATIONCLASS),
    ZSK("04","知识库", Common.ORGANIZATIONCLASS),
    YWK("05","业务库", Common.ORGANIZATIONCLASS),
    YWYSSYK("06","业务要素索引库", Common.ORGANIZATIONCLASS),
    QT("99","其他", Common.ORGANIZATIONCLASS),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据要素代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    SJYBM("1_1","数据元编码", Common.DATAELEMENTCODE),       //数据元编码
    SJYSJZD("1_2","数据元+数据字典", Common.DATAELEMENTCODE),//数据元+数据字典
    ISNB("2_1","标准", Common.DATAELEMENTCODE),             //公司内部制作
    NONB("2_2","私有", Common.DATAELEMENTCODE),             //客户现场制作
    PEOPLE("3_1","人员", Common.DATAELEMENTCODE),           //人
    OBJ("3_2","物", Common.DATAELEMENTCODE),                //物
    GROUP("3_3","组织", Common.DATAELEMENTCODE),            //组织
    DI("3_4","地", Common.DATAELEMENTCODE),                //地
    MATTER("3_5","事", Common.DATAELEMENTCODE),            //事
    SJ("3_6","时间", Common.DATAELEMENTCODE),              //时间
    INFO("3_7","信息", Common.DATAELEMENTCODE);            //信息

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/


    private String key;
    private String value;
    private String type;

    KeyStrEnum(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public static String getKeyByNameAndType(String value, String type) {
        String returnStr = null;
        for (KeyStrEnum exp : KeyStrEnum.class.getEnumConstants()) {
            if (exp.getValue().equalsIgnoreCase(value) && exp.getType().equals(type)) {
                returnStr = exp.getKey();
                break;
            }
        }
        return returnStr;
    }

    public static String getValueByKeyAndType(String key, String type) {
        String returnStr = null;
        for (KeyStrEnum exp : KeyStrEnum.class.getEnumConstants()) {
            if (exp.getKey().equals(key) && exp.getType().equals(type)) {
                returnStr = exp.getValue();
                break;
            }
        }
        return returnStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
