INSERT INTO driver (name)
VALUES ('Vasya');

INSERT INTO driver (name)
VALUES ('Petya');

INSERT INTO driver (name)
VALUES ('Fedya');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 1, 'B');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 1, 'C');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 2, 'C');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 2, 'D');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 3, 'D');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 3, 'B');


INSERT INTO car (driver_id, car_type)
VALUES (1, 'EASY');

INSERT INTO car (driver_id, car_type)
VALUES (1, 'HEAVY');

INSERT INTO car (driver_id, car_type)
VALUES (2, 'HEAVY');

INSERT INTO car (driver_id, car_type)
VALUES (2, 'BUS');

INSERT INTO car (driver_id, car_type)
VALUES (3, 'BUS');

INSERT INTO car (driver_id, car_type)
VALUES (3, 'EASY');