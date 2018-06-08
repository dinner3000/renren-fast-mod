package com.brh.channel.shiro.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brh.channel.common.utils.CommonConstant;
import com.brh.channel.common.utils.RRException;
import com.brh.channel.shiro.dao.SysRoleDao;
import com.brh.channel.shiro.entity.SysRoleEntity;
import com.brh.channel.shiro.service.SysRoleMenuService;
import com.brh.channel.shiro.service.SysRoleService;
import com.brh.channel.shiro.service.SysUserRoleService;
import com.brh.channel.shiro.service.SysUserService;
import com.brh.workflow.engine.entity.WorkflowGroup;
import com.brh.workflow.engine.exception.WorkflowException;
import com.brh.workflow.engine.synch.user.AccountInfoSynch;



/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private AccountInfoSynch accountInfoSynch;
	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		java.sql.Timestamp dateTime = new java.sql.Timestamp(new Date().getTime());
		role.setCreateTime(dateTime);
		sysRoleDao.save(role);
		
		//检查权限是否越权
		checkPrems(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
		WorkflowGroup  flowGroup=new WorkflowGroup();
		flowGroup.setId(role.getRoleId().toString());
		flowGroup.setName(role.getRoleName());
		try {
			accountInfoSynch.saveGroup(flowGroup);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("同步角色信息失败");
		}
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//检查权限是否越权
		checkPrems(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
		WorkflowGroup  flowGroup=new WorkflowGroup();
		flowGroup.setId(role.getRoleId().toString());
		flowGroup.setName(role.getRoleName());
		try {
			accountInfoSynch.saveGroup(flowGroup);
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new RuntimeException("更新角色信息失败");
		}
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}
	
	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return sysRoleDao.queryRoleIdList(createUserId);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == CommonConstant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
		
		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new RRException("新增角色的权限，已超出你的权限范围");
		}
	}

	@Override
	public void deleteBatchRoleAndMenuAndUser(Object[] roles) {
		sysRoleDao.deleteBatchRole(roles);
		sysRoleDao.deleteBatchRoleMenu(roles);
		sysRoleDao.deleteBatchUserRole(roles);
		for(int i=0;i<roles.length;i++){
		try {
			accountInfoSynch.deleteGroup(Long.parseLong(roles[i].toString()));
		} catch (NumberFormatException | WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
