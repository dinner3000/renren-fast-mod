package com.brh.channel.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 资本台账实体  保费代收台账实体 共用
 */
public class StandingBookEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String incomNo;// 进件编号
	private String contractNo;// 贷款合同号
	private String agencyNm;// 资金方名称
	private String structTyp;// 结构类型
	private String ciNm;// 借款企业
	private String licence;// 营业执照号
	private String taxNo;// 纳税人识别号
	private String legalNm;// 法人
	private String legalTele;// 法人电话
	private String loadPurpose;// 贷款用途 固定值：“企业流动资金补充”
	private String contactPer;// 联系人
	private String contactTele;// 联系人手机
	private String loadAmt;// 贷款金额
	private String loadTm1;// 贷款期限
	private String loadCycle1;// 贷款期限单位
	private String valueDate;// 起息日
	private String expiryDate;// 结息日
	private String repayDate;// 还款日
	private String repayWay;// 还款方式
	private String priorRepayTm;// 上期还款时间 空
	private String priorRepayFee;// 上期还款金额 空
	private String currentRepayTm;// 本期还款时间 空
	private String currentRepayFee;// 本期还款金额 空
	private BigDecimal loadRate;// 借款利率%
	private String status;// 状态：放款
	private String pledge;// 质押信息 详情：链接
	private String insurNo;// 保单号
	private String insurAmt;// 保单金额
	private String loadTm2;// 保单期限
	private String loadCycle2;// 保单期限单位
	private String payInsureTm;// payment-2002新增字段 保费收取日期
	private String insurFee;// 保费
	private String depositFeeTm;// 保证金收取日期 空
	private String depositFee;// 保证金金额
	private String integralFeeTm;// 邦积分服务费收取日期 空
	private String integralFee;// 邦积分服务费金额
	private String capitalSideFeeTm;// 资金方服务费收取日期 空
	private String capitalSideFee;// 资金方服务费金额
	private String remark;// 备注

	/**
	 * 保费代收台账新增字段
	 */
	// 保险开始时间
	private String insureBeginTm;
	// 保险终止时间
	private String insureEndTm;
	// 保险年费率
	private String insureFeeRate;
	// 绝对免赔率
	private String absExcess;
	// 核保日期
	private String underwriteTm;
	// 状态
	private String paySts;
	// 统一社会信用代码
	private String orgCode;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAgencyNm() {
		return agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}

	public String getStructTyp() {
		return structTyp;
	}

	public void setStructTyp(String structTyp) {
		this.structTyp = structTyp;
	}

	public String getCiNm() {
		return ciNm;
	}

	public void setCiNm(String ciNm) {
		this.ciNm = ciNm;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getLegalNm() {
		return legalNm;
	}

	public void setLegalNm(String legalNm) {
		this.legalNm = legalNm;
	}

	public String getLegalTele() {
		return legalTele;
	}

	public void setLegalTele(String legalTele) {
		this.legalTele = legalTele;
	}

	public String getLoadPurpose() {
		return loadPurpose;
	}

	public void setLoadPurpose(String loadPurpose) {
		this.loadPurpose = loadPurpose;
	}

	public String getContactPer() {
		return contactPer;
	}

	public void setContactPer(String contactPer) {
		this.contactPer = contactPer;
	}

	public String getContactTele() {
		return contactTele;
	}

	public void setContactTele(String contactTele) {
		this.contactTele = contactTele;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getRepayWay() {
		return repayWay;
	}

	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}

	public String getPriorRepayTm() {
		return priorRepayTm;
	}

	public void setPriorRepayTm(String priorRepayTm) {
		this.priorRepayTm = priorRepayTm;
	}

	public String getCurrentRepayTm() {
		return currentRepayTm;
	}

	public void setCurrentRepayTm(String currentRepayTm) {
		this.currentRepayTm = currentRepayTm;
	}

	public BigDecimal getLoadRate() {
		return loadRate;
	}

	public void setLoadRate(BigDecimal loadRate) {
		this.loadRate = loadRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPledge() {
		return pledge;
	}

	public void setPledge(String pledge) {
		this.pledge = pledge;
	}

	public String getInsurNo() {
		return insurNo;
	}

	public void setInsurNo(String insurNo) {
		this.insurNo = insurNo;
	}

	public String getInsurFee() {
		return insurFee;
	}

	public void setInsurFee(String insurFee) {
		this.insurFee = insurFee;
	}

	public String getDepositFeeTm() {
		return depositFeeTm;
	}

	public void setDepositFeeTm(String depositFeeTm) {
		this.depositFeeTm = depositFeeTm;
	}

	public String getIntegralFeeTm() {
		return integralFeeTm;
	}

	public void setIntegralFeeTm(String integralFeeTm) {
		this.integralFeeTm = integralFeeTm;
	}

	public String getCapitalSideFeeTm() {
		return capitalSideFeeTm;
	}

	public void setCapitalSideFeeTm(String capitalSideFeeTm) {
		this.capitalSideFeeTm = capitalSideFeeTm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoadTm1() {
		return loadTm1;
	}

	public void setLoadTm1(String loadTm1) {
		this.loadTm1 = loadTm1;
	}

	public String getLoadCycle1() {
		return loadCycle1;
	}

	public void setLoadCycle1(String loadCycle1) {
		this.loadCycle1 = loadCycle1;
	}

	public String getLoadTm2() {
		return loadTm2;
	}

	public void setLoadTm2(String loadTm2) {
		this.loadTm2 = loadTm2;
	}

	public String getLoadCycle2() {
		return loadCycle2;
	}

	public void setLoadCycle2(String loadCycle2) {
		this.loadCycle2 = loadCycle2;
	}

	public String getIncomNo() {
		return incomNo;
	}

	public void setIncomNo(String incomNo) {
		this.incomNo = incomNo;
	}

	public String getLoadAmt() {
		return loadAmt;
	}

	public void setLoadAmt(String loadAmt) {
		this.loadAmt = loadAmt;
	}

	public String getPriorRepayFee() {
		return priorRepayFee;
	}

	public void setPriorRepayFee(String priorRepayFee) {
		this.priorRepayFee = priorRepayFee;
	}

	public String getCurrentRepayFee() {
		return currentRepayFee;
	}

	public void setCurrentRepayFee(String currentRepayFee) {
		this.currentRepayFee = currentRepayFee;
	}

	public String getInsurAmt() {
		return insurAmt;
	}

	public void setInsurAmt(String insurAmt) {
		this.insurAmt = insurAmt;
	}

	public String getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(String depositFee) {
		this.depositFee = depositFee;
	}

	public String getIntegralFee() {
		return integralFee;
	}

	public void setIntegralFee(String integralFee) {
		this.integralFee = integralFee;
	}

	public String getCapitalSideFee() {
		return capitalSideFee;
	}

	public void setCapitalSideFee(String capitalSideFee) {
		this.capitalSideFee = capitalSideFee;
	}

	public String getPayInsureTm() {
		return payInsureTm;
	}

	public void setPayInsureTm(String payInsureTm) {
		this.payInsureTm = payInsureTm;
	}

	public String getInsureBeginTm() {
		return insureBeginTm;
	}

	public void setInsureBeginTm(String insureBeginTm) {
		this.insureBeginTm = insureBeginTm;
	}

	public String getInsureEndTm() {
		return insureEndTm;
	}

	public void setInsureEndTm(String insureEndTm) {
		this.insureEndTm = insureEndTm;
	}

	public String getInsureFeeRate() {
		return insureFeeRate;
	}

	public void setInsureFeeRate(String insureFeeRate) {
		this.insureFeeRate = insureFeeRate;
	}

	public String getAbsExcess() {
		return absExcess;
	}

	public void setAbsExcess(String absExcess) {
		this.absExcess = absExcess;
	}

	public String getUnderwriteTm() {
		return underwriteTm;
	}

	public void setUnderwriteTm(String underwriteTm) {
		this.underwriteTm = underwriteTm;
	}

	public String getPaySts() {
		return paySts;
	}

	public void setPaySts(String paySts) {
		this.paySts = paySts;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Override
	public String toString() {
		return "StandingBookEntity [incomNo=" + incomNo + ", contractNo="
				+ contractNo + ", agencyNm=" + agencyNm + ", structTyp="
				+ structTyp + ", ciNm=" + ciNm + ", licence=" + licence
				+ ", taxNo=" + taxNo + ", legalNm=" + legalNm + ", legalTele="
				+ legalTele + ", loadPurpose=" + loadPurpose + ", contactPer="
				+ contactPer + ", contactTele=" + contactTele + ", loadAmt="
				+ loadAmt + ", loadTm1=" + loadTm1 + ", loadCycle1="
				+ loadCycle1 + ", valueDate=" + valueDate + ", expiryDate="
				+ expiryDate + ", repayDate=" + repayDate + ", repayWay="
				+ repayWay + ", priorRepayTm=" + priorRepayTm
				+ ", priorRepayFee=" + priorRepayFee + ", currentRepayTm="
				+ currentRepayTm + ", currentRepayFee=" + currentRepayFee
				+ ", loadRate=" + loadRate + ", status=" + status + ", pledge="
				+ pledge + ", insurNo=" + insurNo + ", insurAmt=" + insurAmt
				+ ", loadTm2=" + loadTm2 + ", loadCycle2=" + loadCycle2
				+ ", payInsureTm=" + payInsureTm + ", insurFee=" + insurFee
				+ ", depositFeeTm=" + depositFeeTm + ", depositFee="
				+ depositFee + ", integralFeeTm=" + integralFeeTm
				+ ", integralFee=" + integralFee + ", capitalSideFeeTm="
				+ capitalSideFeeTm + ", capitalSideFee=" + capitalSideFee
				+ ", remark=" + remark + ", insureBeginTm=" + insureBeginTm
				+ ", insureEndTm=" + insureEndTm + ", insureFeeRate="
				+ insureFeeRate + ", absExcess=" + absExcess
				+ ", underwriteTm=" + underwriteTm + ", paySts=" + paySts
				+ ", orgCode=" + orgCode + "]";
	}

}
