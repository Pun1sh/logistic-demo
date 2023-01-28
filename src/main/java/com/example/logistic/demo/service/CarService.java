package com.example.logistic.demo.service;

import com.example.logistic.demo.mapper.Mapper;
import com.example.logistic.demo.dto.car.CarRequest;
import com.example.logistic.demo.dto.car.CarResponse;
import com.example.logistic.demo.model.Car;
import com.example.logistic.demo.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {

    private final CarRepository carRepository;
    private final Mapper mapper;

    @Transactional(readOnly = true)
    public CarResponse getCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car with id=" + id + " not found"));
        return mapper.toDto(car, CarResponse.class);
    }

    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars() {
        return mapper.toListDto(carRepository.findAll(), CarResponse.class);
    }

    public CarResponse saveCar(CarRequest carDto) {
        Car saved = carRepository.save(mapper.toEntity(carDto, Car.class));
        return mapper.toDto(saved, CarResponse.class);
    }

    public CarResponse updateCar(Long id, CarRequest carDto) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car with id=" + id + " not found"));
        mapper.updateEntityFromDto(carDto, car);
        return mapper.toDto(carRepository.save(car), CarResponse.class);
    }

    public void deleteCar(Long id) {
        carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car with id=" + id + " not found"));
        carRepository.deleteById(id);
    }
}
