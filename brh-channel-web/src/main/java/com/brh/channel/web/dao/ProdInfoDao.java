package com.brh.channel.web.dao;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.web.entity.ProdInfoEntity;

/**
 * PROD_INFO
 * 
 * @date 2017-08-14 20:36:05
 */
public interface ProdInfoDao extends BaseDao<ProdInfoEntity> {

	int queryReProdNoByProdNo(String prodNo);

	void deleteProd(String prodId);

	int queryReProdNmByProdNm(String prodNm);

	String queryReProdIdByProdNm(String prodNm);
	
}
