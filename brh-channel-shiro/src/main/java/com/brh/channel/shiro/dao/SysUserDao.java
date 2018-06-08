package com.brh.channel.shiro.dao;

import java.util.List;
import java.util.Map;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.shiro.entity.SysUserEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	int deleteBatchUser(Object[] id);
	/**
	 * 批量删除用户角色
	 * @param id
	 * @return
	 */
	int deleteBatchUserRole(Object[] id);
	/**
	 * 通过用户查找所在部门
	 * @param id
	 * @return
	 */
	String queryDeptNameByUserId(Long userId);

	String queryChUserNameByUserId(Integer userId);
}
