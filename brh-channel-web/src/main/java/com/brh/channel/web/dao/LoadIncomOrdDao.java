package com.brh.channel.web.dao;

import java.util.List;
import java.util.Map;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.common.utils.Query;
import com.brh.channel.web.entity.LoadIncomOrdEntity;
import com.brh.channel.web.entity.StandingBookEntity;

/**
 * LOAD_INCOM_ORD
 * 
 * @date 2017-08-14 10:51:49
 */
public interface LoadIncomOrdDao extends BaseDao<LoadIncomOrdEntity> {
	int deleteByPrimaryKey(String incomNo);

	int insert(LoadIncomOrdEntity record);

	int insertSelective(LoadIncomOrdEntity record);

	LoadIncomOrdEntity selectByPrimaryKey(String incomNo);

	int updateByPrimaryKeySelective(LoadIncomOrdEntity record);

	int updateByPrimaryKey(LoadIncomOrdEntity record);

	List<LoadIncomOrdEntity> selectToDoByUserId(Map<String, Object> map);

	List<StandingBookEntity> selectStandingBook(Map<String, Object> map);
	
	int selectStandingBookTotal(Map<String, Object> map);

	int queryToDoTotalByUserId(Map<String, Object> map);

	List<LoadIncomOrdEntity> queryListByCurStep(Query query);

	int queryTotalByCurStep(Query query);
	
	List<StandingBookEntity> selectInsureFeeBook(Map<String, Object> map);
	
	int selectInsureFeeBookTotal(Map<String, Object> map);
	
	List<LoadIncomOrdEntity> queryPdfFilesInfo(LoadIncomOrdEntity loadIncomOrdEntity);

	LoadIncomOrdEntity queryCiNoByIncomNo(String incomNo);

	void updateInsureFileStsByIncomNo(String incomNo);
}
