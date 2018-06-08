package com.brh.channel.web.service;


import java.util.List;
import java.util.Map;

import com.brh.channel.common.utils.Query;
import com.brh.channel.web.service.dto.ChannelInvestDto;
import com.brh.channel.web.service.dto.ChannelRegistDto;
import com.brh.channel.web.service.dto.ChannelStatisticsDto;

/**
 * CHANNEL_STATISTICS
 * 
 * @author auto
 * @email auto
 * @date 2018-05-23 14:55:25
 */
public interface ChannelStatisticsService {
	
	/**
	 * 渠道汇总统计列表查询）
	 * @param query
	 * @return
	 */
	List<ChannelStatisticsDto> queryList(Query query);
	/**
	 * 渠道汇总统计列表总记录数
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);
	/**
	 * 渠道注册统计列表查询（分页）
	 * @param map
	 * @return
	 */
	List<ChannelRegistDto> getChannelRegistInfo(Map<String, Object> map);
	/**
	 * 渠道注册统计总记录查询
	 * @param map
	 * @return
	 */
	List<ChannelRegistDto> getChannelRegistInfoTotal(Map<String, Object> map);
	/**
	 * 渠道注册统计总记录数查询
	 * @param query
	 * @return
	 */
	int queryTotalChannelRegist(Query query);
	/**
	 * 渠道投资统计列表查询（分页）
	 * @param map
	 * @return
	 */
	List<ChannelInvestDto> getChannelInvestInfo(Map<String, Object> map);
	/**
	 * 渠道投资统计总记录查询
	 * @param map
	 * @return
	 */
	List<ChannelInvestDto> getChannelInvestInfoTotal(Map<String, Object> map);
	/**
	 * 渠道投资统计总记录数查询
	 * @param query
	 * @return
	 */
	int queryTotalChannelInvest(Query query);
	/**
	 * 渠道被邀请人投资统计总记录查询
	 * @param params
	 * @return
	 */
	List<ChannelInvestDto> getChannelInviteeInfoTotal(Map<String, Object> params);
	/**
	 * 渠道被邀请人投资统计总记录数查询
	 * @param query
	 * @return
	 */
	int queryTotalChannelInvitee(Query query);
	/**
	 *  渠道被邀请人投资统计列表查询（分页）
	 * @param query
	 * @return
	 */
	List<ChannelInvestDto> getChannelInviteeInfo(Query query);
	/**
	 * 根据渠道编号查询汇总信息
	 * @param query
	 * @return
	 */
	List<ChannelStatisticsDto> querySingleInfo(Query query);
	
}
