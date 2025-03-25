package com.synway.governace.dao;

import com.synway.governace.pojo.process.ApprovalInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 审批流程dao
 * @author ywj
 */

@Repository
@Mapper
public interface ProcessDao {

    /**
     * 根据id查询审批申请信息
     * @param approvalId
     * @return ApprovalInfo
     */
    ApprovalInfo findApprovalById(@Param("approvalId") String approvalId);

    /**
     * 新增审批申请
     * @param approval
     */
    void insertApprovalInfo(ApprovalInfo approval);

    /**
     * 修改审批申请
     * @param approval
     */
    void updateApprovalInfo(ApprovalInfo approval);

    /**
     * 更新审批状态
     * @param approvalId
     * @param status
     */
    void updateApprovalStatus(@Param("approvalId") String approvalId, @Param("status") String status);

    /**
     * 审批信息查询
     * @param info
     * @return List<ApprovalInfo>
     */
    List<ApprovalInfo> queryApprovalInfoForModule(ApprovalInfo info);

    /**
     * 操作类型查询
     * @return List<String>
     */
    List<String> getOperationTypeData();

    /**
     * 执行异常信息保存
     * @param executeResult
     * @param approvalId
     */
    void saveExecuteResult(@Param("executeResult") String executeResult, @Param("approvalId") String approvalId);

    @MapKey("processinstanceid")
    Map<String, ApprovalInfo> getExecuteErrorInfo();
}
