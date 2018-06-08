package com.brh.channel.common.enums;
/**
 * 客户状态
 * @author lcl
 *
 */
public enum CustStatus {

	PASS("00","审核通过"),
	ENTER("01","录入"),
	PEND("02","待审核"),
	NOT("03","审核不通过"),
	ABANDON("04","放弃");
	
    private final String code;
	
	private final String desc;
	
	private CustStatus(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
