package com.brh.channel.common.enums;
/**
 *  性别
 */
public enum SexCode {
	MAN("0","男"),
	WOMAN("1","女");

    private final String code;
	private final String desc;

	private SexCode(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static SexCode getSexCodeByDesc(String desc) {
		if (desc == null) {
			return null;
		}
		for (SexCode sexCode : values()) {
			if (sexCode.getDesc().equals(desc)) {
				return sexCode;
			}
		}
		return null;
	}
}
