package com.brh.channel.common.enums;

public enum CurStep {
	LOAD("0","贷款审核"),
	FIRST("1","一次进件"),
	SECOND("2","二次进件");
	
    private final String code;
	
	private final String desc;
	
	private CurStep(String code,String desc){
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
