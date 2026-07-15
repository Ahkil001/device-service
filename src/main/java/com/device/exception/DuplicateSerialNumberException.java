package com.device.exception;

public class DuplicateSerialNumberException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateSerialNumberException(String message) {
        super(message);
    }

}