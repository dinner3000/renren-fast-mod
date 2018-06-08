package com.brh.channel.web.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * CUSTOMER_INFO
 * 
 * @date 2017-08-15 10:26:16
 */
public class CustomerInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//CI_NO
	private String ciNo;
	//CI_NM
	private String ciNm;
	//CI_TYP
	private String ciTyp;
	//ID_TYP
	private String idTyp;
	//ID_NO
	private String idNo;
	//LEGAL_NM
	private String legalNm;
	//LEGAL_TELE
	private String legalTele;//法人手机
	//纳税人识别号  
	private String taxNo;
	//LICENCE
	private String licence;
	//ORG_CODE
	private String orgCode; //统一社会信用编码
	//CONTACT_ADS
	private String contactAds;
	//CONTACT_PER
	private String contactPer;
	//CONTACT_TELE
	private String contactTele;
	//CONTACT_PHONE
	private String contactPhone;
	//POST_CODE
	private String postCode;
	//ADDRESS
	private String address;
	//FAX
	private String fax;
	//EMAIL
	private String email;
	//WEB_URL
	private String webUrl;
	//STATUS
	private String status;
	//OPER_ID
	private String operId;
	//CREATE_TM
	private String createTm;
	//TIME_STAMP
	private String timeStamp;
	//客户风险
	private String riskLvl;
	//客户备注
	private String remark;
	//纳税人类型
	private String payerTyp;
	//税务登记开户行
	private String regBankCode;
	//税务登记开户行账号
	private String regBankNo;
	//税务登记公司地址
	private String regComAds;
	//税务登记公司电话
	private String regComTele;
	
	private String organCode; //组织机构代码
	
	/**
	 * 设置：CI_NO
	 */
	public void setCiNo(String ciNo) {
		this.ciNo = ciNo;
	}
	/**
	 * 获取：CI_NO
	 */
	public String getCiNo() {
		return ciNo;
	}
	/**
	 * 设置：CI_NM
	 */
	public void setCiNm(String ciNm) {
		this.ciNm = ciNm;
	}
	/**
	 * 获取：CI_NM
	 */
	public String getCiNm() {
		return ciNm;
	}
	
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	/**
	 * 设置：CI_TYP
	 */
	public void setCiTyp(String ciTyp) {
		this.ciTyp = ciTyp;
	}
	/**
	 * 获取：CI_TYP
	 */
	public String getCiTyp() {
		return ciTyp;
	}
	/**
	 * 设置：ID_TYP
	 */
	public void setIdTyp(String idTyp) {
		this.idTyp = idTyp;
	}
	/**
	 * 获取：ID_TYP
	 */
	public String getIdTyp() {
		return idTyp;
	}
	/**
	 * 设置：ID_NO
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	/**
	 * 获取：ID_NO
	 */
	public String getIdNo() {
		return idNo;
	}
	/**
	 * 设置：LEGAL_NM
	 */
	public void setLegalNm(String legalNm) {
		this.legalNm = legalNm;
	}
	/**
	 * 获取：LEGAL_NM
	 */
	public String getLegalNm() {
		return legalNm;
	}
	/**
	 * 设置：LEGAL_TELE
	 */
	public void setLegalTele(String legalTele) {
		this.legalTele = legalTele;
	}
	/**
	 * 获取：LEGAL_TELE
	 */
	public String getLegalTele() {
		return legalTele;
	}
	/**
	 * 设置：LICENCE
	 */
	public void setLicence(String licence) {
		this.licence = licence;
	}
	/**
	 * 获取：LICENCE
	 */
	public String getLicence() {
		return licence;
	}
	/**
	 * 设置：ORG_CODE
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 获取：ORG_CODE
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * 设置：CONTACT_ADS
	 */
	public void setContactAds(String contactAds) {
		this.contactAds = contactAds;
	}
	/**
	 * 获取：CONTACT_ADS
	 */
	public String getContactAds() {
		return contactAds;
	}
	/**
	 * 设置：CONTACT_PER
	 */
	public void setContactPer(String contactPer) {
		this.contactPer = contactPer;
	}
	/**
	 * 获取：CONTACT_PER
	 */
	public String getContactPer() {
		return contactPer;
	}
	/**
	 * 设置：CONTACT_TELE
	 */
	public void setContactTele(String contactTele) {
		this.contactTele = contactTele;
	}
	/**
	 * 获取：CONTACT_TELE
	 */
	public String getContactTele() {
		return contactTele;
	}
	/**
	 * 设置：CONTACT_PHONE
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * 获取：CONTACT_PHONE
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * 设置：POST_CODE
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * 获取：POST_CODE
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * 设置：ADDRESS
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：ADDRESS
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：FAX
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 获取：FAX
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * 设置：EMAIL
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：EMAIL
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：WEB_URL
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	/**
	 * 获取：WEB_URL
	 */
	public String getWebUrl() {
		return webUrl;
	}
	/**
	 * 设置：STATUS
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：STATUS
	 */
	public String getStatus() {
		return status;
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
	 * 设置：TIME_STAMP
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：TIME_STAMP
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	public String getRiskLvl() {
		return riskLvl;
	}
	public void setRiskLvl(String riskLvl) {
		this.riskLvl = riskLvl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPayerTyp() {
		return payerTyp;
	}
	public void setPayerTyp(String payerTyp) {
		this.payerTyp = payerTyp;
	}
	public String getRegBankCode() {
		return regBankCode;
	}
	public void setRegBankCode(String regBankCode) {
		this.regBankCode = regBankCode;
	}
	public String getRegBankNo() {
		return regBankNo;
	}
	public void setRegBankNo(String regBankNo) {
		this.regBankNo = regBankNo;
	}
	public String getRegComAds() {
		return regComAds;
	}
	public void setRegComAds(String regComAds) {
		this.regComAds = regComAds;
	}
	public String getRegComTele() {
		return regComTele;
	}
	public void setRegComTele(String regComTele) {
		this.regComTele = regComTele;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	
	@Override
	public String toString() {
		return "CustomerInfoEntity [ciNo=" + ciNo + ", ciNm=" + ciNm
				+ ", ciTyp=" + ciTyp + ", idTyp=" + idTyp + ", idNo=" + idNo
				+ ", legalNm=" + legalNm + ", legalTele=" + legalTele
				+ ", taxNo=" + taxNo + ", licence=" + licence + ", orgCode="
				+ orgCode + ", contactAds=" + contactAds + ", contactPer="
				+ contactPer + ", contactTele=" + contactTele
				+ ", contactPhone=" + contactPhone + ", postCode=" + postCode
				+ ", address=" + address + ", fax=" + fax + ", email=" + email
				+ ", webUrl=" + webUrl + ", status=" + status + ", operId="
				+ operId + ", createTm=" + createTm + ", timeStamp="
				+ timeStamp + ", riskLvl=" + riskLvl + ", remark=" + remark
				+ ", payerTyp=" + payerTyp + ", regBankCode=" + regBankCode
				+ ", regBankNo=" + regBankNo + ", regComAds=" + regComAds
				+ ", regComTele=" + regComTele + ", organCode=" + organCode
				+ "]";
	}

	/**
	 * 校验必填信息是否为空
	 */
	public boolean validator() {
		if (StringUtils.isBlank(this.getCiNm())
				|| StringUtils.isBlank(this.getLegalNm())
				|| StringUtils.isBlank(this.getLegalTele())
				|| StringUtils.isBlank(this.getCiNo())
//				|| StringUtils.isBlank(this.getLicence())
				|| StringUtils.isBlank(this.getContactPer())
				|| StringUtils.isBlank(this.getContactTele())
				|| StringUtils.isBlank(this.getContactAds())
				|| StringUtils.isBlank(this.getPostCode())
//				|| StringUtils.isBlank(this.getTaxNo())
                || StringUtils.isBlank(this.getRiskLvl())) {

			return false;
		}

		return true;
	}
	

}
