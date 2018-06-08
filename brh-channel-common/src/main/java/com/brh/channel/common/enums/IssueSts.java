package com.brh.channel.common.enums;
/**
 * 出单状态
 * @author lcl
 *
 */
public enum IssueSts {
	ISSUE00("00","未出单"),
	ISSUE01("01","已出单"),
	ISSUE02("02","出单处理中"),
	ISSUE03("03","出单失败");

    private final String code;
	private final String desc;

	private IssueSts(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static IssueSts getIssueSts(String code) {
		if (code == null) {
			return null;
		}
		for (IssueSts issueSts : values()) {
			if (issueSts.getCode().equals(code)) {
				return issueSts;
			}
		}
		return null;
	}
}
