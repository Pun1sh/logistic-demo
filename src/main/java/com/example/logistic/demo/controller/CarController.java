package com.example.logistic.demo.controller;

import com.example.logistic.demo.dto.car.CarRequest;
import com.example.logistic.demo.dto.car.CarResponse;
import com.example.logistic.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCar(@PathVariable Long id) {
        return new ResponseEntity<>(carService.getCar(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarResponse> saveCar(@RequestBody CarRequest carDto) {
        return new ResponseEntity<>(carService.saveCar(carDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody CarRequest carDto) {
        return new ResponseEntity<>(carService.updateCar(id, carDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>("Car was deleted", HttpStatus.OK);
    }
}
