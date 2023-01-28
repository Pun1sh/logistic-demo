package com.example.logistic.demo.service;

import com.example.logistic.demo.dto.car.CarRequest;
import com.example.logistic.demo.dto.car.CarResponse;
import com.example.logistic.demo.mapper.Mapper;
import com.example.logistic.demo.model.Car;
import com.example.logistic.demo.model.CarType;
import com.example.logistic.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private Mapper mapper;
    @Mock
    private CarRepository carRepository;
    @Captor
    private ArgumentCaptor<Car> carArgumentCaptor;

    private static final PodamFactory factory = new PodamFactoryImpl();

    private Car car;

    @BeforeEach
    public void setup() {
        car = factory.manufacturePojo(Car.class);
    }

    @Test
    @DisplayName("Should inject mocks")
    void injectedComponentsAreNotNull() {
        assertThat(carRepository).isNotNull();
        assertThat(mapper).isNotNull();
    }

    @Test
    @DisplayName("Should get car by id")
    void getCar() {
        CarResponse mockCarResponse = factory.manufacturePojo(CarResponse.class);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of((car)));
        when(mapper.toDto(car, CarResponse.class)).thenReturn(mockCarResponse);
        CarResponse carResponse = carService.getCar(car.getId());
        assertThat(carResponse.getId()).isEqualTo(mockCarResponse.getId());
        assertThat(carResponse.getCarType()).isEqualTo(mockCarResponse.getCarType());
    }

    @Test
    @DisplayName("Should get all cars")
    void getAllCars() {
        Car car2 = factory.manufacturePojo(Car.class);
        List<Car> carList = List.of(car, car2);
        CarResponse mockCarResponse = factory.manufacturePojo(CarResponse.class);
        CarResponse mockCarResponse2 = factory.manufacturePojo(CarResponse.class);
        List<CarResponse> mockCarResponseList = List.of(mockCarResponse, mockCarResponse2);
        when(carRepository.findAll()).thenReturn(carList);
        when(mapper.toListDto(carList, CarResponse.class)).thenReturn(mockCarResponseList);
        List<CarResponse> carResponseList = carService.getAllCars();
        assertThat(carResponseList.size()).isEqualTo(mockCarResponseList.size());
        assertThat(carResponseList.get(0).getId()).isEqualTo(mockCarResponseList.get(0).getId());
    }

    @Test
    @DisplayName("Should save car")
    void saveCar() {
        CarRequest carRequest = factory.manufacturePojo(CarRequest.class);
        CarResponse mockCarResponse = factory.manufacturePojo(CarResponse.class);
        when(mapper.toEntity(carRequest, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(mapper.toDto(car, CarResponse.class)).thenReturn(mockCarResponse);
        CarResponse actualCarResponse = carService.saveCar(carRequest);
        verify(carRepository, times(1)).save(carArgumentCaptor.capture());
        assertThat(carArgumentCaptor.getValue().getId()).isEqualTo(car.getId());
        assertThat(carArgumentCaptor.getValue().getCarType()).isEqualTo(car.getCarType());
        assertThat(actualCarResponse.getId()).isEqualTo(mockCarResponse.getId());
        assertThat(actualCarResponse.getCarType()).isEqualTo(mockCarResponse.getCarType());
    }

    @Test
    @DisplayName("Should update car")
    void updateCar() {
        CarRequest carRequest = factory.manufacturePojo(CarRequest.class);
        CarResponse mockCarResponse = factory.manufacturePojo(CarResponse.class);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        doAnswer(invocation -> {
            car.setCarType(CarType.EASY);
            return null;
        }).when(mapper).updateEntityFromDto(carRequest, car);
        when(carRepository.save(car)).thenReturn(car);
        when(mapper.toDto(car, CarResponse.class)).thenReturn(mockCarResponse);
        CarResponse actualCarResponse = carService.updateCar(car.getId(), carRequest);
        verify(carRepository, times(1)).save(carArgumentCaptor.capture());
        assertThat(carArgumentCaptor.getValue().getId()).isEqualTo(car.getId());
        assertThat(carArgumentCaptor.getValue().getCarType()).isEqualTo(CarType.EASY);
        assertThat(actualCarResponse.getId()).isEqualTo(mockCarResponse.getId());
        assertThat(actualCarResponse.getCarType()).isEqualTo(mockCarResponse.getCarType());
    }

    @Test
    @DisplayName("Should delete car")
    void deleteCar() {
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        doNothing().when(carRepository).deleteById(car.getId());
        carService.deleteCar(car.getId());
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(carRepository, times(1)).deleteById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(car.getId());
    }
}