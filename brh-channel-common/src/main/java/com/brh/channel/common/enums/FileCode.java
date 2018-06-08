package com.brh.channel.common.enums;
/**
 *  文件编码
 */
public enum FileCode {
	FILE3001("3001", "押品清单"),
	FILE3002("3002", "质押车辆信息登记表"),
	FILE3003("3003", "车辆照片"),
	FILE3004("3004", "手续照片"),

	FILE4003("4002","电子保单"),
	FILE4004("4004","付款凭证");

    private final String code;
	private final String desc;

	private FileCode(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static FileCode getFileCode(String code) {
		if (code == null) {
			return null;
		}
		for (FileCode fileCode : values()) {
			if (fileCode.getCode().equals(code)) {
				return fileCode;
			}
		}
		return null;
	}
}
