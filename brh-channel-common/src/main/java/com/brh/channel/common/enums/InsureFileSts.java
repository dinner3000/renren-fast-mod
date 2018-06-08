package com.brh.channel.common.enums;
/**
 * 出单文件上传状态
 * @author lcl
 *
 */
public enum InsureFileSts {
	FILE00("00","未上传"),
	FILE01("01","已上传");

    private final String code;
	private final String desc;

	private InsureFileSts(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static InsureFileSts getInsureFileSts(String code) {
		if (code == null) {
			return null;
		}
		for (InsureFileSts insureFileSts : values()) {
			if (insureFileSts.getCode().equals(code)) {
				return insureFileSts;
			}
		}
		return null;
	}
}
