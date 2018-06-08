package com.brh.channel.web.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.brh.channel.web.entity.SysDeptEntity;

/**
 * SYS_DEPT
 * 
 * @date 2017-08-10 16:30:59
 */
public interface SysDeptService {
	
	SysDeptEntity queryObject(BigDecimal deptId);
	
	List<SysDeptEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysDeptEntity sysDept);
	
	void update(SysDeptEntity sysDept);
	
	void delete(BigDecimal deptId);
	
	void deleteBatch(BigDecimal[] deptIds);

	List<SysDeptEntity> queryNotButtonList();

	SysDeptEntity deptInfoByUserId(String userId);

	int queryReDeptNmByDeptNm(String deptName);

	BigDecimal queryReDeptIdByDeptNm(String deptName);

	List<SysDeptEntity> queryListAll();
}
