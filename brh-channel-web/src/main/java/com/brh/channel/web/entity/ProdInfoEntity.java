package com.brh.channel.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * PROD_INFO
 * 
 * @date 2017-08-14 20:36:05
 */
public class ProdInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//PROD_ID
	private String prodId;
	//PROD_NO
	private String prodNo;
	//PROD_NM
	private String prodNm;
	//PROD_TYP
	private String prodTyp;
	//PROD_RATE
	private BigDecimal prodRate;
	//ISSUER
	private String issuer;
	//结构类型
	private String structTyp;
	//REPAY_WAY
	private String repayWay;
	//REPAY_CYCLE
	private String repayCycle;
	//REPAY_TIME
	private String repayTime;
	//LOAD_MIN
	private BigDecimal loadMin;
	//LOAD_MAX
	private BigDecimal loadMax;
	//PROD_STS
	private String prodSts;
	//OPER_ID
	private String operId;
	//CREATE_TM
	private String createTm;
	//TIMESTAMP
	private String timestamp;
	//货贷周期
	private String paymentCycle;
	private AgencyInfoEntity agencyInfo;
	//担保方式
	private String guaranteeTyp;  
	
	public AgencyInfoEntity getAgencyInfo() {
		return agencyInfo;
	}
	public void setAgencyInfo(AgencyInfoEntity agencyInfo) {
		this.agencyInfo = agencyInfo;
	}
	/**
	 * 设置：PROD_ID
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * 获取：PROD_ID
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * 设置：PROD_NO
	 */
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	/**
	 * 获取：PROD_NO
	 */
	public String getProdNo() {
		return prodNo;
	}
	/**
	 * 设置：PROD_NM
	 */
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	/**
	 * 获取：PROD_NM
	 */
	public String getProdNm() {
		return prodNm;
	}
	/**
	 * 设置：PROD_TYP
	 */
	public void setProdTyp(String prodTyp) {
		this.prodTyp = prodTyp;
	}
	/**
	 * 获取：PROD_TYP
	 */
	public String getProdTyp() {
		return prodTyp;
	}
	/**
	 * 设置：ISSUER
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	/**
	 * 获取：ISSUER
	 */
	public String getIssuer() {
		return issuer;
	}
	/**
	 * 设置：STRUCT_TYP
	 */
	public void setStructTyp(String structTyp) {
		this.structTyp = structTyp;
	}
	/**
	 * 获取：STRUCT_TYP
	 */
	public String getStructTyp() {
		return structTyp;
	}
	/**
	 * 设置：REPAY_WAY
	 */
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	/**
	 * 获取：REPAY_WAY
	 */
	public String getRepayWay() {
		return repayWay;
	}
	/**
	 * 设置：REPAY_CYCLE
	 */
	public void setRepayCycle(String repayCycle) {
		this.repayCycle = repayCycle;
	}
	/**
	 * 获取：REPAY_CYCLE
	 */
	public String getRepayCycle() {
		return repayCycle;
	}
	/**
	 * 设置：REPAY_TIME
	 */
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	/**
	 * 获取：REPAY_TIME
	 */
	public String getRepayTime() {
		return repayTime;
	}
	
	/**
	 * 设置：PROD_STS
	 */
	public void setProdSts(String prodSts) {
		this.prodSts = prodSts;
	}
	/**
	 * 获取：PROD_STS
	 */
	public String getProdSts() {
		return prodSts;
	}
	/**
	 * 设置：OPER_ID
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}
	/**
	 * 获取：OPER_ID
	 */
	public String getOperId() {
		return operId;
	}
	/**
	 * 设置：CREATE_TM
	 */
	public void setCreateTm(String createTm) {
		this.createTm = createTm;
	}
	/**
	 * 获取：CREATE_TM
	 */
	public String getCreateTm() {
		return createTm;
	}
	/**
	 * 设置：TIMESTAMP
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 获取：TIMESTAMP
	 */
	public String getTimestamp() {
		return timestamp;
	}
	public String getPaymentCycle() {
		return paymentCycle;
	}
	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}
	
	public BigDecimal getProdRate() {
		return prodRate;
	}
	public void setProdRate(BigDecimal prodRate) {
		this.prodRate = prodRate;
	}
	public BigDecimal getLoadMin() {
		return loadMin;
	}
	public void setLoadMin(BigDecimal loadMin) {
		this.loadMin = loadMin;
	}
	public BigDecimal getLoadMax() {
		return loadMax;
	}
	public void setLoadMax(BigDecimal loadMax) {
		this.loadMax = loadMax;
	}
	public String getGuaranteeTyp() {
		return guaranteeTyp;
	}
	public void setGuaranteeTyp(String guaranteeTyp) {
		this.guaranteeTyp = guaranteeTyp;
	}
	@Override
	public String toString() {
		return "ProdInfoEntity [prodId=" + prodId + ", prodNo=" + prodNo
				+ ", prodNm=" + prodNm + ", prodTyp=" + prodTyp + ", prodRate="
				+ prodRate + ", issuer=" + issuer + ", structTyp=" + structTyp
				+ ", repayWay=" + repayWay + ", repayCycle=" + repayCycle
				+ ", repayTime=" + repayTime + ", loadMin=" + loadMin
				+ ", loadMax=" + loadMax + ", prodSts=" + prodSts + ", operId="
				+ operId + ", createTm=" + createTm + ", timestamp="
				+ timestamp + ", paymentCycle=" + paymentCycle
				+ ", agencyInfo=" + agencyInfo + ", guaranteeTyp=" + guaranteeTyp + "]";
	}
	
	
	
}
