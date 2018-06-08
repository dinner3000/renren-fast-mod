package com.brh.channel.shiro.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.channel.common.utils.CommonConstant;
import com.brh.channel.common.utils.RRException;
import com.brh.channel.shiro.dao.SysUserDao;
import com.brh.channel.shiro.entity.SysUserEntity;
import com.brh.channel.shiro.service.SysRoleService;
import com.brh.channel.shiro.service.SysUserRoleService;
import com.brh.channel.shiro.service.SysUserService;
import com.brh.workflow.engine.entity.WorkflowUser;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.synch.user.AccountInfoSynch;



/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private AccountInfoSynch accountInfoSynch;
	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	public void save(SysUserEntity user) {
		java.sql.Timestamp dateTime = new java.sql.Timestamp(new Date().getTime());
		user.setCreateTime(dateTime);
		//sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		sysUserDao.save(user);
		
		//检查角色是否越权
		checkRole(user);

		WorkflowUser flowUser=new WorkflowUser();
		flowUser.setId(user.getUserId().toString());
		flowUser.setPassword(user.getPassword());
		flowUser.setEmail(user.getEmail());
		flowUser.setLast(user.getUsername());
		try {
			accountInfoSynch.saveUser(flowUser);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("同步用户信息失败");
		}
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		sysUserDao.update(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		WorkflowUser flowUser=new WorkflowUser();
		flowUser.setId(user.getUserId().toString());
		flowUser.setPassword(user.getPassword());
		flowUser.setEmail(user.getEmail());
		flowUser.setLast(user.getUsername());
		try {
			accountInfoSynch.saveUser(flowUser);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("更新用户角色信息失败");
		}
	}

	@Override
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatch(userId);
		deleteWorkflowUser(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == CommonConstant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		
		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}

	@Override
	public int deleteBatchUser(Object[] id) {
		int i=sysUserDao.deleteBatchUser(id);
		deleteWorkflowUser(id);
		return i;
	}

	@Override
	public int deleteBatchUserRole(Object[] id) {
		return sysUserDao.deleteBatchUserRole(id);
	}
	public void deleteWorkflowUser(Object[] id){
		for(int i=0;i<id.length;i++){
		try {
			 accountInfoSynch.deleteUser(Long.parseLong(id[i].toString()));
		} catch (NumberFormatException | WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	@Override
	public String queryDeptNameByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysUserDao.queryDeptNameByUserId(userId);
	}

	@Override
	public String queryChUserNameByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return sysUserDao.queryChUserNameByUserId(userId);
	}
}
