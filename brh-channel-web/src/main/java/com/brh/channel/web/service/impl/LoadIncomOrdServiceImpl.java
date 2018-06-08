package com.brh.channel.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.channel.common.utils.Query;
import com.brh.channel.web.dao.LoadIncomOrdDao;
import com.brh.channel.web.entity.LoadIncomOrdEntity;
import com.brh.channel.web.entity.StandingBookEntity;
import com.brh.channel.web.service.LoadIncomOrdService;



@Service("loadIncomOrdService")
public class LoadIncomOrdServiceImpl implements LoadIncomOrdService {
	@Autowired
	private LoadIncomOrdDao loadIncomOrdDao;
	
	@Override
	public LoadIncomOrdEntity queryObject(String incomNo){
		return loadIncomOrdDao.selectByPrimaryKey(incomNo);
	}
	
	@Override
	public List<LoadIncomOrdEntity> queryList(Map<String, Object> map){
		return loadIncomOrdDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return loadIncomOrdDao.queryTotal(map);
	}
	
	@Override
	public void save(LoadIncomOrdEntity loadIncomOrd){
		loadIncomOrdDao.insertSelective(loadIncomOrd);
	}
	
	@Override
	public void update(LoadIncomOrdEntity loadIncomOrd){
		loadIncomOrdDao.updateByPrimaryKeySelective(loadIncomOrd);
	}
	
	@Override
	public void delete(String incomNo){
		loadIncomOrdDao.deleteByPrimaryKey(incomNo);
	}
	
	@Override
	public void deleteBatch(String[] incomNos){
		loadIncomOrdDao.deleteBatch(incomNos);
	}

	@Override
	public List<LoadIncomOrdEntity> queryToDoList(Map<String, Object> map) {
		return loadIncomOrdDao.selectToDoByUserId(map);
	}

	@Override
	public int queryToDoTotal(Map<String, Object> map){
		return loadIncomOrdDao.queryToDoTotalByUserId(map);
	}

	@Override
	public List<StandingBookEntity> queryStandingBookList(
			Map<String, Object> map) {
		return loadIncomOrdDao.selectStandingBook(map);
	}

	@Override
	public int queryStandingBookListTotal(Map<String, Object> map) {
		return loadIncomOrdDao.selectStandingBookTotal(map);
	}

	@Override
	public List<LoadIncomOrdEntity> queryListByCurStep(Query query) {
		// TODO Auto-generated method stub
		return loadIncomOrdDao.queryListByCurStep(query);
	}

	@Override
	public int queryTotalByCurStep(Query query) {
		// TODO Auto-generated method stub
		return loadIncomOrdDao.queryTotalByCurStep(query);
	}
	@Override
	public List<StandingBookEntity> queryInsureFeeBookList(
			Map<String, Object> map) {
		return loadIncomOrdDao.selectInsureFeeBook(map);
	}

	@Override
	public int queryInsureFeeBookListTotal(Map<String, Object> map) {
		return loadIncomOrdDao.selectInsureFeeBookTotal(map);
	}
	@Override
	public List<LoadIncomOrdEntity> queryPdfFilesInfo(LoadIncomOrdEntity loadIncomOrdEntity) {
		// TODO Auto-generated method stub
		return loadIncomOrdDao.queryPdfFilesInfo(loadIncomOrdEntity);
	}

	@Override
	public LoadIncomOrdEntity queryCiNoByIncomNo(String incomNo) {
		// TODO Auto-generated method stub
		return loadIncomOrdDao.queryCiNoByIncomNo(incomNo);
	}

	@Override
	public void updateInsureFileStsByIncomNo(String incomNo) {
		// TODO Auto-generated method stub
		loadIncomOrdDao.updateInsureFileStsByIncomNo(incomNo);
	}
}
