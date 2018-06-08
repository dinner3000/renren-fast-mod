package com.brh.channel.web.service;

import java.util.List;
import java.util.Map;

import com.brh.channel.web.entity.ProdInfoEntity;

/**
 * PROD_INFO
 * 
 * @date 2017-08-14 20:36:05
 */
public interface ProdInfoService {
	
	ProdInfoEntity queryObject(String prodId);
	
	List<ProdInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProdInfoEntity prodInfo);
	
	void update(ProdInfoEntity prodInfo);
	
	void delete(String prodId);
	
	void deleteBatch(String[] prodIds);

	int queryReProdNoByProdNo(String prodNo);

	void deleteProd(String prodId);

	int queryReProdNmByProdNm(String prodNm);

	String queryReProdIdByProdNm(String prodNm);
}
