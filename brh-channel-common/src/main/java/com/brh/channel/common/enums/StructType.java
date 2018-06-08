package com.brh.channel.common.enums;
/**
 * 结构类型
 * @author lcl
 *
 */
public enum StructType {
	//01直接融资、02信托计划、03债权转让、04定向资管、05委托贷款、06其他
	ST01("01","直接融资"),
	ST02("02","信托计划"),
	ST03("03","债权转让"),
	ST04("04","定向资管"),
	ST05("05","委托贷款"),
	ST06("06","其他");

    private final String code;
	private final String desc;

	private StructType(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static StructType getStructType(String code) {
		if (code == null) {
			return null;
		}
		for (StructType structType : values()) {
			if (structType.getCode().equals(code)) {
				return structType;
			}
		}
		return null;
	}
}
