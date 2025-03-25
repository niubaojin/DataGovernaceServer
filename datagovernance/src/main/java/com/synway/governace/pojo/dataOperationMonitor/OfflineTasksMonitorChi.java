package com.synway.governace.pojo.dataOperationMonitor;
/**
 * 离线任务监控子表
 * @author admin
 *
 */
public class OfflineTasksMonitorChi {
	private String id;//外键
	private String taskName;//任务名称
	private String nodeName;//节点名称
	private int nodeType;//节点类型
	private String nodeTypeVo;//节点类型(显示)
	
	private long nodeWaitTime;//节点等待时间
	private String nodeWaitTimeVo;//节点等待时间(显示)
	
	private String nodeStartTime;//节点启动时间
	private long nodeConsumeTime;//节点耗时
	private String nodeConsumeTimeVo;//节点耗时(显示)
	
	private long nodeExecuteState;//节点执行状态
	private String nodeExecuteStateVo;//节点执行状态(显示)
	
	private long nodeExecuteTimeOver;//节点执行超时
	private String nodeExecuteTimeOverVo;//节点执行超时(显示)
	private String nodeCycleTime;//周期时间
	private int nodeCycleType;//周期类型
	private String nodeCycleTypeVo;//周期类型(显示)
	private int nodePriority;//节点优先级
	private String instanceId;//节点ID
	private String cpuOccupy;//节点ID
	private String memOccupy;//节点ID

	public String getNodeTypeVo() {
		return nodeTypeVo;
	}

	public void setNodeTypeVo(String nodeTypeVo) {
		this.nodeTypeVo = nodeTypeVo;
	}

	public String getNodeWaitTimeVo() {
		return nodeWaitTimeVo;
	}

	public void setNodeWaitTimeVo(String nodeWaitTimeVo) {
		this.nodeWaitTimeVo = nodeWaitTimeVo;
	}

	public String getNodeConsumeTimeVo() {
		return nodeConsumeTimeVo;
	}

	public void setNodeConsumeTimeVo(String nodeConsumeTimeVo) {
		this.nodeConsumeTimeVo = nodeConsumeTimeVo;
	}

	public String getNodeExecuteStateVo() {
		return nodeExecuteStateVo;
	}

	public void setNodeExecuteStateVo(String nodeExecuteStateVo) {
		this.nodeExecuteStateVo = nodeExecuteStateVo;
	}

	public String getNodeExecuteTimeOverVo() {
		return nodeExecuteTimeOverVo;
	}

	public void setNodeExecuteTimeOverVo(String nodeExecuteTimeOverVo) {
		this.nodeExecuteTimeOverVo = nodeExecuteTimeOverVo;
	}

	public String getNodeCycleTypeVo() {
		return nodeCycleTypeVo;
	}

	public void setNodeCycleTypeVo(String nodeCycleTypeVo) {
		this.nodeCycleTypeVo = nodeCycleTypeVo;
	}

	public String getCpuOccupy() {
		return cpuOccupy;
	}

	public void setCpuOccupy(String cpuOccupy) {
		this.cpuOccupy = cpuOccupy;
	}

	public String getMemOccupy() {
		return memOccupy;
	}

	public void setMemOccupy(String memOccupy) {
		this.memOccupy = memOccupy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public long getNodeWaitTime() {
		return nodeWaitTime;
	}

	public void setNodeWaitTime(long nodeWaitTime) {
		this.nodeWaitTime = nodeWaitTime;
	}

	public String getNodeStartTime() {
		return nodeStartTime;
	}

	public void setNodeStartTime(String nodeStartTime) {
		this.nodeStartTime = nodeStartTime;
	}

	public long getNodeConsumeTime() {
		return nodeConsumeTime;
	}

	public void setNodeConsumeTime(long nodeConsumeTime) {
		this.nodeConsumeTime = nodeConsumeTime;
	}

	public long getNodeExecuteState() {
		return nodeExecuteState;
	}

	public void setNodeExecuteState(long nodeExecuteState) {
		this.nodeExecuteState = nodeExecuteState;
	}

	public long getNodeExecuteTimeOver() {
		return nodeExecuteTimeOver;
	}

	public void setNodeExecuteTimeOver(long nodeExecuteTimeOver) {
		this.nodeExecuteTimeOver = nodeExecuteTimeOver;
	}

	public String getNodeCycleTime() {
		return nodeCycleTime;
	}

	public void setNodeCycleTime(String nodeCycleTime) {
		this.nodeCycleTime = nodeCycleTime;
	}

	public int getNodeCycleType() {
		return nodeCycleType;
	}

	public void setNodeCycleType(int nodeCycleType) {
		this.nodeCycleType = nodeCycleType;
	}

	public int getNodePriority() {
		return nodePriority;
	}

	public void setNodePriority(int nodePriority) {
		this.nodePriority = nodePriority;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
