package com.brh.channel.web.dao;


import java.util.List;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.web.entity.AgencyInfoEntity;

/**
 * AGENCY_INFO
 * 
 * @date 2017-08-08 14:44:47
 */
public interface AgencyInfoDao extends BaseDao<AgencyInfoEntity> {
	int insertSelective(AgencyInfoEntity record);

	int updateByPrimaryKeySelective(AgencyInfoEntity record);

	String getCooTypByUserId(String userId);

	String getAgencyIdByUserId(String userId);

	List<AgencyInfoEntity> queryAgencyEntity(AgencyInfoEntity record);

	AgencyInfoEntity queryAgencyInfoByAgencyId(String agencyId);
}
