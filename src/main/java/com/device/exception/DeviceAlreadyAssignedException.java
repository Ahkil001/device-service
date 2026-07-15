package com.device.exception;

public class DeviceAlreadyAssignedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeviceAlreadyAssignedException(String message) {
        super(message);
    }

}