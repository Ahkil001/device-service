package com.device.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponse {

	private UUID id;

	private String serialNumber;

	private String deviceName;

	private String deviceType;

	private String brand;

	private String model;

	private LocalDateTime createdDate;

}