package com.brh.channel.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * CHANNEL_STATISTICS
 * 
 * @author auto
 * @email auto
 * @date 2018-05-23 14:55:25
 */
public class ChannelStatisticsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//出借总金额
	private BigDecimal amountTotal;
	//注册总人数
	private Integer registNum;
	//出借总人数
	private Integer investUserTotal;
	//出借总笔数
	private Integer timesTotal;
	//渠道
	private String channel;
	//渠道名称
	private String channelName;
	
	public BigDecimal getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}
	public Integer getRegistNum() {
		return registNum;
	}
	public void setRegistNum(Integer registNum) {
		this.registNum = registNum;
	}
	public Integer getInvestUserTotal() {
		return investUserTotal;
	}
	public void setInvestUserTotal(Integer investUserTotal) {
		this.investUserTotal = investUserTotal;
	}
	public Integer getTimesTotal() {
		return timesTotal;
	}
	public void setTimesTotal(Integer timesTotal) {
		this.timesTotal = timesTotal;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
