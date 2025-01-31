-- Users tablosunu oluştur
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Flights tablosunu oluştur
CREATE TABLE flights (
    id BIGSERIAL PRIMARY KEY,
    flight_number VARCHAR(50) NOT NULL UNIQUE,
    departure_city VARCHAR(50) NOT NULL,
    arrival_city VARCHAR(50) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    capacity INTEGER NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Admin ve User için başlangıç verileri
INSERT INTO users (id, city, first_name, last_name, password, role, username) 
VALUES (1, 'BURSA', 'admin', 'admin', 
        '$2a$10$GB1YlEiSez3Vm1pRUiq4OuhCRHBLOXSnvY5QguGrHUmTNB4ICwSOG', 
        'ADMIN', 'admin');

INSERT INTO users (id, city, first_name, last_name, password, role, username) 
VALUES (3, 'ANKARA', 'user', 'user', 
        '$2a$10$RgKUv8k/b1f.10Vaatl2UeJovR3l56GwTYBOsIrrs4npmHmeCAEDO', 
        'USER', 'user');

-- Başlangıç uçuş verileri
INSERT INTO flights (id, capacity, price, arrival_time, departure_time, arrival_city, departure_city, flight_number) 
VALUES (1, 100, 241, '2025-01-30 03:22:00', '2025-01-30 00:22:00', 'IZMIR', 'ISTANBUL', 'T123');

INSERT INTO flights (id, capacity, price, arrival_time, departure_time, arrival_city, departure_city, flight_number) 
VALUES (2, 70, 341, '2025-02-04 00:23:00', '2025-02-03 00:22:00', 'ANKARA', 'BURSA', 'B125');

INSERT INTO flights (id, capacity, price, arrival_time, departure_time, arrival_city, departure_city, flight_number) 
VALUES (3, 300, 1250, '2025-02-01 02:23:00', '2025-02-01 00:23:00', 'BURSA', 'IZMIR', 'TGM123');

INSERT INTO flights (id, capacity, price, arrival_time, departure_time, arrival_city, departure_city, flight_number) 
VALUES (4, 21, 2451, '2025-02-28 00:35:00', '2025-02-18 00:35:00', 'ANTALYA', 'ANKARA', 'VR241');

-- Sequence'ları güncelle
SELECT setval('users_id_seq', 4, true);
SELECT setval('flights_id_seq', 5, true); 