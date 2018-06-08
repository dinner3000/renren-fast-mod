package com.brh.channel.web.service;

import java.util.List;
import java.util.Map;

import com.brh.channel.common.utils.Query;
import com.brh.channel.web.entity.LoadIncomOrdEntity;
import com.brh.channel.web.entity.StandingBookEntity;

/**
 * LOAD_INCOM_ORD
 * 
 * @date 2017-08-14 10:51:49
 */
public interface LoadIncomOrdService {
	
	LoadIncomOrdEntity queryObject(String incomNo);
	
	List<LoadIncomOrdEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LoadIncomOrdEntity loadIncomOrd);
	
	void update(LoadIncomOrdEntity loadIncomOrd);
	
	void delete(String incomNo);
	
	void deleteBatch(String[] incomNos);
	
	List<LoadIncomOrdEntity> queryToDoList(Map<String, Object> map);

	int queryToDoTotal(Map<String, Object> map);
	
	List<StandingBookEntity> queryStandingBookList(Map<String, Object> map);
	
	int queryStandingBookListTotal(Map<String, Object> map);

	List<LoadIncomOrdEntity> queryListByCurStep(Query query);

	int queryTotalByCurStep(Query query);
	
	List<StandingBookEntity> queryInsureFeeBookList(Map<String, Object> map);
	
	int queryInsureFeeBookListTotal(Map<String, Object> map);

	List<LoadIncomOrdEntity> queryPdfFilesInfo(LoadIncomOrdEntity loadIncomOrdEntity);

	LoadIncomOrdEntity queryCiNoByIncomNo(String incomNo);

	void updateInsureFileStsByIncomNo(String incomNo);
}
