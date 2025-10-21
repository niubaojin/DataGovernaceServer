package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.entity.pojo.LoginUser;
import com.synway.datastandardmanager.entity.pojo.ObjectEntity;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.vo.ObjectRelationManageVO;
import com.synway.datastandardmanager.entity.vo.PublicDataInfoVO;
import com.synway.datastandardmanager.entity.vo.SourceRelationShipVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 标准表的所有信息，包括数据信息，字段定义，来源关系
 * 20200225 创建
 */
@Data
public class ObjectManageDTO implements Serializable {
    // 为0表示是新增的标准表信息，
    private String objectId;
    @NotNull
    private String tableId;
    // 如果是审批流程拒绝之后 需要回填该值
    private String approvalId;
    // 判断是否为流程中的数据
    private Boolean flow = false;
    // 标准表的 数据信息
    private ObjectEntity objectPojoTable;
    // 标准表的 所有字段信息
    private List<ObjectFieldEntity> objectFieldList;
    // 标准表的 所有来源关系
    private List<SourceRelationShipVO> sourceRelationShipList;
    // 状态(0:初始化;1:审批中;2:退回;3:终止)
    private String status;
    // 如果是原始库　则需要重新生成tableId
    private String dataSourceClassifyStr;
    private String code;
    // 20200911 存储从数据仓库中获取到的数据来源信息
    private PublicDataInfoVO publicDataInfo;
    // 20200917 第三方模块的名称
    private String moduleName = "";
    private LoginUser user = null;
    //20220111 是否有数据集对标内容
    private Boolean dataRelationMapping;
    //20220111 数据项对标内容
    private ObjectRelationManageVO objectRelationManage;
    // 操作类型（发送操作日志用），0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：导出
    private int operateType = 2;

}
