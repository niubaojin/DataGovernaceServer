package com.synway.property.enums;

/**
 * @author 数据接入
 */
public enum MainClassifyExp {
    /**
     * 数据组织分类
     */
    DATA_ORGANIZATION_CLASSIFY("dataOrganizationClassify","数据组织分类"),
    /**
     * 数据来源分类
     */
    DATA_SOURCE_CLASSIFY("dataSourceClassify","数据来源分类"),
    /**
     * 数据资源分类
     */
    DATA_RESOURCE_CLASSIFY("dataResourceClassify","数据资源分类");

    private String value = null;
    private String name=null;

    MainClassifyExp(String value,String name) {
        this.value = value;
        this.name=name;
    }

    //得到中文名称
    public static String getChineseName(Class<MainClassifyExp> classType,String str){
        String returnStr=null;
        for (MainClassifyExp exp : classType.getEnumConstants()) {
            if(exp.getValue().equals(str)){
                returnStr=exp.getName();
                break;
            }
        }
        return returnStr;
    }
    //得到英文名称
    public static String getEnglishName(Class<MainClassifyExp> classType,String str){
        String returnStr=null;
        for (MainClassifyExp exp : classType.getEnumConstants()) {
            if(exp.getName().equals(str)){
                returnStr=exp.getValue();
                break;
            }
        }
        return returnStr;
    }

    public static void main(String[] args) {
        System.out.println(MainClassifyExp.getChineseName(MainClassifyExp.class, "dataSourceClassify"));
        System.out.println(MainClassifyExp.getEnglishName(MainClassifyExp.class, "数据来源分类"));
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
