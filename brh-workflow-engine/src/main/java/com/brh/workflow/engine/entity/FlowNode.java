package com.brh.workflow.engine.entity;

public class FlowNode {
	private String nodeId;
	private String nodeName;
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	@Override
	public String toString() {
		return "FlowNode [nodeId=" + nodeId + ", nodeName=" + nodeName + "]";
	}
	
}
