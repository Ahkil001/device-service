package com.device.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.device.dto.AuditRequest;
import com.device.dto.CreateDeviceRequest;
import com.device.dto.DeviceAssignmentResponse;
import com.device.dto.DeviceResponse;
import com.device.dto.EmployeeResponse;
import com.device.dto.UpdateDeviceRequest;
import com.device.entity.Device;
import com.device.entity.DeviceAssignment;
import com.device.exception.DeviceAlreadyAssignedException;
import com.device.exception.DeviceNotFoundException;
import com.device.exception.DuplicateSerialNumberException;
import com.device.exception.InactiveEmployeeException;
import com.device.feign.AuditClient;
import com.device.feign.EmployeeClient;
import com.device.repo.DeviceAssignmentRepository;
import com.device.repo.DeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

	private final DeviceRepository deviceRepository;
	private final DeviceAssignmentRepository assignmentRepository;
	private final EmployeeClient employeeClient;
	private final AuditClient auditClient;

	@Override
	public DeviceResponse createDevice(CreateDeviceRequest request) {

		if (deviceRepository.existsBySerialNumber(request.getSerialNumber())) {
			throw new DuplicateSerialNumberException("Serial number already exists.");
		}

		Device device = Device.builder().id(UUID.randomUUID()).serialNumber(request.getSerialNumber())
				.deviceName(request.getDeviceName()).deviceType(request.getDeviceType()).brand(request.getBrand())
				.model(request.getModel()).createdDate(LocalDateTime.now()).build();

		deviceRepository.save(device);

		return mapToResponse(device);
	}

	@Override
	public DeviceResponse updateDevice(UUID deviceId, UpdateDeviceRequest request) {

		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found."));

		device.setDeviceName(request.getDeviceName());
		device.setDeviceType(request.getDeviceType());
		device.setBrand(request.getBrand());
		device.setModel(request.getModel());
		device.setUpdatedDate(LocalDateTime.now());

		deviceRepository.save(device);

		return mapToResponse(device);
	}

	@Override
	public DeviceResponse getDevice(UUID deviceId) {

		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found."));

		return mapToResponse(device);
	}

	@Override
	public List<DeviceResponse> getAllDevices() {

		return deviceRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public List<DeviceResponse> getAvailableDevices() {

		List<Device> devices = deviceRepository.findAll();

		return devices.stream()
				.filter(device -> assignmentRepository.findByDeviceIdAndReturnedDateIsNull(device.getId()).isEmpty())
				.map(this::mapToResponse).toList();
	}

	@Override
	public List<DeviceResponse> getAssignedDevices() {

		return assignmentRepository.findByReturnedDateIsNull().stream()
				.map(a -> deviceRepository.findById(a.getDeviceId()).orElse(null)).filter(Objects::nonNull)
				.map(this::mapToResponse).toList();
	}

	@Override
	public DeviceAssignmentResponse assignDevice(UUID deviceId, UUID employeeId) {

		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device not found."));

		if (assignmentRepository.findByDeviceIdAndReturnedDateIsNull(deviceId).isPresent()) {
			throw new DeviceAlreadyAssignedException("Device already assigned.");
		}

		EmployeeResponse employee = employeeClient.getEmployee(employeeId);

		if (!employee.isActive()) {
			throw new InactiveEmployeeException("Employee is inactive.");
		}

		DeviceAssignment assignment = DeviceAssignment.builder().id(UUID.randomUUID()).deviceId(deviceId)
				.employeeId(employeeId).assignedDate(LocalDateTime.now()).build();

		assignmentRepository.save(assignment);

		auditClient.saveAudit(
				AuditRequest.builder().eventType("DEVICE_ASSIGNED").deviceId(deviceId).employeeId(employeeId).build());

		return DeviceAssignmentResponse.builder().deviceId(deviceId).employeeId(employeeId)
				.assignedDate(assignment.getAssignedDate()).build();
	}

	@Override
	public void returnDevice(UUID deviceId) {

		DeviceAssignment assignment = assignmentRepository.findByDeviceIdAndReturnedDateIsNull(deviceId)
				.orElseThrow(() -> new DeviceNotFoundException("Device is not assigned."));

		assignment.setReturnedDate(LocalDateTime.now());

		assignmentRepository.save(assignment);

		auditClient.saveAudit(AuditRequest.builder().eventType("DEVICE_RETURNED").deviceId(deviceId)
				.employeeId(assignment.getEmployeeId()).build());
	}

	private DeviceResponse mapToResponse(Device device) {

		return DeviceResponse.builder().id(device.getId()).serialNumber(device.getSerialNumber())
				.deviceName(device.getDeviceName()).deviceType(device.getDeviceType()).brand(device.getBrand())
				.model(device.getModel()).createdDate(device.getCreatedDate()).build();
	}

}