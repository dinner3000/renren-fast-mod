package com.brh.workflow.engine.entity;

import java.io.Serializable;
/**
 * 
 * @author lcl
 * 工作流引擎用户实体
 */
public class WorkflowUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//id
	private String email;//邮箱
	private String first;//名称
	private String last;//姓氏
	private String password;//密码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "WorkflowUser [id=" + id + ", email=" + email + ", first="
				+ first + ", last=" + last + ", password=" + password + "]";
	}

}
