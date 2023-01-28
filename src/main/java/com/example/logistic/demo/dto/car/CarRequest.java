package com.example.logistic.demo.dto.car;

import com.example.logistic.demo.model.CarType;
import lombok.Data;

@Data
public class CarRequest {
    private CarType carType;
}
