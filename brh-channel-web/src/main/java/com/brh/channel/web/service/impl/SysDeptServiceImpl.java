package com.brh.channel.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.brh.channel.web.dao.SysDeptDao;
import com.brh.channel.web.entity.SysDeptEntity;
import com.brh.channel.web.service.SysDeptService;



@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	
	@Override
	public SysDeptEntity queryObject(BigDecimal deptId){
		return sysDeptDao.queryObject(deptId);
	}
	
	@Override
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysDeptDao.queryTotal(map);
	}
	
	@Override
	public void save(SysDeptEntity sysDept){
		sysDeptDao.save(sysDept);
	}
	
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptDao.update(sysDept);
	}
	
	@Override
	public void delete(BigDecimal deptId){
		sysDeptDao.delete(deptId);
	}
	
	@Override
	public void deleteBatch(BigDecimal[] deptIds){
		sysDeptDao.deleteBatch(deptIds);
	}

	@Override
	public List<SysDeptEntity> queryNotButtonList() {
		// TODO Auto-generated method stub
		return sysDeptDao.queryNotButtonList();
	}

	@Override
	public SysDeptEntity deptInfoByUserId(String userId) {
		// TODO Auto-generated method stub
		return sysDeptDao.deptInfoByUserId(userId);
	}

	@Override
	public int queryReDeptNmByDeptNm(String deptName) {
		// TODO Auto-generated method stub
		return sysDeptDao.queryReDeptNmByDeptNm(deptName);
	}

	@Override
	public BigDecimal queryReDeptIdByDeptNm(String deptName) {
		// TODO Auto-generated method stub
		return sysDeptDao.queryReDeptIdByDeptNm(deptName);
	}

	@Override
	public List<SysDeptEntity> queryListAll() {
		// TODO Auto-generated method stub
		return sysDeptDao.queryListAll();
	}
	
}
