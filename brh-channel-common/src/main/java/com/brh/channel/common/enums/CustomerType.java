package com.brh.channel.common.enums;
/**
 * 客户类型
 * @author WJLong
 *
 */
public enum CustomerType {

	PERSONAL("0","个人客户","per"),
	COMPANY("1","企业各户","com");

    private final String code;
	private final String desc;
	private final String type;

	private CustomerType(String code, String desc, String type){
		this.code = code;
		this.desc = desc;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getType() {
		return type;
	}

	public static CustomerType getCustomerType(String code) {
		if (code == null) {
			return null;
		}
		for (CustomerType customerType : values()) {
			if (customerType.getCode().equals(code)) {
				return customerType;
			}
		}
		return null;
	}
}
