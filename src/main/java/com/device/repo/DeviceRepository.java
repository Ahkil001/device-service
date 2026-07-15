package com.device.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.device.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    boolean existsBySerialNumber(String serialNumber);

    Optional<Device> findBySerialNumber(String serialNumber);

}