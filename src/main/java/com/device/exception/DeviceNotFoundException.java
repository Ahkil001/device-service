package com.device.exception;

public class DeviceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException(String message) {
		super(message);
	}

}