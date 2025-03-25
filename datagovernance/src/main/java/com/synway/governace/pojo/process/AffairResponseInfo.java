package com.synway.governace.pojo.process;

import com.alibaba.druid.util.StringUtils;

/**
 * 审批事务详细信息
 *
 * @author ywj
 */
public class AffairResponseInfo {
    private String affairname;
    private String name_;
    private String create_time_;
    private String process_name_;
    private String startusername;
    private String start_organ_name;
    private String status;
    private String suspension_state_;
    private String proc_inst_id_;
    private String proc_def_id_;
    private String task_def_key_;
    private String assignee_;
    private String procinst_status;
    private String sys_id_;
    private String censor_url_;
    private String operationType;
    private String executeStatus;
    private String executeMessage;

    public String getAffairname() {
        return affairname;
    }

    public void setAffairname(String affairname) {
        this.affairname = affairname;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getCreate_time_() {
        return create_time_;
    }

    public void setCreate_time_(String create_time_) {
        this.create_time_ = create_time_;
    }

    public String getProcess_name_() {
        return process_name_;
    }

    public void setProcess_name_(String process_name_) {
        this.process_name_ = process_name_;
    }

    public String getStartusername() {
        return startusername;
    }

    public void setStartusername(String startusername) {
        this.startusername = startusername;
    }

    public String getStart_organ_name() {
        return start_organ_name;
    }

    public void setStart_organ_name(String start_organ_name) {
        this.start_organ_name = start_organ_name;
    }

    public String getStatus() {
        if(StringUtils.equals("1", this.status) && StringUtils.equals("2", this.suspension_state_)){
            this.status = "已办(挂起)";
        }else if(StringUtils.equals("1", this.status) && !StringUtils.equals("2", this.suspension_state_)){
            this.status = "已办";
        }else if(StringUtils.equals("0", this.status) && StringUtils.equals("2", this.suspension_state_)){
            this.status = "待办(挂起)";
        }else if(StringUtils.equals("0", this.status) && !StringUtils.equals("2", this.suspension_state_)){
            this.status = "待办";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuspension_state_() {
        return suspension_state_;
    }

    public void setSuspension_state_(String suspension_state_) {
        this.suspension_state_ = suspension_state_;
    }

    public String getProc_inst_id_() {
        return proc_inst_id_;
    }

    public void setProc_inst_id_(String proc_inst_id_) {
        this.proc_inst_id_ = proc_inst_id_;
    }

    public String getProc_def_id_() {
        return proc_def_id_;
    }

    public void setProc_def_id_(String proc_def_id_) {
        this.proc_def_id_ = proc_def_id_;
    }

    public String getTask_def_key_() {
        return task_def_key_;
    }

    public void setTask_def_key_(String task_def_key_) {
        this.task_def_key_ = task_def_key_;
    }

    public String getAssignee_() {
        return assignee_;
    }

    public void setAssignee_(String assignee_) {
        this.assignee_ = assignee_;
    }

    public String getProcinst_status() {
        return procinst_status;
    }

    public void setProcinst_status(String procinst_status) {
        this.procinst_status = procinst_status;
    }

    public String getSys_id_() {
        return sys_id_;
    }

    public void setSys_id_(String sys_id_) {
        this.sys_id_ = sys_id_;
    }

    public String getCensor_url_() {
        return censor_url_;
    }

    public void setCensor_url_(String censor_url_) {
        this.censor_url_ = censor_url_;
    }

    public String getOperationType() {
        if (!StringUtils.isEmpty(this.affairname)) {
            this.operationType = this.affairname.substring(this.affairname.indexOf("\"") + 1, this.affairname.lastIndexOf("\""));
        }
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getExecuteMessage() {
        return executeMessage;
    }

    public void setExecuteMessage(String executeMessage) {
        this.executeMessage = executeMessage;
    }
}
