package com.brh.channel.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brh.channel.common.utils.CommonConstant;
import com.brh.channel.common.utils.PropertyConfigurer;
import com.brh.channel.shiro.dao.SysRoleMenuDao;
import com.brh.channel.shiro.entity.SysRoleMenuEntity;
import com.brh.channel.shiro.service.SysRoleMenuService;

/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	private static Logger LOG=Logger.getLogger(SysRoleMenuServiceImpl.class);
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	PropertyConfigurer propertyConfigurer;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if (menuIdList.size() == 0) {
			return;
		}
		// 先删除角色与菜单关系
		sysRoleMenuDao.delete(roleId);
		String dbTyp=propertyConfigurer.getProperty(CommonConstant.DB_TYP, "oracle");
		LOG.debug("dbTyp="+dbTyp);
		if(dbTyp.equals("oracle")){
		SysRoleMenuEntity sysRoleMenu = new SysRoleMenuEntity();
		for (Long longId : menuIdList) {
			sysRoleMenu.setId(selectSequenceId());
			sysRoleMenu.setMenuId(longId);
			sysRoleMenu.setRoleId(roleId);
			sysRoleMenuDao.save(sysRoleMenu);
		}
		}else if(dbTyp.equals("mysql")){
		// 保存角色与菜单关系
			Map<String, Object> map = new HashMap<>();
			map.put("roleId", roleId);
			map.put("menuIdList", menuIdList);
			sysRoleMenuDao.save(map);
	}
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}

	@Override
	public Long selectSequenceId() {
		return sysRoleMenuDao.selectSequenceId();
	}

}
