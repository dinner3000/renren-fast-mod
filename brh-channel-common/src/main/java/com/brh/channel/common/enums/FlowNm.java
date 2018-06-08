package com.brh.channel.common.enums;
/**
 * 流程类型枚举
 * @author lcl
 *
 */
public enum FlowNm {
	LOAD_FLOW("loadCheck","贷款审核流程"),
	INCOM_FLOW("carIncom","一二次进件流程");
	
    private final String code;
	
	private final String desc;
	
	private FlowNm(String code,String desc){
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
