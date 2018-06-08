package com.brh.workflow.engine.entity;

import java.io.Serializable;

/**
 * 
 * @author lcl
 *	工作流引擎用户组实体
 */
public class WorkflowGroup implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;//id
    private Integer rev;//版本 可空
    private String name;//用户组描述
    private String type;//用户组类型
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRev() {
		return rev;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "WorkflowGroup [id=" + id + ", rev=" + rev + ", name=" + name
				+ ", type=" + type + "]";
	}
    
}
