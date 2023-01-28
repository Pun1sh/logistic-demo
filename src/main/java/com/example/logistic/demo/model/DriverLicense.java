package com.example.logistic.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "driver_license")
@Getter
@Setter
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "license_type")
    private LicenseType licenseType;
}