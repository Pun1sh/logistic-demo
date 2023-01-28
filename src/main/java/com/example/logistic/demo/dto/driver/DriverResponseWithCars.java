package com.example.logistic.demo.dto.driver;

import com.example.logistic.demo.dto.car.CarResponse;
import lombok.Data;

import java.util.List;

@Data
public class DriverResponseWithCars {
    private Long id;
    private String name;
    private List<CarResponse> cars;
}
