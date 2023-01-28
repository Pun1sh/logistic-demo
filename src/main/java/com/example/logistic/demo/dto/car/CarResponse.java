package com.example.logistic.demo.dto.car;

import com.example.logistic.demo.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private Long id;
    private CarType carType;
}
