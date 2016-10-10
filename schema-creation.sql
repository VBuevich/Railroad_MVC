DROP TABLE Seatmap;
DROP TABLE Schedule;
DROP TABLE Ticket;
DROP TABLE Station;
DROP TABLE Train;
DROP TABLE Passenger;
DROP TABLE Employee;
DROP TABLE Template_rows;
DROP TABLE Template_seats;
DROP TABLE Template_train;
DROP TABLE Statistics;

CREATE TABLE Template_train
(
  template_train_id      INTEGER NOT NULL AUTO_INCREMENT,
  template_id            VARCHAR(3),
  PRIMARY KEY (template_train_id)
);

CREATE UNIQUE INDEX XAK1Template_train ON Template_train
(
  template_id
);

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
  template_id          VARCHAR(3),
  PRIMARY KEY (train_number)
);
ALTER TABLE Train
  ADD FOREIGN KEY R_23 (template_id) REFERENCES Template_train (template_id);

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

CREATE TABLE Template_rows
(
  Template_rows_id     INTEGER NOT NULL AUTO_INCREMENT,
  template_id          VARCHAR(3),
  row_number           INTEGER NOT NULL,
  row_seats            VARCHAR(10) NOT NULL,
  PRIMARY KEY (Template_rows_id)
);

ALTER TABLE Template_rows
  ADD FOREIGN KEY R_21 (template_id) REFERENCES Template_train (template_id);

CREATE TABLE Template_seats
(
  Template_seats_id    INTEGER NOT NULL AUTO_INCREMENT,
  template_id          VARCHAR(3),
  seat                 VARCHAR(7) NOT NULL,
  PRIMARY KEY (Template_seats_id)
);

ALTER TABLE Template_seats
  ADD FOREIGN KEY R_22 (template_id) REFERENCES Template_train (template_id);

CREATE TABLE Statistics
(
  statistics_id        INTEGER NOT NULL AUTO_INCREMENT,
  datetime             DATETIME NOT NULL,
  passenger_name       VARCHAR(20) NOT NULL,
  passenger_surname    VARCHAR(20) NOT NULL,
  passenger_dob        DATE NOT NULL,
  passenger_email      VARCHAR(40) NOT NULL,
  train_number         INTEGER NOT NULL,
  train_type           VARCHAR(3) NOT NULL,
  departure_station    VARCHAR(20) NOT NULL,
  arrival_station      VARCHAR(20) NOT NULL,
  seat                 VARCHAR(4) NOT NULL,
  is_one_way           BOOLEAN NOT NULL,
  departure_date       DATE NOT NULL,
  departure_time       TIME NOT NULL,
  PRIMARY KEY (statistics_id)
);