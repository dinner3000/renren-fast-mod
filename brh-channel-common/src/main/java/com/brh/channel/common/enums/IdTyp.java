package com.brh.channel.common.enums;

public enum IdTyp {
		ID01("00","身份证"),
		ID02("01","护照"),
		ID03("02","军官证"),
		ID04("03","港澳居民往来内地通行证"),
		ID05("04","台胞证"),
		ID06("05","其他");

	    private final String code;
		private final String desc;

		private IdTyp(String code, String desc){
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
		
		public static IdTyp getIdType(String code) {
			if (code == null) {
				return null;
			}
			for (IdTyp idTyp : values()) {
				if (idTyp.getCode().equals(code)) {
					return idTyp;
				}
			}
			return null;
		}
}
