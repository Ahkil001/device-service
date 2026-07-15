package com.device.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_assignments")
public class DeviceAssignment {

    @Id
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "assigned_by")
    private UUID assignedBy;

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;
}