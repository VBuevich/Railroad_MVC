DROP TABLE Seatmap;
DROP TABLE Schedule;
DROP TABLE Ticket;
DROP TABLE Station;
DROP TABLE Train;
DROP TABLE Passenger;
DROP TABLE Employee;

CREATE TABLE Employee
(
	employee_id          INTEGER NOT NULL AUTO_INCREMENT,
	name                 VARCHAR(20) NOT NULL,
	surname              VARCHAR(20) NOT NULL,
	email                VARCHAR(40) NOT NULL,
	password             VARCHAR(100) NOT NULL,
  pass_recovery        VARCHAR(100) NOT NULL,
  PRIMARY KEY (employee_id)
);

CREATE UNIQUE INDEX XAK1Employee ON Employee
(
  email
);

CREATE TABLE Passenger
(
	passenger_id         INTEGER NOT NULL AUTO_INCREMENT,
	name                 VARCHAR(20) NOT NULL,
	surname              VARCHAR(20) NOT NULL,
	dob                  DATE NOT NULL,
	email                VARCHAR(40) NOT NULL,
	password             VARCHAR(100) NOT NULL,
  pass_recovery        VARCHAR(100) NOT NULL,
  PRIMARY KEY (passenger_id)
);

CREATE UNIQUE INDEX XAK1Passenger ON Passenger
(
	name,
	surname,
	dob
);

CREATE UNIQUE INDEX XAK2Passenger ON Passenger
(
  email
);

CREATE TABLE Schedule
(
	schedule_id          INTEGER NOT NULL AUTO_INCREMENT,
  station_name         VARCHAR(20) NOT NULL,
	train_number         INTEGER NOT NULL,  
	time                 TIME NOT NULL,
  PRIMARY KEY (schedule_id)
);

CREATE UNIQUE INDEX XAK1Schedule ON Schedule
(
	train_number,
  station_name
);

CREATE TABLE Station
(
	station_name         VARCHAR(20) NOT NULL,
  PRIMARY KEY (station_name)
);

CREATE TABLE Ticket
(
	ticket_id            INTEGER NOT NULL AUTO_INCREMENT,
	passenger_id         INTEGER NOT NULL,
	train_number         INTEGER NOT NULL,
	departure_station    VARCHAR(20) NOT NULL,
	arrival_station      VARCHAR(20) NOT NULL,
  seat                 VARCHAR(4) NOT NULL,
	is_one_way           BOOLEAN NOT NULL,  
  PRIMARY KEY (ticket_id)
);

CREATE TABLE Train
(
	train_number         INTEGER NOT NULL,
  PRIMARY KEY (train_number)
);

ALTER TABLE Schedule
ADD FOREIGN KEY R_3 (train_number) REFERENCES Train (train_number);

ALTER TABLE Schedule
ADD FOREIGN KEY R_1 (station_name) REFERENCES Station (station_name);

ALTER TABLE Ticket
ADD FOREIGN KEY R_4 (passenger_id) REFERENCES Passenger (passenger_id);

ALTER TABLE Ticket
ADD FOREIGN KEY R_5 (train_number) REFERENCES Train (train_number);

ALTER TABLE Ticket
ADD FOREIGN KEY R_10 (departure_station) REFERENCES Station (station_name);

ALTER TABLE Ticket
ADD FOREIGN KEY R_11 (arrival_station) REFERENCES Station (station_name);

CREATE TABLE Seatmap
(
  seatmap_id           INTEGER NOT NULL AUTO_INCREMENT,
  train_number         INTEGER NOT NULL,
  seat                 VARCHAR(4) NOT NULL,
  passenger_owner      INTEGER, 
  PRIMARY KEY (seatmap_id)
);

ALTER TABLE Seatmap
ADD FOREIGN KEY R_12 (train_number) REFERENCES Train (train_number);

ALTER TABLE Seatmap
ADD FOREIGN KEY R_13 (passenger_owner) REFERENCES Passenger (passenger_id);
