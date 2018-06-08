package com.brh.workflow.engine.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;

import com.brh.workflow.engine.entity.FlowNode;
import com.brh.workflow.engine.entity.WorkflowEntity;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.manager.WorkflowManager;
import com.brh.workflow.engine.util.JumpActivityCmd;
import com.brh.workflow.engine.util.WorkflowPage;
import com.brh.workflow.engine.util.WorkflowTraceService;
import com.brh.workflow.engine.util.WorkflowUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author lcl 流程管理类实现
 */
@Service("workflowManager")
public class WorkflowManagerImpl implements WorkflowManager {
	private static Logger LOG = LoggerFactory
			.getLogger(WorkflowManagerImpl.class);
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;
	@Autowired
	ManagementService managementService;
	@Autowired
	WorkflowTraceService traceService;
	@Autowired
	ProcessEngineFactoryBean processEngine;

	@Autowired
	ProcessEngineConfiguration processEngineConfiguration;

	@Override
	public ProcessInstance startWorkflow(WorkflowEntity entity,
			Map<String, Object> variables, String flowName)
			throws WorkflowException {
		LOG.debug("saved entity{}", entity);
		String businessKey = entity.getBusinessKey().toString();

		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(entity.getUserId());

			processInstance = runtimeService.startProcessInstanceByKey(
					flowName, businessKey, variables);
			String processInstanceId = processInstance.getId();
			entity.setProcInstId(processInstanceId);
			LOG.debug(
					"start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { flowName, businessKey, processInstanceId,
							variables });
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;

	}

	@Override
	public List<WorkflowEntity> findTodoTasks(String userId,
			WorkflowPage<WorkflowEntity> page, int[] pageParams)
			throws WorkflowException {
		List<WorkflowEntity> results = new ArrayList<WorkflowEntity>();

		// 根据当前人的ID查询
		// TaskQuery taskQuery = taskService.createTaskQuery()
		// .taskCandidateOrAssigned(userId);
		TaskQuery taskQueryTotal = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId);// 待签收任务

		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(
				userId);// 待签收任务
		List<Task> tasks = taskQuery.active().listPage(pageParams[0],
				pageParams[1]);

		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active()
					.singleResult();
			if (processInstance == null) {
				continue;
			}
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			WorkflowEntity entity = new WorkflowEntity();
			entity.setBusinessKey(Long.parseLong(businessKey));
			entity.setTask(task);
			entity.setTaskId(task.getId());
			FlowNode node = new FlowNode();
			node.setNodeId(task.getId());
			node.setNodeName(task.getName());
			entity.setCurrentNode(node);
			// findTaskNodeInfo(task, entity);// 获取当前节点
			entity.setProcessInstance(processInstance);
			entity.setProcessDefinition(getProcessDefinition(processInstance
					.getProcessDefinitionId()));
			entity.setFlowStatus("claim");
			results.add(entity);
		}

		TaskQuery taskQueryClaim = taskService.createTaskQuery().taskAssignee(
				userId);// 已签收任务

		List<Task> tasksClaim = taskQueryClaim.active().listPage(pageParams[0],
				pageParams[1]);
		for (Task task : tasksClaim) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active()
					.singleResult();
			if (processInstance == null) {
				continue;
			}
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			WorkflowEntity entity = new WorkflowEntity();
			entity.setBusinessKey(Long.parseLong(businessKey));
			entity.setTask(task);
			entity.setTaskId(task.getId());
			FlowNode node = new FlowNode();
			node.setNodeId(task.getId());
			node.setNodeName(task.getName());
			entity.setCurrentNode(node);
			// findTaskNodeInfo(task, entity);// 获取当前节点
			entity.setProcessInstance(processInstance);
			entity.setProcessDefinition(getProcessDefinition(processInstance
					.getProcessDefinitionId()));
			entity.setFlowStatus("todo");
			results.add(entity);
		}

		page.setTotalCount(taskQueryTotal.count());
		page.setResult(results);
		return results;
	}

	/**
	 * 根据流程名查询正在运行的流程
	 */
	@Override
	public List<WorkflowEntity> findRunningProcessInstaces(
			WorkflowPage<WorkflowEntity> page, int[] pageParams, String flowName)
			throws WorkflowException {
		List<WorkflowEntity> results = new ArrayList<WorkflowEntity>();
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery().processDefinitionKey(flowName)
				.active().orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.listPage(pageParams[0],
				pageParams[1]);

		// 关联业务实体
		for (ProcessInstance processInstance : list) {
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			WorkflowEntity entity = new WorkflowEntity();
			entity.setBusinessKey(Long.parseLong(businessKey));
			entity.setProcessInstance(processInstance);
			entity.setProcessDefinition(getProcessDefinition(processInstance
					.getProcessDefinitionId()));
			results.add(entity);

			// 设置当前任务信息
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).active()
					.orderByTaskCreateTime().desc().listPage(0, 1);
			entity.setTask(tasks.get(0));
		}

		page.setTotalCount(query.count());
		page.setResult(results);
		return results;
	}

	@Override
	public List<WorkflowEntity> findFinishedProcessInstaces(
			WorkflowPage<WorkflowEntity> page, int[] pageParams, String flowName) {
		List<WorkflowEntity> results = new ArrayList<WorkflowEntity>();
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionKey(flowName).finished()
				.orderByProcessInstanceEndTime().desc();
		List<HistoricProcessInstance> list = query.listPage(pageParams[0],
				pageParams[1]);

		// 关联业务实体
		for (HistoricProcessInstance historicProcessInstance : list) {
			String businessKey = historicProcessInstance.getBusinessKey();
			WorkflowEntity entity = new WorkflowEntity();
			entity.setBusinessKey(Long.parseLong(businessKey));
			entity.setProcessDefinition(getProcessDefinition(historicProcessInstance
					.getProcessDefinitionId()));
			entity.setHistoricProcessInstance(historicProcessInstance);
			results.add(entity);
		}
		page.setTotalCount(query.count());
		page.setResult(results);
		return results;
	}

	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	/**
	 * 根据taskId查询要执行的节点的信息 应该也是当前task信息
	 */
	@Override
	public void findTaskNodeInfo(Task task, WorkflowEntity entity)
			throws WorkflowException {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities();
		// 根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();
		// 然后循环activitiList
		// 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				FlowNode currentNode = new FlowNode();
				LOG.info("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
				currentNode.setNodeId(activitiId);
				currentNode.setNodeName(activityImpl.getProperty("name")
						.toString());
				entity.setCurrentNode(currentNode);
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				boolean next = true;
				FlowNode nextNode = new FlowNode();
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					if (next) {
						nextNode.setNodeId(ac.getId());
						nextNode.setNodeName(ac.getProperty("name").toString());
						entity.setNextNode(nextNode);
					}
					LOG.info("下一步任务任务：" + ac.getProperty("name"));
				}
				break;
			}
		}
	}

	@Override
	public String claim(String taskId, String userId,
			Map<String, Object> variables) throws WorkflowException {
		taskService.claim(taskId, userId);
		return "success";
	}

	@Override
	public Map<String, Object> getTaskWithVars(String taskId)
			throws WorkflowException {
		Map<String, Object> variables = taskService.getVariables(taskId);
		return variables;
	}

	@Override
	public String complete(String taskId, Map<String, Object> variables)
			throws WorkflowException {
		try {
			taskService.complete(taskId, variables);
			return "success";
		} catch (Exception e) {
			LOG.error("error on complete task {}, variables={}", new Object[] {
					taskId, variables, e });
			return "error";
		}
	}

	@Override
	public boolean queryProcInstStatus(String processInstId)
			throws WorkflowException {
		{
			ProcessInstance pi = runtimeService// 表示正在执行的流程实例和执行对象
					.createProcessInstanceQuery()// 创建流程实例查询
					.processInstanceId(processInstId)// 使用流程实例ID查询
					.singleResult();

			if (pi == null) {
				LOG.info("流程已经结束");
				return false;
			} else {
				LOG.info("流程没有结束");
				return true;
			}
		}
	}

	@Override
	public List<WorkflowEntity> queryProcessDefine(
			WorkflowPage<WorkflowEntity> page, int[] pageParams) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		List<WorkflowEntity> objects = new ArrayList<WorkflowEntity>();

		// ProcessDefinitionQuery processDefinitionQuery = repositoryService
		// .createProcessDefinitionQuery().listPage(firstResult,
		// maxResults).latestVersion()
		// .orderByDeploymentId().desc();
		List<ProcessDefinition> processDefinitionList = repositoryService
				.createProcessDefinitionQuery().latestVersion()
				.listPage(pageParams[0], pageParams[1]);

		WorkflowEntity entity = null;
		for (ProcessDefinition processDefinition : processDefinitionList) {
			entity = new WorkflowEntity();
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery()
					.deploymentId(deploymentId).singleResult();
			entity.setDeploymentId(deployment.getId());
			entity.setDeploymentName(deployment.getName());
			entity.setProcessDefinitionId(processDefinition.getId());
			entity.setProcessDefinitionKey(processDefinition.getKey());
			entity.setProcessDefinitionDes(processDefinition.getDescription());
			entity.setProcessDefinitionName(processDefinition.getName());
			entity.setProcessDefinitionPng(processDefinition
					.getDiagramResourceName());
			entity.setProcessDefinitionRes(processDefinition.getResourceName());
			entity.setProcessDefinitionVersion(String.valueOf(processDefinition
					.getVersion()));
			LOG.debug("entity:" + entity.toString());
			objects.add(entity);
		}

		page.setTotalCount(processDefinitionList.size());
		page.setResult(objects);

		return objects;
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param processDefinitionId
	 *            流程定义
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @throws Exception
	 */
	public void loadByDeployment(String processDefinitionId,
			String resourceType, HttpServletResponse response) throws Exception {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 读取资源，通过流程ID
	 * 
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @param processInstanceId
	 *            流程实例ID
	 * @param response
	 * @throws Exception
	 */
	public void loadByProcessInstance(
			@RequestParam("type") String resourceType,
			@RequestParam("pid") String processInstanceId,
			HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = null;
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processInstance.getProcessDefinitionId())
				.singleResult();

		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		resourceAsStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	public String delete(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
		return "redirect:/workflow/process-list";
	}

	/**
	 * 输出跟踪流程信息
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */

	public List<Map<String, Object>> traceProcess(String processInstanceId)
			throws Exception {
		List<Map<String, Object>> activityInfos = traceService
				.traceProcess(processInstanceId);
		return activityInfos;
	}

	/**
	 * 读取带跟踪的图片
	 */
	public void readResource(String executionId, HttpServletResponse response)
			throws Exception {
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery().processInstanceId(executionId)
				.singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService
				.getActiveActivityIds(executionId);
		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)
		// ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		processEngineConfiguration = processEngine
				.getProcessEngineConfiguration();
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

		ProcessDiagramGenerator diagramGenerator = processEngineConfiguration
				.getProcessDiagramGenerator();
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel,
				"png", activeActivityIds);

		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	@RequestMapping(value = "/deploy")
	public String deploy(
			@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		String fileName = file.getOriginalFilename();

		try {
			InputStream fileInputStream = file.getInputStream();
			Deployment deployment = null;

			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				deployment = repositoryService.createDeployment()
						.addZipInputStream(zip).deploy();
			} else {
				deployment = repositoryService.createDeployment()
						.addInputStream(fileName, fileInputStream).deploy();
			}

			List<ProcessDefinition> list = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployment.getId()).list();

			for (ProcessDefinition processDefinition : list) {
				WorkflowUtils.exportDiagramToFile(repositoryService,
						processDefinition, exportDir);
			}

		} catch (Exception e) {
			LOG.error("error on deploy process, because of file input stream",
					e);
		}

		return "redirect:/workflow/process-list";
	}

	public String convertToModel(
			@PathVariable("processDefinitionId") String processDefinitionId)
			throws UnsupportedEncodingException, XMLStreamException {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(),
				processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter
				.convertToJson(bpmnModel);
		Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getDeploymentId());

		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,
				processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
				processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());

		repositoryService.saveModel(modelData);

		repositoryService.addModelEditorSource(modelData.getId(), modelNode
				.toString().getBytes("utf-8"));

		return "redirect:/workflow/model/list";
	}

	/**
	 * 挂起、激活流程实例
	 */
	public String updateState(@PathVariable("state") String state,
			@PathVariable("processDefinitionId") String processDefinitionId,
			RedirectAttributes redirectAttributes) {
		if (state.equals("active")) {
			redirectAttributes.addFlashAttribute("message", "已激活ID为["
					+ processDefinitionId + "]的流程定义。");
			repositoryService.activateProcessDefinitionById(
					processDefinitionId, true, null);
		} else if (state.equals("suspend")) {
			repositoryService.suspendProcessDefinitionById(processDefinitionId,
					true, null);
			redirectAttributes.addFlashAttribute("message", "已挂起ID为["
					+ processDefinitionId + "]的流程定义。");
		}
		return "redirect:/workflow/process-list";
	}

	/**
	 * 导出图片文件到硬盘
	 * 
	 * @return
	 */
	public List<String> exportDiagrams(
			@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir)
			throws IOException {
		List<String> files = new ArrayList<String>();
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery().list();

		for (ProcessDefinition processDefinition : list) {
			files.add(WorkflowUtils.exportDiagramToFile(repositoryService,
					processDefinition, exportDir));
		}

		return files;
	}

	public boolean jump(String executionId, String activityId) {
		Command<Object> cmd = new JumpActivityCmd(executionId, activityId);
		managementService.executeCommand(cmd);
		return true;
	}

	public BpmnModel queryBpmnModel(
			@PathVariable("processDefinitionId") String processDefinitionId) {
		BpmnModel bpmnModel = repositoryService
				.getBpmnModel(processDefinitionId);
		return bpmnModel;
	}

	/**
	 * 先签收 然后进行任务处理
	 */
	@Override
	@Transactional
	public String claimAndcomplete(String taskId, String userId,
			Map<String, Object> variables) throws WorkflowException {
		taskService.claim(taskId, userId);
		taskService.complete(taskId, variables);
		return "success";
	}

	/**
	 * 根据实例id查询历史审批记录列表
	 */
	@Override
	public int queryApprovalList(String processInstId,
			List<WorkflowEntity> listEntity, int[] param)
			throws WorkflowException {
        int firstResult = param[0] == 0 ? param[0] : param[0] - 1;
        int maxResults = param[1];

		// 使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()// 历史任务表查询
				.processInstanceId(processInstId)// 使用流程实例ID查询
				.orderByTaskCreateTime().asc().listPage(firstResult, maxResults);
		int total = (int) historyService.createHistoricTaskInstanceQuery()// 历史任务表查询
				.processInstanceId(processInstId).count();
		// 遍历集合，获取每个任务ID
		if (htiList != null && htiList.size() > 0) {
			for (HistoricTaskInstance hti : htiList) {
				WorkflowEntity entity = new WorkflowEntity();
				// 任务ID
				String htaskId = hti.getId();
				// 获取批注信息
				List<Comment> taskList = taskService.getTaskComments(htaskId);// 对应历史完成后的任务ID
				LOG.info("htaskId:"+htaskId);
				//System.out.println("curStep1:"+taskService.getVariableLocal(htaskId, "curStep"));
				HistoricVariableInstance var=historyService.createHistoricVariableInstanceQuery().taskId(htaskId).singleResult();
				//HistoricVariableInstance curStep=historyService.createHistoricVariableInstanceQuery().taskId(htaskId).variableName("curStep").singleResult();
				
				if(var==null){
					entity.setCurStep(""); // 如果查不出来 抛出异常，表明第一个开始节点没有设置task 变量	
				}else{
					String curStep=String.valueOf(var.getValue());
					LOG.info("curStep:"+curStep);
					entity.setCurStep(curStep);
				}
				
				
//				try {
//				
//				} catch (Exception e) {
//			    System.out.println("Exception curStep:"+curStep);	
//					entity.setCurStep("1"); // 如果查不出来 抛出异常，表明第一个开始节点没有设置task 变量
//				}
				LOG.info("entityCurStep:"+entity.getCurStep());
				// 每个节点取后放到一个curStep中
				entity.setHisTaskInfo(hti);
				entity.setComment(taskList);
				listEntity.add(entity);
			}
		}

		return total;
	}

	@Override
	public void addComment(String taskId, String procInstId, String userNm,
			String comment) {
		// 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
		Authentication.setAuthenticatedUserId(userNm);// 批注人的名称
														// 一定要写，不然查看的时候不知道人物信息
		// 添加批注信息
		taskService.addComment(taskId, procInstId, comment);// comment为批注内容
	}

	/**
	 * 流程监控 查询所有流程实例
	 */
	@Override
	public void findProcessInstaces(String procInstId, WorkflowEntity entity)
			throws WorkflowException {

		// 设置当前任务信息
		Task task = taskService.createTaskQuery().processInstanceId(procInstId)
				.active().singleResult();
		// 创建历史流程实例查询
		if (task == null) {
			HistoricProcessInstance hip = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(procInstId).singleResult();
			FlowNode currentNode = new FlowNode();
			currentNode.setNodeId(hip.getId());
			currentNode.setNodeName("结束");
			entity.setCurrentNode(currentNode);
		} else {
			LOG.info("procDefineId:" + task.getProcessDefinitionId());
			LOG.info("procDefineId:" + task.getId());
			LOG.info("procDefineId:" + task.getName());
			FlowNode node = new FlowNode();
			node.setNodeId(task.getId());
			node.setNodeName(task.getName());
			entity.setCurrentNode(node);
			entity.setTask(task);
			// findTaskNodeInfo(task, entity);// 获取当前节点
		}
	}

	/*
	 * 设置task变量
	 */
	public void setTaskVariable(String taskId, String variableName, String value) {
		taskService.setVariableLocal(taskId, variableName, value);
	}

}
