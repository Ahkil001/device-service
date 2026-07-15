package com.device.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.device.dto.CreateDeviceRequest;
import com.device.dto.DeviceAssignmentResponse;
import com.device.dto.DeviceResponse;
import com.device.dto.UpdateDeviceRequest;
import com.device.service.DeviceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Validated
public class DeviceController {

	private final DeviceService deviceService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody CreateDeviceRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(request));
	}

	@PutMapping("/{deviceId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DeviceResponse> updateDevice(@PathVariable UUID deviceId,
			@Valid @RequestBody UpdateDeviceRequest request) {

		return ResponseEntity.ok(deviceService.updateDevice(deviceId, request));
	}

	@GetMapping("/{deviceId}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
	public ResponseEntity<DeviceResponse> getDevice(@PathVariable UUID deviceId) {

		return ResponseEntity.ok(deviceService.getDevice(deviceId));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DeviceResponse>> getAllDevices() {

		return ResponseEntity.ok(deviceService.getAllDevices());
	}

	@GetMapping("/available")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DeviceResponse>> getAvailableDevices() {

		return ResponseEntity.ok(deviceService.getAvailableDevices());
	}

	@GetMapping("/assigned")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DeviceResponse>> getAssignedDevices() {

		return ResponseEntity.ok(deviceService.getAssignedDevices());
	}

	@PostMapping("/{deviceId}/assign/{employeeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DeviceAssignmentResponse> assignDevice(@PathVariable UUID deviceId,
			@PathVariable UUID employeeId) {

		return ResponseEntity.ok(deviceService.assignDevice(deviceId, employeeId));
	}

	@PostMapping("/{deviceId}/return")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> returnDevice(@PathVariable UUID deviceId) {

		deviceService.returnDevice(deviceId);

		return ResponseEntity.noContent().build();
	}
}