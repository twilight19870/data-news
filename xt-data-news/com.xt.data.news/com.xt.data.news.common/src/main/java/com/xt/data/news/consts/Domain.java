package com.xt.data.news.consts;

/**
 *  领域对象
 * @author vivi207
 *
 */
public enum Domain {
	purchase("01", "采购"),
	storage("02", "仓储"),
	delivery("03", "配送"),
	operators("04", "运营"),
	;
	
	/**
	 * 领域代码
	 */
	private String code;
	
	/**
	 * 领域名称
	 */
	private String label;
	
	Domain(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	} 
	public String getLabel() {
		return label;
	}
	
	/**
	 * 根据代码获取渠道
	 * @param code
	 * @return
	 */
    public static Domain codeOf(String code) {
    	if(code==null) {
    		return null;
    	}
        for(Domain m : Domain.values()) {
            if(m.getCode().equals(code)) {
                return m;
            }
        }
        return null;
    }
	
}
