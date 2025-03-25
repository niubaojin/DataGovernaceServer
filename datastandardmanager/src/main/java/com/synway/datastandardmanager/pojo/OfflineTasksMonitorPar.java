package com.synway.datastandardmanager.pojo;

/**
 * 离线任务监控主表
 * @author admin
 *
 */
public class OfflineTasksMonitorPar {
	private String id;//主键
	private String statisticTime;//日期
	private String taskName;//任务名称
	private String taskCategories;//任务类别
	private long significance;//重要性
	private String significanceVo;//重要性

	private String inputTableName;//输入表名
	private String outputTableName;//输出表名
	private String involveApply;//涉及应用
	private String taskNominator;//任务制定人
	
	private int taskExecuteCycle;//任务执行周期
	private String taskExecuteCycleVo;//任务执行周期

	private int taskPriority;//任务优先级
	private String taskStartTime;//任务启动时间
	private long taskWaitTime;//任务等待时间
	private String taskWaitTimeVo;//任务等待时间

	private int taskNumber;//任务数
	private int executeState;//执行状态
	private String executeStateVo;///执行状态
	private long executeTimeOver;//执行时间超长
	private String executeTimeOverVo;///执行时间超长

    private String taskConsume; //任务耗时
    

	public String getTaskConsume() {
		return taskConsume;
	}

	public void setTaskConsume(String taskConsume) {
		this.taskConsume = taskConsume;
	}

	public String getSignificanceVo() {
		return significanceVo;
	}

	public void setSignificanceVo(String significanceVo) {
		this.significanceVo = significanceVo;
	}

	public String getTaskExecuteCycleVo() {
		return taskExecuteCycleVo;
	}

	public void setTaskExecuteCycleVo(String taskExecuteCycleVo) {
		this.taskExecuteCycleVo = taskExecuteCycleVo;
	}

	public String getTaskWaitTimeVo() {
		return taskWaitTimeVo;
	}

	public void setTaskWaitTimeVo(String taskWaitTimeVo) {
		this.taskWaitTimeVo = taskWaitTimeVo;
	}

	public String getExecuteStateVo() {
		return executeStateVo;
	}

	public void setExecuteStateVo(String executeStateVo) {
		this.executeStateVo = executeStateVo;
	}

	public String getExecuteTimeOverVo() {
		return executeTimeOverVo;
	}

	public void setExecuteTimeOverVo(String executeTimeOverVo) {
		this.executeTimeOverVo = executeTimeOverVo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatisticTime() {
		return statisticTime;
	}

	public void setStatisticTime(String statisticTime) {
		this.statisticTime = statisticTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCategories() {
		return taskCategories;
	}

	public void setTaskCategories(String taskCategories) {
		this.taskCategories = taskCategories;
	}

	public long getSignificance() {
		return significance;
	}

	public void setSignificance(long significance) {
		this.significance = significance;
	}

	public String getInputTableName() {
		return inputTableName;
	}

	public void setInputTableName(String inputTableName) {
		this.inputTableName = inputTableName;
	}

	public String getOutputTableName() {
		return outputTableName;
	}

	public void setOutputTableName(String outputTableName) {
		this.outputTableName = outputTableName;
	}

	public String getInvolveApply() {
		return involveApply;
	}

	public void setInvolveApply(String involveApply) {
		this.involveApply = involveApply;
	}

	public String getTaskNominator() {
		return taskNominator;
	}

	public void setTaskNominator(String taskNominator) {
		this.taskNominator = taskNominator;
	}

	public int getTaskExecuteCycle() {
		return taskExecuteCycle;
	}

	public void setTaskExecuteCycle(int taskExecuteCycle) {
		this.taskExecuteCycle = taskExecuteCycle;
	}

	public int getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(int taskPriority) {
		this.taskPriority = taskPriority;
	}

	public long getTaskWaitTime() {
		return taskWaitTime;
	}

	public void setTaskWaitTime(long taskWaitTime) {
		this.taskWaitTime = taskWaitTime;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}

	public int getExecuteState() {
		return executeState;
	}

	public void setExecuteState(int executeState) {
		this.executeState = executeState;
	}

	public long getExecuteTimeOver() {
		return executeTimeOver;
	}

	public void setExecuteTimeOver(long executeTimeOver) {
		this.executeTimeOver = executeTimeOver;
	}
}
