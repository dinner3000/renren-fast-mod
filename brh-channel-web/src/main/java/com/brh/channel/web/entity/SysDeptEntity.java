package com.brh.channel.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * SYS_DEPT
 * 
 * @date 2017-08-10 16:30:59
 */
public class SysDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//部门id
	private BigDecimal deptId;
	//上级部门名称
	private BigDecimal parentId;
	//部门名称
	private String name;
	//排序
	private BigDecimal orderNum;
	//DEL_FLAG
	private BigDecimal delFlag;
	/**
	 * 父菜单名称
	 */
	private String parentName;
	/**
	 * 合作机构ID
	 */
	private String agencyId;
	/**
	 * 合作机构名称
	 */
	private String agencyNm;
	/**
	 * 合作机构
	 */
	private AgencyInfoEntity agencyInfo;
	
	/**
	 * ztree属性
	 */
	private Boolean open;

	public BigDecimal getDeptId() {
		return deptId;
	}

	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}

	public BigDecimal getParentId() {
		return parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigDecimal orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(BigDecimal delFlag) {
		this.delFlag = delFlag;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public AgencyInfoEntity getAgencyInfo() {
		return agencyInfo;
	}

	public void setAgencyInfo(AgencyInfoEntity agencyInfo) {
		this.agencyInfo = agencyInfo;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}
	
	
	
	
}
