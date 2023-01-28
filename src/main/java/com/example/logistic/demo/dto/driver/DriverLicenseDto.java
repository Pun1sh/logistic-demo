package com.example.logistic.demo.dto.driver;


import com.example.logistic.demo.model.LicenseType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverLicenseDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LicenseType licenseType;
}
