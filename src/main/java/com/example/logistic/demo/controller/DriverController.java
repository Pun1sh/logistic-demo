package com.example.logistic.demo.controller;

import com.example.logistic.demo.dto.driver.DriverRequest;
import com.example.logistic.demo.dto.driver.DriverResponse;
import com.example.logistic.demo.dto.driver.DriverResponseWithCars;
import com.example.logistic.demo.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/with_cars")
    public ResponseEntity<DriverResponseWithCars> getDriverWithCars(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriverWithCars(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/with_cars")
    public ResponseEntity<List<DriverResponseWithCars>> getAllDriversWithCars() {
        return new ResponseEntity<>(driverService.getAllDriversWithCars(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> saveDriver(@RequestBody DriverRequest driverDto) {
        return new ResponseEntity<>(driverService.saveDriver(driverDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@PathVariable Long id, @RequestBody DriverRequest driverDto) {
        return new ResponseEntity<>(driverService.updateDriver(id, driverDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>("Driver was deleted", HttpStatus.OK);
    }

    @PutMapping("/{driverId}/add_car/{carId}")
    public ResponseEntity<DriverResponseWithCars> addCarToDriver(@PathVariable Long driverId, @PathVariable Long carId) {
        return new ResponseEntity<>(driverService.addCarToDriver(driverId, carId), HttpStatus.OK);
    }

    @PutMapping("/{driverId}/remove_car/{carId}")
    public ResponseEntity<DriverResponseWithCars> removeCarFromDriver(@PathVariable Long driverId, @PathVariable Long carId) {
        return new ResponseEntity<>(driverService.removeCarFromDriver(driverId, carId), HttpStatus.OK);
    }
}
