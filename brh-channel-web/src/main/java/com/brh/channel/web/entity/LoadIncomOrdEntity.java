package com.brh.channel.web.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.brh.channel.common.enums.CurStep;
import com.brh.workflow.engine.entity.WorkflowEntity;

public class LoadIncomOrdEntity  extends WorkflowEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String incomNo;

    private String ciNo;

    private String prodNo;

    private String prodNm;

    private String contractNo;

    private BigDecimal loadAmt;

    private String repayWay;

    private BigDecimal loadRate;

    private String loadTm1;
    
    private String loadCycle1;

    private String insurNo;

    private String lendNo;

    private BigDecimal creditAmt;

    private BigDecimal insurAmt;

    private String loadTm2;
    
    private String loadCycle2;

    private String procInstId;

    private String curStep;

    private String status;

    private String startTm;

    private String endTm;

    private String operId;

    private String createTm;

    private String timestamp;

    private String ciNm;

    private String ciTyp;
    //新增工作流视图查询字段
    private String taskId;
    private String procInstIdIn;//工作流视图内的实例id
    private String actId;
    private String actName;
    private String assignee;
    private String delegationId;
    private String description;
    private String businessKeyIn;
    private String createTime;
    private String dueDate;
    private String candidate;
    
    private String commentStr;//批注字段

    private CustomerInfoEntity customerInfo;
    private ProdInfoEntity prodInfo;
    private String operName;
    private String applyTime;
    
    //优化新增
    private String agencyId;//机构id
    private String agencyNm;//机构名称
    private BigDecimal insurFee;//保费
    private String remark;//备注

    private ContractInfoEntity  contractInfo;
    
    //二次进件-客户经理
    private String accountManager;
    
    //二次进件-投保人贷款卡号
    private String loanCardNo;
    
    //二次进件-保险开始日期
    private String insureBeginTm;
    
    //二次进件-保险终止日期
    private String insureEndTm;

    //二次进件-保险年费率 
    private BigDecimal insureFeeRate;
    
    //二次进件-保险业务品种 
    private String insureProd;
    //二次进件-保险产品名称
    private String insureProdNm;
    
    //二次进件-绝对免赔率 
    private BigDecimal absExcess;
    
    //二次进件-保费缴纳类型
    private String insurePayTyp;

    //二次进件-出单机构 
    private String outAgencyId;
    
    //二次进件-特别约定 
    private String specialAgr;
    
    //二次进件-核保人
    private String underwriteUser;
    
    //二次进件-核保时间
    private String underwriteTm;

    //二次进件保费缴费状态 
    private String paySts;

    //二次进件出单状态 
    private String issueSts;

    //二次进件实缴保费缴费金额
    private BigDecimal payInsureAmt;

    //二次进件实缴保费缴纳日期
    private String payInsureTm;
    
    //二次进件-出单客户经理
    private String insureAccountMgr;
    
    //二次进件-出单客户经理工号
    private String insureAccountMgrno;
    
    //二次进件-出单文件上传状态
    private String insureFileSts;
    //二次进件保险录入-争议处理  0001：诉讼 0002：仲裁
    private String argueDeal;
    public String getTaskId() {
		return taskId;
	}
    
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstIdIn() {
		return procInstIdIn;
	}

	public void setProcInstIdIn(String procInstIdIn) {
		this.procInstIdIn = procInstIdIn;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(String delegationId) {
		this.delegationId = delegationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getCommentStr() {
		return commentStr;
	}

	public void setCommentStr(String commentStr) {
		this.commentStr = commentStr;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getBusinessKeyIn() {
		return businessKeyIn;
	}

	public void setBusinessKeyIn(String businessKeyIn) {
		this.businessKeyIn = businessKeyIn;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public String getIncomNo() {
        return incomNo;
    }

    public void setIncomNo(String incomNo) {
        this.incomNo = incomNo;
    }

    public String getCiNo() {
        return ciNo;
    }

    public void setCiNo(String ciNo) {
        this.ciNo = ciNo;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getLoadAmt() {
        return loadAmt;
    }

    public void setLoadAmt(BigDecimal loadAmt) {
        this.loadAmt = loadAmt;
    }

    public String getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(String repayWay) {
        this.repayWay = repayWay;
    }

    public BigDecimal getLoadRate() {
        return loadRate;
    }

    public void setLoadRate(BigDecimal loadRate) {
        this.loadRate = loadRate;
    }

    public String getLoadTm1() {
        return loadTm1;
    }

    public void setLoadTm1(String loadTm1) {
        this.loadTm1 = loadTm1;
    }

    public String getInsurNo() {
        return insurNo;
    }

    public void setInsurNo(String insurNo) {
        this.insurNo = insurNo;
    }

    public String getLendNo() {
        return lendNo;
    }

    public void setLendNo(String lendNo) {
        this.lendNo = lendNo;
    }

    public BigDecimal getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(BigDecimal creditAmt) {
        this.creditAmt = creditAmt;
    }

    public BigDecimal getInsurAmt() {
        return insurAmt;
    }

    public void setInsurAmt(BigDecimal insurAmt) {
        this.insurAmt = insurAmt;
    }

    public String getLoadTm2() {
        return loadTm2;
    }

    public void setLoadTm2(String loadTm2) {
        this.loadTm2 = loadTm2;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getCurStep() {
        return curStep;
    }

    public void setCurStep(String curStep) {
        this.curStep = curStep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTm() {
        return startTm;
    }

    public void setStartTm(String startTm) {
        this.startTm = startTm;
    }

    public String getEndTm() {
        return endTm;
    }

    public void setEndTm(String endTm) {
        this.endTm = endTm;
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

    public String getCiNm() {
        return ciNm;
    }

    public void setCiNm(String ciNm) {
        this.ciNm = ciNm;
    }

    public String getCiTyp() {
        return ciTyp;
    }

    public void setCiTyp(String ciTyp) {
        this.ciTyp = ciTyp;
    }

    public CustomerInfoEntity getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoEntity customerInfo) {
        this.customerInfo = customerInfo;
    }

    public ProdInfoEntity getProdInfo() {
        return prodInfo;
    }

    public void setProdInfo(ProdInfoEntity prodInfo) {
        this.prodInfo = prodInfo;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getApplyTime() { 
            return applyTime;
    }

    public String getLoadCycle1() {
		return loadCycle1;
	}

	public void setLoadCycle1(String loadCycle1) {
		this.loadCycle1 = loadCycle1;
	}

	public String getLoadCycle2() {
		return loadCycle2;
	}

	public void setLoadCycle2(String loadCycle2) {
		this.loadCycle2 = loadCycle2;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyNm() {
		return agencyNm;
	}

	public void setAgencyNm(String agencyNm) {
		this.agencyNm = agencyNm;
	}
	public BigDecimal getInsurFee() {
		return insurFee;
	}

	public void setInsurFee(BigDecimal insurFee) {
		this.insurFee = insurFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public ContractInfoEntity getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfoEntity contractInfo) {
        this.contractInfo = contractInfo;
    }
    
	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public String getLoanCardNo() {
		return loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
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

	public BigDecimal getInsureFeeRate() {
		return insureFeeRate;
	}

	public void setInsureFeeRate(BigDecimal insureFeeRate) {
		this.insureFeeRate = insureFeeRate;
	}

	public String getInsureProd() {
		return insureProd;
	}

	public void setInsureProd(String insureProd) {
		this.insureProd = insureProd;
	}

	public BigDecimal getAbsExcess() {
		return absExcess;
	}

	public void setAbsExcess(BigDecimal absExcess) {
		this.absExcess = absExcess;
	}

	public String getInsurePayTyp() {
		return insurePayTyp;
	}

	public void setInsurePayTyp(String insurePayTyp) {
		this.insurePayTyp = insurePayTyp;
	}

	public String getOutAgencyId() {
		return outAgencyId;
	}

	public void setOutAgencyId(String outAgencyId) {
		this.outAgencyId = outAgencyId;
	}

	public String getSpecialAgr() {
		return specialAgr;
	}

	public void setSpecialAgr(String specialAgr) {
		this.specialAgr = specialAgr;
	}

	public String getUnderwriteUser() {
		return underwriteUser;
	}

	public void setUnderwriteUser(String underwriteUser) {
		this.underwriteUser = underwriteUser;
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

	public String getIssueSts() {
		return issueSts;
	}

	public void setIssueSts(String issueSts) {
		this.issueSts = issueSts;
	}

	public BigDecimal getPayInsureAmt() {
		return payInsureAmt;
	}

	public void setPayInsureAmt(BigDecimal payInsureAmt) {
		this.payInsureAmt = payInsureAmt;
	}

	public String getPayInsureTm() {
		return payInsureTm;
	}

	public void setPayInsureTm(String payInsureTm) {
		this.payInsureTm = payInsureTm;
	}

	public String getInsureAccountMgr() {
		return insureAccountMgr;
	}

	public void setInsureAccountMgr(String insureAccountMgr) {
		this.insureAccountMgr = insureAccountMgr;
	}

	public String getInsureAccountMgrno() {
		return insureAccountMgrno;
	}

	public void setInsureAccountMgrno(String insureAccountMgrno) {
		this.insureAccountMgrno = insureAccountMgrno;
	}

	public String getInsureFileSts() {
		return insureFileSts;
	}

	public void setInsureFileSts(String insureFileSts) {
		this.insureFileSts = insureFileSts;
	}

	public String getInsureProdNm() {
		return insureProdNm;
	}

	public void setInsureProdNm(String insureProdNm) {
		this.insureProdNm = insureProdNm;
	}

	public String getArgueDeal() {
		return argueDeal;
	}

	public void setArgueDeal(String argueDeal) {
		this.argueDeal = argueDeal;
	}

	@Override
	public String toString() {
		return "LoadIncomOrdEntity [incomNo=" + incomNo + ", ciNo=" + ciNo
				+ ", prodNo=" + prodNo + ", prodNm=" + prodNm + ", contractNo="
				+ contractNo + ", loadAmt=" + loadAmt + ", repayWay="
				+ repayWay + ", loadRate=" + loadRate + ", loadTm1=" + loadTm1
				+ ", loadCycle1=" + loadCycle1 + ", insurNo=" + insurNo
				+ ", lendNo=" + lendNo + ", creditAmt=" + creditAmt
				+ ", insurAmt=" + insurAmt + ", loadTm2=" + loadTm2
				+ ", loadCycle2=" + loadCycle2 + ", procInstId=" + procInstId
				+ ", curStep=" + curStep + ", status=" + status + ", startTm="
				+ startTm + ", endTm=" + endTm + ", operId=" + operId
				+ ", createTm=" + createTm + ", timestamp=" + timestamp
				+ ", ciNm=" + ciNm + ", ciTyp=" + ciTyp + ", taskId=" + taskId
				+ ", procInstIdIn=" + procInstIdIn + ", actId=" + actId
				+ ", actName=" + actName + ", assignee=" + assignee
				+ ", delegationId=" + delegationId + ", description="
				+ description + ", businessKeyIn=" + businessKeyIn
				+ ", createTime=" + createTime + ", dueDate=" + dueDate
				+ ", candidate=" + candidate + ", commentStr=" + commentStr
				+ ", customerInfo=" + customerInfo + ", prodInfo=" + prodInfo
				+ ", operName=" + operName + ", applyTime=" + applyTime
				+ ", agencyId=" + agencyId + ", agencyNm=" + agencyNm
				+ ", insurFee=" + insurFee + ", remark=" + remark
				+ ", contractInfo=" + contractInfo + ", accountManager="
				+ accountManager + ", loanCardNo=" + loanCardNo
				+ ", insureBeginTm=" + insureBeginTm + ", insureEndTm="
				+ insureEndTm + ", insureFeeRate=" + insureFeeRate
				+ ", insureProd=" + insureProd + ", insureProdNm="
				+ insureProdNm + ", absExcess=" + absExcess + ", insurePayTyp="
				+ insurePayTyp + ", outAgencyId=" + outAgencyId
				+ ", specialAgr=" + specialAgr + ", underwriteUser="
				+ underwriteUser + ", underwriteTm=" + underwriteTm
				+ ", paySts=" + paySts + ", issueSts=" + issueSts
				+ ", payInsureAmt=" + payInsureAmt + ", payInsureTm="
				+ payInsureTm + ", insureAccountMgr=" + insureAccountMgr
				+ ", insureAccountMgrno=" + insureAccountMgrno
				+ ", insureFileSts=" + insureFileSts + ", argueDeal="
				+ argueDeal + "]";
	}

	/**
     * 校验必填信息是否为空
     */
    public boolean validator() {

        /*
        if (!LoadStatus.ENTER.getCode().equals(getStatus())) {
            return true;
        }
        */

        if (StringUtils.isBlank(this.getCiNo())
                || StringUtils.isBlank(this.getCiNm())
                || StringUtils.isBlank(this.getCiTyp())) {
            return false;
        }

        if (StringUtils.isBlank(this.getAgencyId())
                || StringUtils.isBlank(this.getProdNo())
                || this.getLoadAmt() == null) {
            return false;
        }

        String cusStep = this.getCurStep();
        if (CurStep.SECOND.getCode().equals(cusStep)) {
            if (StringUtils.isBlank(this.getLendNo())
                    || this.getCreditAmt() == null
                    || StringUtils.isBlank(this.getLoadCycle2())
                    || StringUtils.isBlank(this.getLoadTm2())) {
                return false;
            }
        }

        // StringUtils.isBlank(this.getContractNo())


        return true;
    }
    
}