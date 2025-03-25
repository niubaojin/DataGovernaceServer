//package com.synway.datastandardmanager.util;
//
//import com.synway.datastandardmanager.pojo.ObjectField;
//import com.synway.datastandardmanager.pojo.ObjectFieldStandard;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 在标准管理中公共字段的相关数据
// * @date 20191126 开始创建
// */
//public class StandardCommonColumnUtil {
//    private final static List<ObjectFieldStandard> Common_Column_List = new ArrayList<>();
//
//    static{
//        // 设置主键ID的公共字段信息
//        ObjectFieldStandard objectFieldMdId = new ObjectFieldStandard(Long.valueOf("0"),0,"03J0002","PID",
//                "主记录id","","2",128,0,0,1,
//                0,"PID","主记录id",0,0,1,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//
//        // 来源系统分类代码
//        ObjectFieldStandard objectFieldDataSource = new ObjectFieldStandard(Long.valueOf("1"),0,"09E0009","DATA_SOURCE3",
//                "来源系统分类代码","","2",64,0,0,1,
//                0,"DATA_SOURCE3","来源系统分类代码",0,0,1,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//        // 采集地   CLUE_DST_SYS
//        ObjectFieldStandard objectFieldClueDstSys = new ObjectFieldStandard(Long.valueOf("0"),3,"08A0007","CLUE_DST_SYS",
//                "采集地","","2",6,0,0,1,
//                0,"CLUE_DST_SYS","采用行政区划代码 ",0,0,1,1,
//                0,"0",3,"",0,"","","","", "","","","","","","");
//
//        // 信息入库时间
//        ObjectFieldStandard objectFieldSavedTime = new ObjectFieldStandard(Long.valueOf("0"),0,"03S0005","SAVED_TIME",
//                "信息入库时间","","0",20,0,0,1,
//                0,"SAVED_TIME","信息入库时间",0,0,1,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//        // 数据敏感级别编码
//        ObjectFieldStandard objectFieldSensitiveLevel = new ObjectFieldStandard(Long.valueOf("0"),0,"08A0034","SENSITIVE_LEVEL",
//                "数据敏感级别编码","","2",10,0,0,1,
//                0,"SENSITIVE_LEVEL","数据敏感级别编码",0,0,1,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//
//        // 备注
//        ObjectFieldStandard objectFieldRemark = new ObjectFieldStandard(Long.valueOf("0"),0,"09A0009","REMARK",
//                "备注","","2",4000,0,0,0,
//                0,"REMARK","备注",0,0,1,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//
//        // 截获时间(上线时间)
//        ObjectFieldStandard objectFieldCaptureTime = new ObjectFieldStandard(Long.valueOf("0"),0,"08A0014","CAPTURE_TIME",
//                "截获时间","","0",20,0,0,0,
//                0,"CAPTURE_TIME","截获时间",0,0,0,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//        // 协议代码
//        ObjectFieldStandard objectFieldProtocol = new ObjectFieldStandard(Long.valueOf("0"),0,"08A0001","PROTOCOL",
//                "协议代码","","2",20,0,0,0,
//                0,"PROTOCOL","协议代码",0,0,0,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//        // 线索ID
//        ObjectFieldStandard objectFieldClueId = new ObjectFieldStandard(Long.valueOf("0"),0,"08A0005","CLUE_ID",
//                "线索ID","","2",128,0,0,0,
//                0,"CLUE_ID","线索ID/日志查询标识",0,0,0,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//
//        // 线索布控
//        ObjectFieldStandard objectFieldClueSrcSys = new ObjectFieldStandard(Long.valueOf("0"),0,"08A0006","CLUE_SRC_SYS",
//                "线索布控","","2",6,0,0,0,
//                0,"CLUE_SRC_SYS","线索布控/日志查询源",0,0,0,1,
//                0,"0",0,"",0,"","","","", "","","","","","","");
//
//
//        Common_Column_List.add(objectFieldMdId);
//        Common_Column_List.add(objectFieldClueDstSys);
//        Common_Column_List.add(objectFieldDataSource);
//        Common_Column_List.add(objectFieldSavedTime);
//        Common_Column_List.add(objectFieldSensitiveLevel);
//        Common_Column_List.add(objectFieldRemark);
//        Common_Column_List.add(objectFieldCaptureTime);
//        Common_Column_List.add(objectFieldProtocol);
//        Common_Column_List.add(objectFieldClueId);
//        Common_Column_List.add(objectFieldClueSrcSys);
//
//    }
//
//    /**
//     *  获取公共字段信息
//     * @return
//     * @throws Exception
//     */
//    public static List<ObjectFieldStandard> getCommon_Column(Long objectId,int maxRecno) throws Exception {
//        String version = DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
//        int recno = maxRecno;
//        for(ObjectFieldStandard objectFieldStandard:Common_Column_List){
//            objectFieldStandard.setObjectId(objectId);
//            objectFieldStandard.setVersions(Integer.valueOf(version));
//            objectFieldStandard.setRecno(recno);
//            objectFieldStandard.setStandardRecno(recno);
//            recno = recno+1;
//        }
//        return Common_Column_List;
//    }
//}
