package com.synway.property.pojo;

import com.synway.property.pojo.approvalinfo.DataApproval;
import com.synway.property.pojo.datastoragemonitor.DataResourceTable;
import com.synway.property.pojo.datastoragemonitor.TableOrganizationData;
import com.synway.property.pojo.formorganizationindex.ReceiveTag;
import com.synway.property.pojo.interfacePojo.ReceiveTable;
import lombok.Data;

import java.util.List;

/**
 * @author 数据接入
 */
@Data
public class RequestParameter {

    private String parentId;//获取告警配置的parentid

    private List<DataApproval> dataApprovals;//审批数据

    //样例数据，表结构 接口,更新生命周期
    private String tableType;
    private String tableProject;
    private String tableNameEn;
    private String tableNameCh;
    private String beginTime;
    private String endTime;
    private String resourceId;

    //更新表组织参数
    private TableOrganizationData oldTableOrganizationData;
    private TableOrganizationData newTableOrganizationData;

    //更新生命周期
    private String oldValue;
    private String value;
    private String userName;
    private String userId;
    private String organId;

    //审批
    private String status;
    private String applicationInfo;

    //高级检索
    private String fieldTermType;
    private List<String> fieldTermConfirmed;
    private String composeTerm;

    //表组织查询
    private String input;
    private List<ReceiveTag> classifyTags;
    private List<ReceiveTag> registerTags;
    //    List<ReceiveTag> labelTags;
    private List<ReceiveTag> storageTags;
    private List<ReceiveTag> usingTags;
    private List<String> termSetting;
    private List<String> lastModifiedTime;
    private Long startRecordNum;
    private Long endRecordNum;
    private Long startStorageSize;
    private Long endStorageSize;
    private DataResourceTable queryTable;

    // 分页查询
    private int pageIndex;
    private int pageSize;
    private String sortName;
    private String sortOrder;
    // 过滤条件
    private String objectStateStatus;
    private String productStageStatus;
    private String registerStateStatus;
    private String tableProjectStatus;
    private String tableStateStatus;
    private String tableidStatus;
    private String updatePeriodStatus;
    private String isStandardStatus;


    // 表组织导出(数据组织资产导出)
    private String type;                    // 导出文件类型：1、xlsx，2、csv，3、doc
    private String incrementDays;           // 导出的日增量天数
    private String mainClassify;
    private String primaryClassifyCh;
    private String secondaryClassifyCh;
    private String threeValue;

    //资产对外接口
    private List<ReceiveTable> table;

}
