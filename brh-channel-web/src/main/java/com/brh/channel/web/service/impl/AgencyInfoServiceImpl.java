package com.brh.channel.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.channel.web.dao.AgencyInfoDao;
import com.brh.channel.web.entity.AgencyInfoEntity;
import com.brh.channel.web.service.AgencyInfoService;

import java.util.List;
import java.util.Map;



@Service("agencyInfoService")
public class AgencyInfoServiceImpl implements AgencyInfoService {
	@Autowired
	private AgencyInfoDao agencyInfoDao;
	
	@Override
	public AgencyInfoEntity queryObject(String agencyId){
		return agencyInfoDao.queryObject(agencyId);
	}
	
	@Override
	public List<AgencyInfoEntity> queryList(Map<String, Object> map){
		return agencyInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return agencyInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(AgencyInfoEntity agencyInfo){
		agencyInfoDao.insertSelective(agencyInfo);
	}
	
	@Override
	public void update(AgencyInfoEntity agencyInfo){
		agencyInfoDao.updateByPrimaryKeySelective(agencyInfo);
	}
	
	@Override
	public void delete(String agencyId){
		agencyInfoDao.delete(agencyId);
	}
	
	@Override
	public void deleteBatch(String[] agencyIds){
		agencyInfoDao.deleteBatch(agencyIds);
	}

	@Override
	public String getCooTypByUserId(String userId) {
		// TODO Auto-generated method stub
		return agencyInfoDao.getCooTypByUserId(userId);
	}

	@Override
	public String getAgencyIdByUserId(String userId) {
		// TODO Auto-generated method stub
		return agencyInfoDao.getAgencyIdByUserId(userId);
	}
	@Override
	public List<AgencyInfoEntity> queryObject(AgencyInfoEntity agencyInfo) {
		// TODO Auto-generated method stub
		return agencyInfoDao.queryAgencyEntity(agencyInfo);
	}

	@Override
	public AgencyInfoEntity queryAgencyInfoByAgencyId(String agencyId) {
		// TODO Auto-generated method stub
		return agencyInfoDao.queryAgencyInfoByAgencyId(agencyId);
	}
	
}
