package com.example.logistic.demo.repository;

import com.example.logistic.demo.model.Car;
import com.example.logistic.demo.model.CarType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("Should save car")
    public void shouldSaveCar() {
        Car car = new Car(null, null, CarType.HEAVY);
        Car saved = carRepository.save(car);
        assertNotNull(saved.getId());
    }

    @Test
    @DisplayName("Should find car by id")
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = "classpath:test-data.sql")
    public void shouldFindCar() {
        Car gotCar = carRepository.findById(1L).get();
        assertNotNull(gotCar.getId());
    }

    @Test
    @DisplayName("Should find all car")
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = "classpath:test-data.sql")
    public void shouldFindAllCars() {
        List<Car> all = carRepository.findAll();
        assertEquals(all.size(), 2);
        assertNotNull(all.get(0).getId());
        assertNotNull(all.get(1).getId());
    }

    @Test
    @DisplayName("Should delete car")
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = "classpath:test-data.sql")
    public void shouldDeleteCar() {
        Car car = carRepository.findById(1L).get();
        assertNotNull(car.getId());
        carRepository.deleteById(1L);
        Optional<Car> byId = carRepository.findById(1L);
        assertTrue(byId.isEmpty());
    }
}