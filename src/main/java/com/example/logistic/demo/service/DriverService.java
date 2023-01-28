package com.example.logistic.demo.service;

import com.example.logistic.demo.mapper.Mapper;
import com.example.logistic.demo.dto.driver.DriverRequest;
import com.example.logistic.demo.dto.driver.DriverResponse;
import com.example.logistic.demo.dto.driver.DriverResponseWithCars;
import com.example.logistic.demo.model.Car;
import com.example.logistic.demo.model.Driver;
import com.example.logistic.demo.repository.CarRepository;
import com.example.logistic.demo.repository.DriverRepository;
import com.example.logistic.demo.utils.DriverLicenseValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverLicenseValidator validator;
    private final CarRepository carRepository;
    private final Mapper mapper;

    @Transactional(readOnly = true)
    public DriverResponse getDriver(Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Driver with id=" + id + " not found"));
        return mapper.toDto(driver, DriverResponse.class);
    }

    @Transactional(readOnly = true)
    public DriverResponseWithCars getDriverWithCars(Long id) {
        Driver driver = driverRepository.getDriverWithCars(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id=" + id + " not found"));
        return mapper.toDto(driver, DriverResponseWithCars.class);
    }

    @Transactional(readOnly = true)
    public List<DriverResponse> getAllDrivers() {
        return mapper.toListDto(driverRepository.findAll(), DriverResponse.class);
    }

    @Transactional(readOnly = true)
    public List<DriverResponseWithCars> getAllDriversWithCars() {
        return mapper.toListDto(driverRepository.getAllDriversWithCars(), DriverResponseWithCars.class);
    }

    public DriverResponse saveDriver(DriverRequest driverDto) {
        return mapper.toDto(driverRepository.save(mapper.toEntity(driverDto, Driver.class)), DriverResponse.class);
    }

    public DriverResponse updateDriver(Long id, DriverRequest driverDto) {
        Driver driver = driverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Driver with id=" + id + " not found"));
        mapper.updateEntityFromDto(driverDto, driver);
        return mapper.toDto(driverRepository.save(driver), DriverResponse.class);
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public DriverResponseWithCars addCarToDriver(Long driverId, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car with id=" + carId + " not found"));
        Driver driver = driverRepository.getDriverWithCars(driverId)
                .flatMap(foundDriver -> driverRepository.getDriverWithLicences(driverId))
                .orElseThrow(() -> new EntityNotFoundException("Driver with id=" + driverId + " not found"));
        validator.validateLicense(driver, car.getCarType());
        driver.addCar(car);
        return mapper.toDto(driverRepository.save(driver), DriverResponseWithCars.class);
    }

    public DriverResponseWithCars removeCarFromDriver(Long driverId, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car with id=" + carId + " not found"));
        Driver driver = driverRepository.getDriverWithCars(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id=" + driverId + " not found"));
        if (driver.getCars().contains(car)) {
            driver.removeCar(car);
        } else {
            throw new IllegalStateException("Car with id=" + car.getId() + " not attached to driver with id=" + driver.getId());
        }
        return mapper.toDto(driverRepository.save(driver), DriverResponseWithCars.class);
    }
}