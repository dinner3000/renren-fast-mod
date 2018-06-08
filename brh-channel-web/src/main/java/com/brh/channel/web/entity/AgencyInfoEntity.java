package com.brh.channel.web.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * AGENCY_INFO
 * 
 * @date 2017-08-08 14:44:47
 */
public class AgencyInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	  private String agencyId;

	    private String agencyNm;
	    private String licence;//营业执照
	    private String idTyp;//证件类型
	    private String idNo;//证件号码
	    private String legalNm;//法人姓名
	    private String cooTyp;

	    private String address;

	    private String cooPer;

	    private String cooTele;

	    private String cooPhone;

	    private String postCode;

	    private String fax;

	    private String email;

	    private String webUrl;

	    private String operId;

	    private String createTm;

	    private String timeStamp;
	    private String status;
	    private String remark;
	    
	    private String orgCode;  //统一社会信用编码
	    
	    private String organCode; //组织机构代码
	    
	    private String taxNo;  //纳税人识别号

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

	    public String getCooTyp() {
	        return cooTyp;
	    }

	    public void setCooTyp(String cooTyp) {
	        this.cooTyp = cooTyp;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getCooPer() {
	        return cooPer;
	    }

	    public void setCooPer(String cooPer) {
	        this.cooPer = cooPer;
	    }

	    public String getCooTele() {
	        return cooTele;
	    }

	    public void setCooTele(String cooTele) {
	        this.cooTele = cooTele;
	    }

	    public String getCooPhone() {
	        return cooPhone;
	    }

	    public void setCooPhone(String cooPhone) {
	        this.cooPhone = cooPhone;
	    }

	    public String getPostCode() {
	        return postCode;
	    }

	    public void setPostCode(String postCode) {
	        this.postCode = postCode;
	    }

	    public String getFax() {
	        return fax;
	    }

	    public void setFax(String fax) {
	        this.fax = fax;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getWebUrl() {
	        return webUrl;
	    }

	    public void setWebUrl(String webUrl) {
	        this.webUrl = webUrl;
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

	    public String getTimeStamp() {
	        return timeStamp;
	    }

	    public void setTimeStamp(String timeStamp) {
	        this.timeStamp = timeStamp;
	    }

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getLicence() {
			return licence;
		}

		public void setLicence(String licence) {
			this.licence = licence;
		}

		public String getIdTyp() {
			return idTyp;
		}

		public void setIdTyp(String idTyp) {
			this.idTyp = idTyp;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}

		public String getLegalNm() {
			return legalNm;
		}

		public void setLegalNm(String legalNm) {
			this.legalNm = legalNm;
		}

		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getOrgCode() {
			return orgCode;
		}

		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		public String getOrganCode() {
			return organCode;
		}

		public void setOrganCode(String organCode) {
			this.organCode = organCode;
		}

		public String getTaxNo() {
			return taxNo;
		}

		public void setTaxNo(String taxNo) {
			this.taxNo = taxNo;
		}

		@Override
		public String toString() {
			return "AgencyInfoEntity [agencyId=" + agencyId + ", agencyNm="
					+ agencyNm + ", licence=" + licence + ", idTyp=" + idTyp
					+ ", idNo=" + idNo + ", legalNm=" + legalNm + ", cooTyp="
					+ cooTyp + ", address=" + address + ", cooPer=" + cooPer
					+ ", cooTele=" + cooTele + ", cooPhone=" + cooPhone
					+ ", postCode=" + postCode + ", fax=" + fax + ", email="
					+ email + ", webUrl=" + webUrl + ", operId=" + operId
					+ ", createTm=" + createTm + ", timeStamp=" + timeStamp
					+ ", status=" + status + ", remark=" + remark
					+ ", orgCode=" + orgCode + ", organCode=" + organCode
					+ ", taxNo=" + taxNo + "]";
		}

		

	

		
	    
}
