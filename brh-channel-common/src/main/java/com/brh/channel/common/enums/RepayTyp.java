package com.brh.channel.common.enums;
/**
 * 还款方式
 * 0:一次性还本付息  1:先息后本  2:等额本息
 * @author lcl
 *
 */
public enum RepayTyp {
	PAYWAY0("0","等额本金"),
	PAYWAY1("1","等额本息"),
	PAYWAY2("2","一次性还本付息"),
	PAYWAY3("3","月度结息,到期还本"),
	PAYWAY4("4","季度结息,到期还本"),
	PAYWAY5("5","其他");
    private final String code;
	private final String desc;

	private RepayTyp(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static RepayTyp getRepayTyp(String code) {
		if (code == null) {
			return null;
		}
		for (RepayTyp repayTyp : values()) {
			if (repayTyp.getCode().equals(code)) {
				return repayTyp;
			}
		}
		return null;
	}
}
