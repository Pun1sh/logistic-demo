package com.example.logistic.demo.service;

import com.example.logistic.demo.dto.driver.DriverRequest;
import com.example.logistic.demo.dto.driver.DriverResponse;
import com.example.logistic.demo.dto.driver.DriverResponseWithCars;
import com.example.logistic.demo.mapper.Mapper;
import com.example.logistic.demo.model.Car;
import com.example.logistic.demo.model.Driver;
import com.example.logistic.demo.repository.CarRepository;
import com.example.logistic.demo.repository.DriverRepository;
import com.example.logistic.demo.utils.DriverLicenseValidator;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverLicenseValidator driverLicenseValidator;
    @Mock
    private CarRepository carRepository;
    @Mock
    private Mapper mapper;
    @Captor
    private ArgumentCaptor<Driver> driverArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    private static final PodamFactory factory = new PodamFactoryImpl();

    private Driver driver;

    @BeforeEach
    public void setup() {
        driver = factory.manufacturePojo(Driver.class);
    }

    @Test
    @DisplayName("Should inject mocks")
    void injectedComponentsAreNotNull() {
        assertThat(driverRepository).isNotNull();
        assertThat(driverLicenseValidator).isNotNull();
        assertThat(carRepository).isNotNull();
        assertThat(mapper).isNotNull();
    }

    @Test
    @DisplayName("Should get driver by id")
    void getDriver() {
        DriverResponse mockDriverResponse = factory.manufacturePojo(DriverResponse.class);
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of((driver)));
        when(mapper.toDto(driver, DriverResponse.class)).thenReturn(mockDriverResponse);
        DriverResponse driverResponse = driverService.getDriver(driver.getId());
        assertThat(driverResponse.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(driverResponse.getName()).isEqualTo(mockDriverResponse.getName());
    }

    @Test
    @DisplayName("Should get driver by id with cars")
    void getDriverWithCars() {
        DriverResponseWithCars mockDriverResponse = factory.manufacturePojo(DriverResponseWithCars.class);
        when(driverRepository.getDriverWithCars(driver.getId())).thenReturn(Optional.of((driver)));
        when(mapper.toDto(driver, DriverResponseWithCars.class)).thenReturn(mockDriverResponse);
        DriverResponseWithCars driverWithCars = driverService.getDriverWithCars(driver.getId());
        assertThat(driverWithCars.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(driverWithCars.getName()).isEqualTo(mockDriverResponse.getName());
        assertThat(driverWithCars.getCars().size()).isEqualTo(mockDriverResponse.getCars().size());
    }

    @Test
    @DisplayName("Should get all drivers")
    void getAllDrivers() {
        Driver driver2 = factory.manufacturePojo(Driver.class);
        List<Driver> driverList = List.of(driver, driver2);
        DriverResponse mockDriverResponse = factory.manufacturePojo(DriverResponse.class);
        DriverResponse mockDriverResponse2 = factory.manufacturePojo(DriverResponse.class);
        List<DriverResponse> mockDriverResponseList = List.of(mockDriverResponse, mockDriverResponse2);
        when(driverRepository.findAll()).thenReturn(driverList);
        when(mapper.toListDto(driverList, DriverResponse.class)).thenReturn(mockDriverResponseList);
        List<DriverResponse> driverResponseList = driverService.getAllDrivers();
        assertThat(driverResponseList.size()).isEqualTo(mockDriverResponseList.size());
        assertThat(driverResponseList.get(0).getId()).isEqualTo(mockDriverResponseList.get(0).getId());
    }

    @Test
    @DisplayName("Should get all drivers with cars")
    void getAllDriversWithCars() {
        Driver driver2 = factory.manufacturePojo(Driver.class);
        List<Driver> driverList = List.of(driver, driver2);
        DriverResponseWithCars mockDriverResponse = factory.manufacturePojo(DriverResponseWithCars.class);
        DriverResponseWithCars mockDriverResponse2 = factory.manufacturePojo(DriverResponseWithCars.class);
        List<DriverResponseWithCars> mockDriverResponseList = List.of(mockDriverResponse, mockDriverResponse2);
        when(driverRepository.getAllDriversWithCars()).thenReturn(driverList);
        when(mapper.toListDto(driverList, DriverResponseWithCars.class)).thenReturn(mockDriverResponseList);
        List<DriverResponseWithCars> driverResponseList = driverService.getAllDriversWithCars();
        assertThat(driverResponseList.size()).isEqualTo(mockDriverResponseList.size());
        assertThat(driverResponseList.get(0).getId()).isEqualTo(mockDriverResponseList.get(0).getId());
    }

    @Test
    @DisplayName("Should save driver")
    void saveDriver() {
        DriverRequest driverRequest = factory.manufacturePojo(DriverRequest.class);
        DriverResponse mockDriverResponse = factory.manufacturePojo(DriverResponse.class);
        when(mapper.toEntity(driverRequest, Driver.class)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(mapper.toDto(driver, DriverResponse.class)).thenReturn(mockDriverResponse);
        DriverResponse driverResponse = driverService.saveDriver(driverRequest);
        verify(driverRepository, times(1)).save(driverArgumentCaptor.capture());
        assertThat(driverArgumentCaptor.getValue().getId()).isEqualTo(driver.getId());
        assertThat(driverArgumentCaptor.getValue().getName()).isEqualTo(driver.getName());
        assertThat(driverArgumentCaptor.getValue().getDriverLicenses().size()).isEqualTo(driver.getDriverLicenses().size());
        assertThat(driverResponse.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(driverResponse.getName()).isEqualTo(mockDriverResponse.getName());
    }

    @Test
    @DisplayName("Should update driver")
    void updateDriver() {
        DriverRequest driverRequest = factory.manufacturePojo(DriverRequest.class);
        DriverResponse mockDriverResponse = factory.manufacturePojo(DriverResponse.class);
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        doAnswer(invocation -> {
            driver.setName("Volodya");
            return null;
        }).when(mapper).updateEntityFromDto(driverRequest, driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(mapper.toDto(driver, DriverResponse.class)).thenReturn(mockDriverResponse);
        DriverResponse driverResponse = driverService.updateDriver(driver.getId(), driverRequest);
        verify(driverRepository, times(1)).save(driverArgumentCaptor.capture());
        assertThat(driverArgumentCaptor.getValue().getId()).isEqualTo(driver.getId());
        assertThat(driverArgumentCaptor.getValue().getName()).isEqualTo("Volodya");
        assertThat(driverResponse.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(driverResponse.getName()).isEqualTo(mockDriverResponse.getName());
    }

    @Test
    @DisplayName("Should delete driver")
    void deleteDriver() {
        doNothing().when(driverRepository).deleteById(any(Long.class));
        driverService.deleteDriver(driver.getId());
        verify(driverRepository, times(1)).deleteById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(driver.getId());
    }

    @Test
    @DisplayName("Should add car to driver")
    void addCarToDriver() {
        int initSize = driver.getCars().size();
        Driver spy = spy(driver);
        Car car = factory.manufacturePojo(Car.class);
        DriverResponseWithCars mockDriverResponse = factory.manufacturePojo(DriverResponseWithCars.class);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(driverRepository.getDriverWithCars(driver.getId())).thenReturn(Optional.of(driver));
        when(driverRepository.getDriverWithLicences(driver.getId())).thenReturn(Optional.of(spy));
        doNothing().when(driverLicenseValidator).validateLicense(spy, car.getCarType());
        doCallRealMethod().when(spy).addCar(car);
        when(driverRepository.save(spy)).thenReturn(driver);
        when(mapper.toDto(driver, DriverResponseWithCars.class)).thenReturn(mockDriverResponse);
        DriverResponseWithCars actualDriverResponse = driverService.addCarToDriver(driver.getId(), car.getId());
        verify(carRepository, times(1)).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(car.getId());
        verify(driverRepository, times(1)).getDriverWithCars(driver.getId());
        verify(driverRepository, times(1)).getDriverWithLicences(driver.getId());
        verify(driverLicenseValidator, times(1)).validateLicense(spy, car.getCarType());
        verify(driverRepository, times(1)).save(driverArgumentCaptor.capture());
        assertTrue(driverArgumentCaptor.getValue().getCars().contains(car));
        assertTrue(driver.getCars().contains(car));
        assertThat(driver.getCars().size()).isEqualTo(initSize + 1);
        assertThat(actualDriverResponse.getName()).isEqualTo(mockDriverResponse.getName());
        assertThat(actualDriverResponse.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(actualDriverResponse.getCars().size()).isEqualTo(mockDriverResponse.getCars().size());
    }

    @Test
    @DisplayName("Should remove car from driver")
    void removeCarFromDriver() {
        Car car = factory.manufacturePojo(Car.class);
        driver.addCar(car);
        Driver spy = spy(driver);
        int initSize = driver.getCars().size();
        DriverResponseWithCars mockDriverResponse = factory.manufacturePojo(DriverResponseWithCars.class);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(driverRepository.getDriverWithCars(driver.getId())).thenReturn(Optional.of(spy));
        doCallRealMethod().when(spy).removeCar(car);
        when(driverRepository.save(spy)).thenReturn(driver);
        when(mapper.toDto(driver, DriverResponseWithCars.class)).thenReturn(mockDriverResponse);
        DriverResponseWithCars actualDriverResponse = driverService.removeCarFromDriver(driver.getId(), car.getId());
        verify(carRepository, times(1)).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(car.getId());
        verify(driverRepository, times(1)).getDriverWithCars(driver.getId());
        verify(driverRepository, times(1)).save(driverArgumentCaptor.capture());
        assertThat(driverArgumentCaptor.getValue().getCars().size()).isEqualTo(initSize - 1);
        assertFalse(driver.getCars().contains(car));
        assertThat(actualDriverResponse.getName()).isEqualTo(mockDriverResponse.getName());
        assertThat(actualDriverResponse.getId()).isEqualTo(mockDriverResponse.getId());
        assertThat(actualDriverResponse.getCars().size()).isEqualTo(mockDriverResponse.getCars().size());
    }
}