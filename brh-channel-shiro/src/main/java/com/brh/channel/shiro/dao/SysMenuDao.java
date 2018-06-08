package com.brh.channel.shiro.dao;

import java.util.List;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.shiro.entity.SysMenuEntity;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);
	/**
	 * 删除菜单
	 * @param menus
	 * @return
	 */
	int deleteBatchMenu(Object[] menus);
	/**
	 * 删除角色菜单
	 * @param menus
	 * @return
	 */
	int deleteBatchRoleMenu(Object[] menus);
	/**
	 * 查询所有菜单列表
	 * @return
	 */
	List<SysMenuEntity> queryListAll();
}
