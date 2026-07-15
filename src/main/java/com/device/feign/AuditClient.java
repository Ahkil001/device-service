package com.device.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.device.dto.AuditRequest;

@FeignClient(name = "audit-service", url = "${audit-service.url}")
public interface AuditClient {

	@PostMapping("/internal/audit")
	void saveAudit(AuditRequest request);

}