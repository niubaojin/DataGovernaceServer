//package com.synway.datarelation.pojo.access;
//
//import lombok.Data;
//
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.List;
//
//@Data
//public class RelationRecord implements Serializable {
//
//    public static final String STANDARD = "standard";
//    public static final String ACCESS = "access";
//    public static final String DATAWARE = "dataware";
//    public static final String DATAPROCESS = "dataprocess";
//    @NotNull
//    private String type;   	// standard:表示是从标准管理传递过来的数据  access: 表示是从数据接入传递过来的数据
//    private String sourceSysId="";     	//源系统代号、  124等代码
//    private String sourceSysChiName=""; // 源系统代号中文名
//    private String sourceEngName="";           // 源协议英文名
//    private String sourceSupplierId="";  // 源协议厂商代码
//    private String sourceChiName="";   //源协议中文名
//    private String targetSysId="";   	//目标系统代号（数据接入时为空）
//    private String targetSysChiName="";     	//目标系统中文名（数据接入时为空）
//    private String targetEngName="";         	//目标协议中文名（数据接入时为空）
//    private String targetChiName="";  // 目标协议中文名
//    private String targetProjectName="";     	// 输出项目名（数据接入输出标准化时为空，odps时填写）
//    private String targetTableName="";   	//输出表名（数据接入输出标准化时为空，odps时填写）
//    private List<ColumnRelation> fieldsRelation;  //  [{"sourceColumnName":"crsj","targetColumnName":"chu_ru_shi_jian"}, {"sourceColumnName":"sfzh","targetColumnName":"shen_fen_zheng_hao"}],       // 输入字段/输出字段、
//    private String taskUUID="";           //对接入就是任务id、对数据处理就是处理任务id
//    private String databaseId="";  		//数据仓库id（数据接入独有）
//    private String targetDBEngName="";   // 输出平台的英文名称 例如 ADS-HC、ADS-HP、ODPS、ADS、OSOS 、file
//
//    @Data
//    public static class  ColumnRelation implements Serializable {
//        @NotNull
//        public String sourceColumnName="";
//        @NotNull
//        public String targetColumnName="";
//        @NotNull
//        public String sourceColumnChiName="";
//        @NotNull
//        public String targetColumnChiName="";
//    }
//
//}
