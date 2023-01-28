package com.example.logistic.demo.utils;

import com.example.logistic.demo.model.CarType;
import com.example.logistic.demo.model.Driver;
import com.example.logistic.demo.model.DriverLicense;
import com.example.logistic.demo.model.LicenseType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverLicenseValidator {

    private static final Map<CarType, LicenseType> map;

    static {
        Map<CarType, LicenseType> aMap = new HashMap<>();
        aMap.put(CarType.EASY, LicenseType.B);
        aMap.put(CarType.HEAVY, LicenseType.C);
        aMap.put(CarType.BUS, LicenseType.D);
        map = Collections.unmodifiableMap(aMap);
    }

    public void validateLicense(Driver driver, CarType carType) {
        checkCarsAmount(driver);
        checkLicenseType(driver, carType, getTimeValidLicences(driver));
    }

    private void checkLicenseType(Driver driver, CarType carType, List<DriverLicense> validLicences) {
        List<LicenseType> licences = validLicences.stream()
                .map(DriverLicense::getLicenseType)
                .toList();
        if (!licences.contains(map.get(carType))) {
            throw new IllegalStateException("Driver with id=" + driver.getId() +
                    " doesn't have right to drive car of type:" + carType.name());
        }
    }

    private List<DriverLicense> getTimeValidLicences(Driver driver) {
        List<DriverLicense> validLicences = driver.getDriverLicenses().stream().filter(this::ifLicenseRangeIsValid).toList();
        if (validLicences.isEmpty()) {
            throw new IllegalStateException("Driver with id=" + driver.getId() +
                    " doesn't have non-expired licences");
        }
        return validLicences;
    }

    private void checkCarsAmount(Driver driver) {
        if (driver.getCars() != null && driver.getCars().size() == 3) {
            throw new IllegalStateException("Driver with id=" + driver.getId() + " already has 3 cars, which is max");
        }
    }

    private boolean ifLicenseRangeIsValid(DriverLicense driverLicense) {
        LocalDate startDate = driverLicense.getStartDate();
        LocalDate endDate = driverLicense.getEndDate();
        LocalDate now = LocalDate.now();
        return startDate.isBefore(now) && endDate.isAfter(now);
    }
}
