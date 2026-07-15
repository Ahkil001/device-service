package com.device.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.device.entity.DeviceAssignment;

public interface DeviceAssignmentRepository extends JpaRepository<DeviceAssignment, UUID> {

    Optional<DeviceAssignment> findByDeviceIdAndReturnedDateIsNull(UUID deviceId);

    List<DeviceAssignment> findByReturnedDateIsNull();

    List<DeviceAssignment> findByEmployeeIdAndReturnedDateIsNull(UUID employeeId);

}