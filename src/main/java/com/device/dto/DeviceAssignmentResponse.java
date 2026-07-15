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
public class DeviceAssignmentResponse {

	private UUID deviceId;

	private UUID employeeId;

	private LocalDateTime assignedDate;

}