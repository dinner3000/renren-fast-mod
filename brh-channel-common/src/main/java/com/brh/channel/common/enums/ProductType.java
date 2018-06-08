package com.brh.channel.common.enums;
/**
 * 产品类型
 * @author WJLong
 *
 */
public enum ProductType {

	PERSONAL("0","车抵贷","car"),
	COMPANY("1","房抵贷","hou");

    private final String code;
	private final String desc;
	private final String type;

	private ProductType(String code, String desc, String type){
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

	public static ProductType getProductType(String code) {
		if (code == null) {
			return null;
		}
		for (ProductType productType : values()) {
			if (productType.getCode().equals(code)) {
				return productType;
			}
		}
		return null;
	}
}
