package com.brh.channel.web.service.dto;

import java.io.Serializable;
import java.util.Date;



/**
 * USER_INFO
 * 
 * @author auto
 * @email auto
 * @date 2018-05-25 09:38:45
 */
public class ChannelRegistDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//PHONETEL
	private String phoneTel;
	//INPUTTIME
	private String inputTime;
	//REALNAME
	private String realName;
	//CERTID
	private String certId;
	//CHANNELNAME
	private String channelName;
	//REFERRERPHONE
	private String referrerPhone;
	//AUTHSTATUS
	private String authStatus;
	//CARDSTATUS
	private String cardStatus;
	//USERID
	private String userId;
	//CHANNEL
	private String channel;
	public String getPhoneTel() {
		return phoneTel;
	}
	public void setPhoneTel(String phoneTel) {
		this.phoneTel = phoneTel;
	}
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReferrerPhone() {
		return referrerPhone;
	}
	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

}
