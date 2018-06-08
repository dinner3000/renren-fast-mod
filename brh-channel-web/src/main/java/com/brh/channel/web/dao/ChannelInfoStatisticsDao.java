package com.brh.channel.web.dao;

import java.util.List;
import java.util.Map;

import com.brh.channel.common.utils.Query;
import com.brh.channel.web.service.dto.ChannelInvestDto;
import com.brh.channel.web.service.dto.ChannelRegistDto;
import com.brh.channel.web.service.dto.ChannelStatisticsDto;

public interface ChannelInfoStatisticsDao {
	/**
	 * 根据渠道编号查询渠道注册信息总数
	 * @param map
	 * @return
	 */
	int queryTotalChannelRegist(Map<String, Object> map);
	/**
	 * 根据渠道编号查询渠道注册信息
	 * @param channel
	 */
	List<ChannelRegistDto> getChannelRegistInfoList(Map<String, Object> map);
	/**
	 * 按条件查询渠道注册信息总数据
	 * @param map
	 * @return
	 */
	List<ChannelRegistDto> getChannelRegistInfoListTotal(Map<String, Object> map);
	/**
	 * 根据渠道编号查询渠道投资信息总数
	 * @param map
	 * @return
	 */
	int queryTotalChannelInvest(Map<String, Object> map);
	/**
	 * 根据渠道编号查询渠道投资信息
	 * @param channel
	 */
	List<ChannelInvestDto> getChannelInvestInfoList(Map<String, Object> map);
	/**
	 * 按条件查询渠道投资信息总数据
	 * @param map
	 * @return
	 */
	List<ChannelInvestDto> getChannelInvestInfoListTotal(Map<String, Object> map);
	/**
	 * 查询各渠道汇总信息
	 * @param map
	 * @return
	 */
	List<ChannelStatisticsDto> queryList(Map<String, Object> map);
	/**
	 * 查询各渠道汇总信息总数，分页使用
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);
	/**
	 * 按条件查询渠道被邀请人投资信息总数据
	 * @param params
	 * @return
	 */
	List<ChannelInvestDto> getChannelInviteeInfoListTotal(Map<String, Object> params);
	/**
	 * 根据渠道编号查询渠道被邀请人投资信息总数
	 * @param query
	 * @return
	 */
	int queryTotalChannelInvitee(Query query);
	/**
	 * 根据渠道编号查询渠道被邀请人投资信息
	 * @param query
	 * @return
	 */
	List<ChannelInvestDto> getChannelInviteeInfoList(Query query);
	/**
	 *  根据渠道编号查询渠道汇总信息
	 * @param query
	 * @return
	 */
	List<ChannelStatisticsDto> getSingleChannelSumInfo(Query query);
}
