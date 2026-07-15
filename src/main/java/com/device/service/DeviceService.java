package com.device.service;

import java.util.List;
import java.util.UUID;

import com.device.dto.CreateDeviceRequest;
import com.device.dto.DeviceAssignmentResponse;
import com.device.dto.DeviceResponse;
import com.device.dto.UpdateDeviceRequest;

public interface DeviceService {

	DeviceResponse createDevice(CreateDeviceRequest request);

	DeviceResponse updateDevice(UUID deviceId, UpdateDeviceRequest request);

	DeviceResponse getDevice(UUID deviceId);

	List<DeviceResponse> getAllDevices();

	List<DeviceResponse> getAvailableDevices();

	List<DeviceResponse> getAssignedDevices();

	DeviceAssignmentResponse assignDevice(UUID deviceId, UUID employeeId);

	void returnDevice(UUID deviceId);

}