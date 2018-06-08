package com.brh.channel.common.enums;
/**
 * 贷款进件订单状态
 * @author lcl
 *
 */
public enum LoadStatus {

	MONEY("00","放款"),
	ENTER("01","录入"),
	PEND("02","待审核"),
	PASS("03","审核通过"),
	NOT("04","审核不通过"),
	ABANDON("05","放弃"),
	REJECT("06","拒绝");
	
    private final String code;
	
	private final String desc;
	
	private LoadStatus(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	public static LoadStatus getLoadStatus(String code) {
		if (code == null) {
			return null;
		}
		for (LoadStatus loadStatus : values()) {
			if (loadStatus.getCode().equals(code)) {
				return loadStatus;
			}
		}
		return null;
	}
}
