package com.brh.channel.web.service;

import java.util.List;
import java.util.Map;

import com.brh.channel.web.entity.AgencyInfoEntity;

/**
 * AGENCY_INFO
 * 
 * @date 2017-08-08 14:44:47
 */
public interface AgencyInfoService {
	
	AgencyInfoEntity queryObject(String agencyId);
	
	List<AgencyInfoEntity> queryObject(AgencyInfoEntity agencyInfo);
	
	List<AgencyInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AgencyInfoEntity agencyInfo);
	
	void update(AgencyInfoEntity agencyInfo);
	
	void delete(String agencyId);
	
	void deleteBatch(String[] agencyIds);

	String getCooTypByUserId(String userId);

	String getAgencyIdByUserId(String userId);
	
	AgencyInfoEntity queryAgencyInfoByAgencyId(String agencyId);
}
