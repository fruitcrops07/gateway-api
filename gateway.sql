CREATE DATABASE IF NOT EXISTS gateway;

use gateway;

CREATE TABLE gateways(
		serial_number VARCHAR(50) NOT NULL PRIMARY KEY, 
		name VARCHAR(100), 
		address VARCHAR(50)
);


INSERT INTO gateways 
values 
("94-05-01", "Gateway 1", "192.168.1.1"),
("94-05-02", "Gateway 2", "172.16.1.1"),
("94-05-03", "Gateway 3", "172.168.8.1"),
("94-05-04", "Gateway 4", "172.17.2.1"),
("94-06-05", "Gateway 5", "192.168.12.1");

CREATE TABLE devices(
		id int AUTO_INCREMENT NOT NULL PRIMARY KEY, 
		gateway_serial_number VARCHAR(50) NOT NULL, 
		vendor VARCHAR(100), 
		created_date DATETIME, 
		status VARCHAR(10), 
		CONSTRAINT FK_GATEWAY_SERIAL_NUMBER FOREIGN KEY (gateway_serial_number) REFERENCES gateways(serial_number)
);

INSERT INTO devices 
values 
(1, "94-05-01", "macintosh", NOW(), "online"),
(2, "94-05-02", "samsung", NOW(), "online"),
(3, "94-05-03", "huawei", NOW(), "online"),
(4, "94-05-04", "dell", NOW(), "online"),
(5, "94-06-05", "asus", NOW(), "online");


CREATE TABLE hibernate_sequence(next_val int);
INSERT INTO hibernate_sequence VALUES (6);

