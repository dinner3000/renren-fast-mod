package com.brh.workflow.engine.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

/**
 * 
 * @author lcl
 *	activit引擎相关要素，业务实体继承此类可使用引擎要素
 */
public class WorkflowEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long businessKey;//业务主键  关联工作流与业务key，必须唯一
	//流程实例id
    private String procInstId;
    private String userId;
	 // 流程任务
    private Task task;
    private String taskId;
    private String isAgree;//审批意见 暂时未用
    
    private String curStep;
    private Map<String, Object> variables;
    private List<Comment> comment;//历史节点对应的批注信息
    private HistoricTaskInstance hisTaskInfo;//历史节点信息
    // 运行中的流程实例
    private ProcessInstance processInstance;

    // 历史的流程实例
    private HistoricProcessInstance historicProcessInstance;

    // 流程定义
    private ProcessDefinition processDefinition;
    private FlowNode currentNode;
    private FlowNode nextNode;
    private String flowStatus;//claim 待签收  todo 待办理
    private String deploymentId;
    private String deploymentName;
    
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String processDefinitionVersion;
    private String processDefinitionDes;
    private String processDefinitionPng;
    private String processDefinitionRes;
  
	public Long getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(Long businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getCurStep() {
		return curStep;
	}

	public void setCurStep(String curStep) {
		this.curStep = curStep;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	private int page = 1; // 当前页
	private int size = 10; // 页大小

	private String sort; // 排序字段
	private String dir; // 升序Or降序

	/** 当前页 */
	public Integer getPage() {
		return page;
	}

	/** 当前页 */
	public void setPage(int page) {
		this.page = page;
	}

	/** 页大小 */
	public int getSize() {
		return size;
	}

	/** 页大小 */
	public void setSize(int size) {
		this.size = size;
	}

	/** 排序字段 */
	public String getSort() {
		return sort;
	}

	/** 排序字段 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/** 升序Or降序 */
	public String getDir() {
		return dir;
	}

	/** 升序Or降序 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getStart() {
		return (page-1) * size;
	}


	public int getLimit() {
		// MYSQL 使用页面条数
		//return size;
		return page * size +1;
	}

	public FlowNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(FlowNode currentNode) {
		this.currentNode = currentNode;
	}

	public FlowNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(FlowNode nextNode) {
		this.nextNode = nextNode;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDeploymentName() {
		return deploymentName;
	}

	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}

	public void setProcessDefinitionVersion(String processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}

	public String getProcessDefinitionDes() {
		return processDefinitionDes;
	}

	public void setProcessDefinitionDes(String processDefinitionDes) {
		this.processDefinitionDes = processDefinitionDes;
	}

	public String getProcessDefinitionPng() {
		return processDefinitionPng;
	}

	public void setProcessDefinitionPng(String processDefinitionPng) {
		this.processDefinitionPng = processDefinitionPng;
	}

	public String getProcessDefinitionRes() {
		return processDefinitionRes;
	}

	public void setProcessDefinitionRes(String processDefinitionRes) {
		this.processDefinitionRes = processDefinitionRes;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public HistoricTaskInstance getHisTaskInfo() {
		return hisTaskInfo;
	}

	public void setHisTaskInfo(HistoricTaskInstance hisTaskInfo) {
		this.hisTaskInfo = hisTaskInfo;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	
}
