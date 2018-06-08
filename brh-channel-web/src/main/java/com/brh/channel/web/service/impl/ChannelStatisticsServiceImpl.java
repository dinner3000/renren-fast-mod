package com.brh.channel.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.web.dao.ChannelInfoStatisticsDao;
import com.brh.channel.web.dao.CodeLibraryDao;
import com.brh.channel.web.entity.CodeLibraryEntity;
import com.brh.channel.web.service.ChannelStatisticsService;
import com.brh.channel.web.service.dto.ChannelInvestDto;
import com.brh.channel.web.service.dto.ChannelRegistDto;
import com.brh.channel.web.service.dto.ChannelStatisticsDto;

@Service("channelStatisticsService")
public class ChannelStatisticsServiceImpl implements ChannelStatisticsService {
	private Logger logger = LoggerFactory.getLogger(ChannelStatisticsServiceImpl.class);
	@Autowired
	private ChannelInfoStatisticsDao channelInfoDao;
	@Autowired
	CodeLibraryDao codeLibraryDao;

	@Override
	public List<ChannelStatisticsDto> queryList(Query query) {
//		if(!map.containsKey("startDay")
//				||!map.containsKey("endDay")){
//			map.put("startDay", DateUtils.getCurrentDate()+" 00:00:00");
//			map.put("endDay", DateUtils.getCurrentDate()+" 23:59:59");
//		}
		CodeLibraryEntity codeLibraryEntity = new CodeLibraryEntity();
		codeLibraryEntity.setCodeNo("ChannelSet");
		codeLibraryEntity = codeLibraryDao.queryObject(codeLibraryEntity);
		if(null==codeLibraryEntity){
			logger.error("渠道统计主渠道列表未定义");
			//throw new CommonException("");
			return null;
		}
		String []channel = codeLibraryEntity.getAttribute1().split("\\,");
		List<ChannelStatisticsDto> list = new ArrayList<ChannelStatisticsDto>();
		List<ChannelStatisticsDto> listDto = null;
		for(int i=0;i<channel.length;i++){
			query.put("channel", channel[i]);
			listDto = channelInfoDao.getSingleChannelSumInfo(query);
			if(null==listDto||listDto.isEmpty()){
				logger.info("没有此渠道【{}】统计",channel[i]);
				continue;
			}
			list.add(listDto.get(0));
		}
		return list;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		if(!map.containsKey("startDay")
				||!map.containsKey("endDay")){
			map.put("startDay", DateUtils.getCurrentDate()+" 00:00:00");
			map.put("endDay", DateUtils.getCurrentDate()+" 23:59:59");
		}

		return channelInfoDao.queryTotal(map);
	}
	@Override
	public int queryTotalChannelRegist(Query query){
		return channelInfoDao.queryTotalChannelRegist(query);
	}

	@Override
	public List<ChannelRegistDto> getChannelRegistInfo(Map<String, Object> map) {
		return channelInfoDao.getChannelRegistInfoList(map);
	}

	@Override
	public List<ChannelRegistDto> getChannelRegistInfoTotal(Map<String, Object> map) {
		return channelInfoDao.getChannelRegistInfoListTotal(map);
	}
	@Override
	public List<ChannelInvestDto> getChannelInvestInfo(Map<String, Object> map) {
		return channelInfoDao.getChannelInvestInfoList(map);
	}

	@Override
	public List<ChannelInvestDto> getChannelInvestInfoTotal(Map<String, Object> map) {
		return channelInfoDao.getChannelInvestInfoListTotal(map);
	}

	@Override
	public int queryTotalChannelInvest(Query query) {
		return channelInfoDao.queryTotalChannelInvest(query);
	}

	@Override
	public List<ChannelInvestDto> getChannelInviteeInfoTotal(Map<String, Object> params) {
		return channelInfoDao.getChannelInviteeInfoListTotal(params);
	}

	@Override
	public int queryTotalChannelInvitee(Query query) {
		return channelInfoDao.queryTotalChannelInvitee(query);
	}

	@Override
	public List<ChannelInvestDto> getChannelInviteeInfo(Query query) {
		return channelInfoDao.getChannelInviteeInfoList(query);
	}

	@Override
	public List<ChannelStatisticsDto> querySingleInfo(Query query) {
		return channelInfoDao.getSingleChannelSumInfo(query);
	}
}
