package com.brh.channel.common.enums;
/**
 * 时间类型
 * @author lcl
 *
 */
public enum TimeCls {
	TMY("Y","年"),
	TMM("M","月"),
	TMD("D","日");
    private final String code;
	private final String desc;

	private TimeCls(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static TimeCls getTimeCls(String code) {
		if (code == null) {
			return null;
		}
		for (TimeCls timeCls : values()) {
			if (timeCls.getCode().equals(code)) {
				return timeCls;
			}
		}
		return null;
	}
}
