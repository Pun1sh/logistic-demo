package com.example.logistic.demo.controller;

import com.example.logistic.demo.dto.car.CarRequest;
import com.example.logistic.demo.dto.car.CarResponse;
import com.example.logistic.demo.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class)
class CarControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    private static final PodamFactory factory = new PodamFactoryImpl();

    private CarResponse carResponse;

    @BeforeEach
    public void setup() {
        carResponse = factory.manufacturePojo(CarResponse.class);
    }

    @Test
    @DisplayName("GET /car/{id} REQUEST")
    void getCar() throws Exception {
        when(carService.getCar(1L)).thenReturn(carResponse);
        mockMvc.perform(get("/car/1"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is(carResponse.getId())))
                .andExpect(jsonPath("$.carType", Matchers.is(carResponse.getCarType().name())));
    }

    @Test
    @DisplayName("GET /car REQUEST")
    void getAllCars() throws Exception {
        CarResponse carResponse1 = factory.manufacturePojo(CarResponse.class);
        when(carService.getAllCars()).thenReturn(List.of(carResponse, carResponse1));
        mockMvc.perform(get("/car"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(carResponse.getId())))
                .andExpect(jsonPath("$[1].id", Matchers.is(carResponse1.getId())))
                .andExpect(jsonPath("$[0].carType", Matchers.is(carResponse.getCarType().name())))
                .andExpect(jsonPath("$[1].carType", Matchers.is(carResponse1.getCarType().name())));
    }

    @Test
    @DisplayName("POST /car REQUEST")
    void saveCar() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CarRequest carRequest = factory.manufacturePojo(CarRequest.class);
        when(carService.saveCar(carRequest)).thenReturn(carResponse);
        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is(carResponse.getId())))
                .andExpect(jsonPath("$.carType", Matchers.is(carResponse.getCarType().name())));
    }


    @Test
    @DisplayName("DELETE /car/{id} REQUEST")
    void deleteCar() throws Exception {
        doNothing().when(carService).deleteCar(1L);
        mockMvc.perform(delete("/car/1"))
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.is("Car was deleted")));
    }
}