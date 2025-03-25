package com.synway.datarelation.constant;

/**
 * 数据处理平台默认的表空间
 *      ADS-HC: hc_db
 * 		ADS-HP: hp_db
 * 		ODPS:   synods
 * 		Hive:	synods
 * 		HBase:	jz
 * @author wangdongwei
 * @date 2021/2/26 16:46
 */
public enum DataBaseProject {

    ADS_HC("ADS-HC","hc_db"),
    ADS_HP("ADS-HP","hp_db"),
    ODPS("ODPS","synods"),
    HIVE("HIVE","synods"),
    HBASE("HBASE","jz");

    private final String code;
    private final String project;

    DataBaseProject(String code ,String project){
        this.code = code;
        this.project = project;
    }


    public static String getTableNameByCode(String code,String tableName){
        for(DataBaseProject dataBaseProject: values()){
            if(dataBaseProject.code.equalsIgnoreCase(code)){
                return dataBaseProject.project+"."+tableName;
            }
        }
        return tableName;
    }




}
