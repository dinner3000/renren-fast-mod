package com.brh.channel.common.enums;
/**
 * 担保方式
 * @author lcl
 *
 */
public enum GuaranteeType {
	//01 质押担保、02抵押担保、03个人担保、04法人担保、05担保公司担保、06分级产品、07其他
	GU01("01","质押担保"),
	GU02("02","抵押担保"),
	GU03("03","个人担保"),
	GU04("04","法人担保"),
	GU05("05","担保公司担保"),
	GU06("06","分级产品"),
	GU07("07","其他");

    private final String code;
	private final String desc;

	private GuaranteeType(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static GuaranteeType getGuaranteeType(String code) {
		if (code == null) {
			return null;
		}
		for (GuaranteeType guaranteeType : values()) {
			if (guaranteeType.getCode().equals(code)) {
				return guaranteeType;
			}
		}
		return null;
	}
}
