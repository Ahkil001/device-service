package com.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeviceRequest {

	private String serialNumber;
	private String deviceName;
	private String deviceType;
	private String brand;
	private String model;

}