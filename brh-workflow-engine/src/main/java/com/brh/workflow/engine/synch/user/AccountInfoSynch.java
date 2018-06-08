package com.brh.workflow.engine.synch.user;
import java.util.List;

import com.brh.workflow.engine.entity.WorkflowGroup;
import com.brh.workflow.engine.entity.WorkflowUser;
import com.brh.workflow.engine.exception.WorkflowException;

/*
 * lcl
 * 2017-07-27
 * activiti工作流用户信息同步接口
 */
public interface AccountInfoSynch {
	/**
	 * 保存用户信息——新增／更新
	 * @param user
	 * @throws WorkflowException
	 */
	public void saveUser(WorkflowUser user) throws WorkflowException;
	/**
	 *  保存用户组信息——新增/更新
	 * @param group
	 * @throws WorkflowException
	 */
	public void saveGroup(WorkflowGroup group) throws WorkflowException;
	/**
	 * 保存或更新用户与组关系信息
	 * @param roleIds
	 * @param userId
	 * @throws WorkflowException
	 */
	public void saveMemberShip(List<?> roleIds, Long userId)throws WorkflowException;
	/**
	 * 删除用户
	 * @param userId
	 * @throws WorkflowException
	 */
	public void deleteUser(Long userId) throws WorkflowException;
	/**
	 * 删除用户组
	 * @param GroupId
	 * @throws WorkflowException
	 */
	public void deleteGroup(Long GroupId) throws WorkflowException;
	/**
	 * 删除指定用户和角色绑定关系
	 * @param userId
	 * @param GroupId
	 * @throws WorkflowException
	 */
	public void deleteMemberShip(Long userId,Long GroupId) throws WorkflowException;
	
}
