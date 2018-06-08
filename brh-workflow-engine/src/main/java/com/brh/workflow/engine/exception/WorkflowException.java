package com.brh.workflow.engine.exception;

public class WorkflowException extends Exception {

	/**
	 * 工作流异常类
	 */
	private static final long serialVersionUID = 1L;

	public WorkflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowException(String message) {
		super(message);
	}
}
