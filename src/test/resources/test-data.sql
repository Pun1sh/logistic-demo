insert INTO driver(name) values ('Vasya');
insert INTO driver(name) values ('Petya');

insert INTO car(car_type,driver_id) values ('EASY',1);
insert INTO car(car_type,driver_id) values ('HEAVY',2);

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 1, 'B');

INSERT INTO driver_license (start_date, end_date, driver_id, license_type)
VALUES ('2021-10-22', '2025-10-22', 2, 'C');