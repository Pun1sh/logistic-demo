package com.example.logistic.demo.repository;

import com.example.logistic.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}