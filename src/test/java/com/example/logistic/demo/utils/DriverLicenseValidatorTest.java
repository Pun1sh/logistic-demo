package com.example.logistic.demo.utils;

import com.example.logistic.demo.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DriverLicenseValidatorTest {

    @Autowired
    DriverLicenseValidator driverLicenseValidator;

    private static final PodamFactory factory = new PodamFactoryImpl();

    @Test
    @DisplayName("Should throw exception if driver already has 3 cars")
    void validateCarAmount() {
        Car car = factory.manufacturePojo(Car.class);
        Car car2 = factory.manufacturePojo(Car.class);
        Car car3 = factory.manufacturePojo(Car.class);
        Driver driver = new Driver();
        driver.setCars(List.of(car, car2, car3));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> driverLicenseValidator.validateLicense(driver, CarType.EASY));
        assertTrue(illegalStateException.getMessage().contains("already has 3 cars"));
    }

    @Test
    @DisplayName("Should throw exception if driver doesn't have non-expired license")
    void validateExpiration() {
        Driver driver = new Driver();
        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setStartDate(LocalDate.now().plusDays(4));
        driverLicense.setEndDate(LocalDate.now().plusDays(360));
        DriverLicense driverLicense2 = new DriverLicense();
        driverLicense2.setStartDate(LocalDate.now().minusDays(4));
        driverLicense2.setEndDate(LocalDate.now().minusDays(2));
        driver.setDriverLicenses(List.of(driverLicense, driverLicense2));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> driverLicenseValidator.validateLicense(driver, CarType.EASY));
        assertTrue(illegalStateException.getMessage().contains("doesn't have non-expired licences"));
    }

    @Test
    @DisplayName("Should throw exception if driver doesn't have needed license type")
    void validateLicenseType() {
        Driver driver = new Driver();
        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setStartDate(LocalDate.now().minusDays(4));
        driverLicense.setEndDate(LocalDate.now().plusDays(360));
        driverLicense.setLicenseType(LicenseType.B);
        driver.setDriverLicenses(List.of(driverLicense));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> driverLicenseValidator.validateLicense(driver, CarType.HEAVY));
        assertTrue(illegalStateException.getMessage().contains("doesn't have right to drive car of type:" + CarType.HEAVY.name()));
    }
}