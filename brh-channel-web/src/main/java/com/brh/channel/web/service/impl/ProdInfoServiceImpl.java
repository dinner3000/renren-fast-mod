package com.brh.channel.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.brh.channel.web.dao.ProdInfoDao;
import com.brh.channel.web.entity.ProdInfoEntity;
import com.brh.channel.web.service.ProdInfoService;



@Service("prodInfoService")
public class ProdInfoServiceImpl implements ProdInfoService {
	@Autowired
	private ProdInfoDao prodInfoDao;
	
	@Override
	public ProdInfoEntity queryObject(String prodId){
		return prodInfoDao.queryObject(prodId);
	}
	
	@Override
	public List<ProdInfoEntity> queryList(Map<String, Object> map){
		return prodInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return prodInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(ProdInfoEntity prodInfo){
		prodInfoDao.save(prodInfo);
	}
	
	@Override
	public void update(ProdInfoEntity prodInfo){
		prodInfoDao.update(prodInfo);
	}
	
	@Override
	public void delete(String prodId){
		prodInfoDao.delete(prodId);
	}
	
	@Override
	public void deleteBatch(String[] prodIds){
		prodInfoDao.deleteBatch(prodIds);
	}

	@Override
	public int queryReProdNoByProdNo(String prodNo) {
		// TODO Auto-generated method stub
		return prodInfoDao.queryReProdNoByProdNo(prodNo);
	}

	@Override
	public void deleteProd(String prodId) {
		// TODO Auto-generated method stub
		 prodInfoDao.deleteProd(prodId);
	}

	@Override
	public int queryReProdNmByProdNm(String prodNm) {
		// TODO Auto-generated method stub
		return prodInfoDao.queryReProdNmByProdNm(prodNm);
	}

	@Override
	public String queryReProdIdByProdNm(String prodNm) {
		// TODO Auto-generated method stub
		return prodInfoDao.queryReProdIdByProdNm(prodNm);
	}
	
}
