package com.brh.workflow.engine.manager;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.brh.workflow.engine.entity.WorkflowEntity;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.util.WorkflowPage;

/**
 * 
 * @author lcl 工作流管理类： 流程的启动／查询／管理等操作
 */
public interface WorkflowManager {
	/**
	 * 
	 * @return
	 */
	public List<WorkflowEntity> queryProcessDefine(WorkflowPage<WorkflowEntity> page, int[] pageParams);
	/**
	 * 启动流程
	 * 
	 * @param entity
	 * @param variables
	 * @throws WorkflowException
	 */
	public ProcessInstance startWorkflow(WorkflowEntity entity,
			Map<String, Object> variables, String flowName)
			throws WorkflowException;

	/**
	 * 查询用户当前待办任务
	 * 
	 * @param userId
	 * @param page
	 * @param pageParams
	 * @param WorkflowEntity
	 * @return
	 * @throws WorkflowException
	 */
	public List<WorkflowEntity> findTodoTasks(String userId,
			WorkflowPage<WorkflowEntity> page, int[] pageParams)
			throws WorkflowException;

	/**
	 * 读取运行中流程
	 * 
	 * @param page
	 * @param pageParams
	 * @return
	 * @throws WorkflowException
	 */
	public List<WorkflowEntity> findRunningProcessInstaces(
			WorkflowPage<WorkflowEntity> page, int[] pageParams, String flowName)
			throws WorkflowException;

	/**
	 * 读取已结束流程
	 * 
	 * @param page
	 * @param pageParams
	 * @return
	 */
	public List<WorkflowEntity> findFinishedProcessInstaces(
			WorkflowPage<WorkflowEntity> page, int[] pageParams, String flowName)
			throws WorkflowException;

	/**
	 * 根据流程id获取当前任务信息
	 * 
	 * @param procInstanceId
	 * @return
	 */
	public void findTaskNodeInfo(Task task, WorkflowEntity entity)
			throws WorkflowException;

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 * @param userId
	 * @param variables
	 * @return
	 * @throws WorkflowException
	 */
	public String claim(String taskId, String userId,
			Map<String, Object> variables) throws WorkflowException;

	/**
	 * 读取任务详细数据
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getTaskWithVars(String taskId)
			throws WorkflowException;

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @return
	 */
	public String complete(String taskId, Map<String, Object> variables)
			throws WorkflowException;
	/**
	 * 查询流程实例状态
	 * @param processInstId
	 * @return true running
	 * @return false over
	 */
	public boolean queryProcInstStatus(String processInstId)throws WorkflowException;
	/**
	 * 签收并完成处理
	 * @param taskId
	 * @param userId
	 * @param variables
	 * @return
	 * @throws WorkflowException
	 */
	public String claimAndcomplete(String taskId,String userId, Map<String, Object> variables)throws WorkflowException;
	/**
	 * 根据processInstId查询历史审批记录列表
	 * @param processInstId
	 * @return
	 * @throws WorkflowException
	 */
	public int queryApprovalList(String processInstId,List<WorkflowEntity> list,int[] param)throws WorkflowException;
	/**
	 * 添加批注
	 * @param taskId
	 * @param userNm
	 * @param comment
	 */
	public void addComment(String taskId,String procInstId,String userNm,String comment);
	/**
	 * 流程监控  查询所有流程
	 * @param page
	 * @param pageParams
	 * @return
	 * @throws WorkflowException
	 */
	public void  findProcessInstaces(String procInstId,WorkflowEntity entity)throws WorkflowException;
	/**
	 * 设置任务变量
	 * @param taskId
	 * @param variableName
	 * @param value
	 */
	public void setTaskVariable(String taskId, String variableName, String value);
}
