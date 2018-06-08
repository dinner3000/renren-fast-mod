package com.brh.channel.web.entity;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import java.math.BigDecimal;

public class ContractInfoEntity {
    private String contractId;

    private String contractNo;
    
    private String incomNo;

    private String contractNm;

    private BigDecimal serviceFee1;

    private BigDecimal serviceFee2;

    private BigDecimal insureFee;

    private String repayDate;

    private String valueDate;

    private String expiryDate;

    private String operId;

    private String createTm;

    private String timestamp;

    private String remark;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNm() {
        return contractNm;
    }

    public void setContractNm(String contractNm) {
        this.contractNm = contractNm;
    }

    public BigDecimal getServiceFee1() {
        return serviceFee1;
    }

    public void setServiceFee1(BigDecimal serviceFee1) {
        this.serviceFee1 = serviceFee1;
    }

    public BigDecimal getServiceFee2() {
        return serviceFee2;
    }

    public void setServiceFee2(BigDecimal serviceFee2) {
        this.serviceFee2 = serviceFee2;
    }

    public BigDecimal getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(BigDecimal insureFee) {
        this.insureFee = insureFee;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
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

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getCreateTm() {
        return createTm;
    }

    public void setCreateTm(String createTm) {
        this.createTm = createTm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getIncomNo() {
		return incomNo;
	}

	public void setIncomNo(String incomNo) {
		this.incomNo = incomNo;
	}

	@Override
	public String toString() {
		return "ContractInfoEntity [contractId=" + contractId + ", contractNo="
				+ contractNo + ", incomNo=" + incomNo + ", contractNm="
				+ contractNm + ", serviceFee1=" + serviceFee1
				+ ", serviceFee2=" + serviceFee2 + ", insureFee=" + insureFee
				+ ", repayDate=" + repayDate + ", valueDate=" + valueDate
				+ ", expiryDate=" + expiryDate + ", operId=" + operId
				+ ", createTm=" + createTm + ", timestamp=" + timestamp
				+ ", remark=" + remark + "]";
	}

    /**
     * 校验必填信息是否为空
     */
    public boolean validator() {
        if(StringUtils.isBlank(this.getContractNo())
                || StringUtils.isBlank(this.getIncomNo())
                || this.getServiceFee1() == null
                || this.getServiceFee2() == null
                || this.getInsureFee() == null
                || StringUtils.isBlank(this.getRepayDate())
                || StringUtils.isBlank(this.getValueDate())
                || StringUtils.isBlank(this.getExpiryDate())
                ) {
            return false;
        }

        return true;
    }
}