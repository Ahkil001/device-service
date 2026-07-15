package com.device.exception;

public class InactiveEmployeeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InactiveEmployeeException(String message) {
		super(message);
	}

}