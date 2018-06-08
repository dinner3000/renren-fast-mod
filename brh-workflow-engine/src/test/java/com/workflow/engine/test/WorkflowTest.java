//package com.workflow.engine.test;
//
//import static org.junit.Assert.*;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.history.HistoricVariableInstance;
//import org.activiti.engine.identity.User;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.activiti.engine.test.Deployment;
//import org.junit.Test;
//
//import com.workflow.test.AbstractTest;
//
//public class WorkflowTest extends AbstractTest {
//	/**
//	 * 测试是否能正常部署
//	 */
////	@Test
////	@Deployment(resources = "diagrams/leave/leave.bpmn")
//	public void queryDeployment() {
//		//查询部署成功流程
//		List<ProcessDefinition> processDefinitionList= repositoryService
//				.createProcessDefinitionQuery().list();
//
//		System.out.println(processDefinitionList.get(0).toString());
//		ProcessDefinition processDefinition=processDefinitionList.get(0);
//		//查询h2数据库中用户
//		List<User> userList=identityService.createUserQuery().list();
//		System.out.println(userList.size());
//		for(User user:userList){
//		System.out.println(user.getId());
//		}
//		  // 设置当前用户
//        String currentUserId = "henryyan";
//        identityService.setAuthenticatedUserId(currentUserId);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Map<String, String> variables = new HashMap<String, String>();
//        Calendar ca = Calendar.getInstance();
//        String startDate = sdf.format(ca.getTime());
//        ca.add(Calendar.DAY_OF_MONTH, 4); // 当前日期加4天
//        String endDate = sdf.format(ca.getTime());
//
//        // 启动流程
//        variables.put("startDate", startDate);
//        variables.put("endDate", endDate);
//        variables.put("reason", "公休");
//        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variables);
//        assertNotNull(processInstance);
//
//        // 部门领导审批通过
//        Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
//        variables = new HashMap<String, String>();
//        variables.put("deptLeaderPass", "true");
//        formService.submitTaskFormData(deptLeaderTask.getId(), variables);
//
//        // 人事审批通过
//        Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
//        variables = new HashMap<String, String>();
//        variables.put("hrPass", "true");
//        formService.submitTaskFormData(hrTask.getId(), variables);
//
//        
//        // 总经理审批
//        Task generalManagerTask = taskService.createTaskQuery().taskCandidateGroup("generalManager").singleResult();
//        variables = new HashMap<String, String>();
//        variables.put("generalManagerApproved", "true");
//        formService.submitTaskFormData(generalManagerTask.getId(), variables);
//
//        // 销假（根据申请人的用户ID读取）
//        Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
//        variables = new HashMap<String, String>();
//        variables.put("reportBackDate", sdf.format(ca.getTime()));
//        formService.submitTaskFormData(reportBackTask.getId(), variables);
//
//        // 验证流程是否已经结束
//        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished().singleResult();
//        assertNotNull(historicProcessInstance);
//
//        // 读取历史变量
//        Map<String, Object> historyVariables = packageVariables(processInstance);
//        System.out.println("historyVariables=" + historyVariables);
//
//        // 验证执行结果
//        assertEquals("ok", historyVariables.get("result"));
//	}
//	 /**
//     * 读取历史变量并封装到Map中
//     */
//    private Map<String, Object> packageVariables(ProcessInstance processInstance) {
//        Map<String, Object> historyVariables = new HashMap<String, Object>();
//        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list();
//        for (HistoricVariableInstance variable : list) {
//            historyVariables.put(variable.getVariableName(), variable.getValue());
//            System.out.println("variable: " + variable.getVariableName() + " = " + variable.getValue());
//        }
//        return historyVariables;
//    }
//
//}
