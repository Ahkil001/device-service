package com.device.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditRequest {

	private String eventType;

	private UUID deviceId;

	private UUID employeeId;

	private UUID performedBy;

}