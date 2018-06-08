package com.brh.channel.web.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.web.entity.SysDeptEntity;

/**
 * SYS_DEPT
 * 
 * @date 2017-08-10 16:30:59
 */
public interface SysDeptDao extends BaseDao<SysDeptEntity> {
	/**
	 * 查询部门列表
	 */
	List<SysDeptEntity> queryList(Map<String, Object> map);

	List<SysDeptEntity> queryNotButtonList();

	SysDeptEntity deptInfoByUserId(String userId);

	int queryReDeptNmByDeptNm(String deptName);

	BigDecimal queryReDeptIdByDeptNm(String deptName);

	List<SysDeptEntity> queryListAll();
}
