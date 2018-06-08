package com.brh.workflow.engine.synch.user.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.workflow.engine.entity.WorkflowGroup;
import com.brh.workflow.engine.entity.WorkflowUser;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.manager.impl.WorkflowManagerImpl;
import com.brh.workflow.engine.synch.user.AccountInfoSynch;

@Service("accountInfoSynch")
public class AccountInfoSynchImpl implements AccountInfoSynch {
	private static Logger LOG = LoggerFactory
			.getLogger(AccountInfoSynchImpl.class);
	@Autowired
	IdentityService identityService;

	@Override
	public void saveUser(WorkflowUser user) throws WorkflowException {
		LOG.info("工作流同步保存用户信息start{}" , user.toString());

		String userId = user.getId().toString();
		List<User> activitiUsers = identityService.createUserQuery()
				.userId(userId).list();
		if (activitiUsers.size() == 1 ) {
			User activitiUser = activitiUsers.get(0);
			BeanUtils.copyProperties(user, activitiUser);
			LOG.info("activitiUser:"+activitiUser.toString());
			identityService.saveUser(activitiUser); // 更新
		} else if(activitiUsers.isEmpty()){
			User activitiUser = identityService.newUser(userId);
			BeanUtils.copyProperties(user, activitiUser);
			LOG.info("activitiUser:"+activitiUser.toString());
			identityService.saveUser(activitiUser); // 新增
		}
			else if (activitiUsers.size() > 1) {
			String errorMsg = "发现重复用户：id=" + userId;
			LOG.error(errorMsg);
			throw new WorkflowException(errorMsg);
		}
		LOG.info("工作流同步保存用户信息end{}" , user.toString());
	}

	@Override
	public void saveGroup(WorkflowGroup group) throws WorkflowException {
		LOG.info("工作流同步保存组信息start{}" , group.toString());
		String groupId = group.getId().toString();
		List<Group> activitiGroups = identityService.createGroupQuery()
				.groupId(groupId).list();
		if (activitiGroups.size() == 1) {
			Group activitiGroup = activitiGroups.get(0);
			BeanUtils.copyProperties(group, activitiGroup);
			identityService.saveGroup(activitiGroup); // 更新或新增
		}else if(activitiGroups.isEmpty()) {
			Group activitiGroup = identityService.newGroup(groupId);
			BeanUtils.copyProperties(group, activitiGroup);
			identityService.saveGroup(activitiGroup); // 新增
		}else if (activitiGroups.size() > 1) {
			String errorMsg = "发现重复组：id=" + groupId;
			LOG.error(errorMsg);
			throw new WorkflowException(errorMsg);
		}
		LOG.info("工作流同步保存组信息end{}" , group.toString());

	}

	@Override
	public void saveMemberShip(List<?> roleIds, Long userId)
			throws WorkflowException {
		LOG.info("工作流同步保存用户与组信息start{}" , userId.toString());
		for (Object roleId : roleIds) {
			LOG.info("add MemberShip to activit:" + roleId);
			identityService.deleteMembership(userId.toString(), roleId.toString());
			identityService.createMembership(userId.toString(), roleId.toString());
		}
		LOG.info("工作流同步保存用户与组信息end{}", userId.toString());
	}

	@Override
	public void deleteUser(Long userId) throws WorkflowException {
		LOG.info("工作流同步删除用户信息start{}", userId.toString());
		
		identityService.deleteUser(userId.toString());  
		 // 删除用户的membership  
        List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId.toString()).list();  
        for(Group group : activitiGroups) {  
            LOG.debug("delete group from activit:"+ToStringBuilder.reflectionToString(group));  
            identityService.deleteMembership(userId.toString(), group.getId());  
        }  
		LOG.info("工作流同步删除用户信息end{}" , userId.toString());
	}

	@Override
	public void deleteGroup(Long groupId) throws WorkflowException {
		LOG.info("工作流同步删除组信息start{}" , groupId.toString());
		identityService.deleteGroup(groupId.toString());
		LOG.info("工作流同步删除组信息end{}" , groupId.toString());

	}

	@Override
	public void deleteMemberShip(Long userId, Long groupId)
			throws WorkflowException {
		LOG.info("工作流同步删除指定用户和组信息start{}" , userId+":"+groupId.toString());
		identityService.deleteMembership(userId.toString(), groupId.toString());
		LOG.info("工作流同步删除组信息end{}" ,  userId+":"+groupId.toString());
		
	}

}
