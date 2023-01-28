package com.example.logistic.demo.repository;

import com.example.logistic.demo.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select d from Driver d left join fetch d.cars where d.id = :id")
    Optional<Driver> getDriverWithCars(@Param("id") Long id);

    @Query("select d from Driver d left join fetch d.driverLicenses where d.id = :id")
    Optional<Driver> getDriverWithLicences(@Param("id") Long id);

    @Query("select d from Driver d left join fetch d.cars")
    List<Driver> getAllDriversWithCars();

    @Query("select d from Driver d left join fetch d.driverLicenses")
    List<Driver> getAllDriversWithLicences();
}