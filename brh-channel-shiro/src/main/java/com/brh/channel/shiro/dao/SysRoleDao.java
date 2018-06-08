package com.brh.channel.shiro.dao;

import java.util.List;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.shiro.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
	/**
	 * 删除角色
	 * @param roles
	 * @return
	 */
	int deleteBatchRole(Object[] roles);
	/**
	 * 删除角色菜单
	 * @param roles
	 * @return
	 */
	int deleteBatchRoleMenu(Object[] roles);
	/**
	 * 删除用户角色
	 * @param roles
	 * @return
	 */
	int deleteBatchUserRole(Object[] roles);
}
