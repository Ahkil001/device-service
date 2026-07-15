package com.device.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DeviceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleDeviceNotFound(DeviceNotFoundException ex, HttpServletRequest request) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(DeviceAlreadyAssignedException.class)
	public ResponseEntity<ErrorResponse> handleAssigned(DeviceAlreadyAssignedException ex, HttpServletRequest request) {

		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(DuplicateSerialNumberException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateSerialNumberException ex,
			HttpServletRequest request) {

		return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(InactiveEmployeeException.class)
	public ResponseEntity<ErrorResponse> handleInactive(InactiveEmployeeException ex, HttpServletRequest request) {

		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path) {

		return ResponseEntity.status(status).body(ErrorResponse.builder().timestamp(LocalDateTime.now())
				.status(status.value()).error(status.getReasonPhrase()).message(message).path(path).build());
	}

	@Getter
	@Builder
	static class ErrorResponse {

		private LocalDateTime timestamp;
		private int status;
		private String error;
		private String message;
		private String path;
	}
}