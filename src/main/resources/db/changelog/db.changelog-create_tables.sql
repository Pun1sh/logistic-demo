CREATE TABLE driver
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id)
);
CREATE TABLE driver_license
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date   date,
    end_date     date,
    driver_id    BIGINT,
    license_type VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_driver_license PRIMARY KEY (id)
);

ALTER TABLE driver_license
    ADD CONSTRAINT FK_DRIVER_LICENSE_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

CREATE TABLE car
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    driver_id BIGINT,
    car_type  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_car PRIMARY KEY (id)
);

ALTER TABLE car
    ADD CONSTRAINT FK_CAR_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);