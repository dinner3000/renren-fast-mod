package com.brh.channel.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.channel.common.utils.CommonConstant;
import com.brh.channel.common.utils.PropertyConfigurer;
import com.brh.channel.shiro.dao.SysRoleMenuDao;
import com.brh.channel.shiro.dao.SysUserRoleDao;
import com.brh.channel.shiro.entity.SysUserRoleEntity;
import com.brh.channel.shiro.service.SysUserRoleService;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.synch.user.AccountInfoSynch;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private AccountInfoSynch accountInfoSynch;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private PropertyConfigurer propertyConfigurer;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if (roleIdList.size() == 0) {
			return;
		}

		// 先删除用户与角色关系
		sysUserRoleDao.delete(userId);

		// 保存用户与角色关系
		String dbTyp = propertyConfigurer
				.getProperty(CommonConstant.DB_TYP, "oracle");
		if (dbTyp.equals("oracle")) {
			SysUserRoleEntity userRole = new SysUserRoleEntity();
			for (Long longId : roleIdList) {
				userRole.setId(selectSequenceId());
				userRole.setUserId(userId);
				userRole.setRoleId(longId);
				sysUserRoleDao.save(userRole);
			}
		} else if (dbTyp.equals("mysql")) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("roleIdList", roleIdList);
			sysUserRoleDao.save(map);
		}

		try {
			accountInfoSynch.saveMemberShip(roleIdList, userId);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("同步用户角色信息失败");
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	public void delete(Long userId) {
		sysUserRoleDao.delete(userId);
		try {
			accountInfoSynch.deleteUser(userId);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("同步删除用户角色信息失败");
		}
	}

	public Long selectSequenceId() {
		return sysRoleMenuDao.selectSequenceId();
	}

	@Override
	public List<SysUserRoleEntity> queryRoleIdListInfo(Long userId) {
		return sysUserRoleDao.queryRoleIdListInfo(userId);
	}
}
