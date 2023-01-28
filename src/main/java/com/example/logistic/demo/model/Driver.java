package com.example.logistic.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "driver")
    @Size(max = 3)
    private List<Car> cars;

    @OneToMany(mappedBy = "driver", orphanRemoval = true)
    private List<DriverLicense> driverLicenses;

    @PreRemove
    private void preRemove() {
        cars.forEach(car -> car.setDriver(null));
    }

    public void addCar(Car car) {
        cars.add(car);
        car.setDriver(this);
    }

    public Driver removeCar(Car car) {
        cars.remove(car);
        car.setDriver(null);
        return this;
    }

    public void addDriverLicense(DriverLicense driverLicense) {
        driverLicenses.add(driverLicense);
        driverLicense.setDriver(this);
    }

    public void removeDriverLicense(DriverLicense driverLicense) {
        driverLicenses.remove(driverLicense);
        driverLicense.setDriver(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Driver driver = (Driver) o;
        return id != null && Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}