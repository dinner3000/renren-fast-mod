package com.brh.channel.common.enums;
/**
 * 缴费状态
 * @author lcl
 *
 */
public enum PaySts {
	PAYTST00("00","未缴费"),
	PAYTST01("01","已缴费");

    private final String code;
	private final String desc;

	private PaySts(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static PaySts getPaySts(String code) {
		if (code == null) {
			return null;
		}
		for (PaySts paySts : values()) {
			if (paySts.getCode().equals(code)) {
				return paySts;
			}
		}
		return null;
	}
}
