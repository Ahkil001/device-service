package com.device.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.device.dto.EmployeeResponse;

@FeignClient(name = "employee-service", url = "${employee-service.url}")
public interface EmployeeClient {

	@GetMapping("/internal/employees/{id}")
	EmployeeResponse getEmployee(@PathVariable UUID id);

}