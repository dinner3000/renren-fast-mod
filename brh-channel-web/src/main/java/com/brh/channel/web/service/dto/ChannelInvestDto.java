package com.brh.channel.web.service.dto;

import java.io.Serializable;



/**
 * CHANNELINVEST
 * 
 * @author auto
 * @email auto
 * @date 2018-05-29 10:38:35
 */
public class ChannelInvestDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//PHONETEL
	private String phoneTel;
	//REALNAME
	private String realName;
	//INPUTTIME
	private String inputTime;
	//AMOUNT
	private Long amount;
	//PROJECTNAME
	private String projectName;
	//CHANNEL
	private String channel;
	public String getPhoneTel() {
		return phoneTel;
	}
	public void setPhoneTel(String phoneTel) {
		this.phoneTel = phoneTel;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
