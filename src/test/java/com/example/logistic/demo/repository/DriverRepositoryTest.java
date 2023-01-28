package com.example.logistic.demo.repository;

import com.example.logistic.demo.model.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    @DisplayName("Should find driver with cars")
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = "classpath:test-data.sql")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void getDriverWithCars() {
        Driver driver = driverRepository.getDriverWithCars(1L).get();
        assertNotNull(driver.getCars());
        assertEquals(driver.getCars().size(), 1);
    }

    @Test
    @DisplayName("Should find driver with cars")
    @Sql(executionPhase = BEFORE_TEST_METHOD, value = "classpath:test-data.sql")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void getDriverWithLicences() {
        Driver driver = driverRepository.getDriverWithLicences(1L).get();
        assertNotNull(driver.getDriverLicenses());
        assertEquals(driver.getDriverLicenses().size(), 1);
    }
}