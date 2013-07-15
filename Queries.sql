DROP DATABASE IF EXISTS Lab3;
CREATE DATABASE Lab3;

USE Lab3;

CREATE TABLE location_type (id_location_type INT NOT NULL AUTO_INCREMENT, location_name CHAR(100) NOT NULL, PRIMARY KEY(id_location_type));


CREATE TABLE places (id_place INT NOT NULL AUTO_INCREMENT, 
place_name CHAR(100) NOT NULL, 
id_location_type int, 
id_parent int, 
KEY (id_location_type), 
FOREIGN KEY(id_location_type) REFERENCES location_type (id_location_type), 
PRIMARY KEY(id_place));

CREATE TABLE device_type (
id_device_type INT NOT NULL AUTO_INCREMENT, 
device_name CHAR(100) NOT NULL, 
PRIMARY KEY(id_device_type));

CREATE TABLE devices (
id_device INT NOT NULL AUTO_INCREMENT, 
device_name CHAR(100) NOT NULL, 
id_device_type int, 
id_parent int, 
id_place int,
KEY (id_place),
KEY (id_device_type), 
FOREIGN KEY(id_device_type) REFERENCES device_type (id_device_type),
FOREIGN KEY(id_place) REFERENCES places (id_place), 
PRIMARY KEY(id_device));



insert into location_type (location_name) values ('region');
insert into location_type (location_name) values ('city');
insert into location_type (location_name) values ('area');
insert into location_type (location_name) values ('building');

insert into places (place_name, id_location_type, id_parent) values ('Sumy region', 1, null);
insert into places (place_name, id_location_type, id_parent) values ('Kyiv region', 1, null);
insert into places (place_name, id_location_type, id_parent) values ('Sumy', 2, 1);
insert into places (place_name, id_location_type, id_parent) values ('Kyiv', 2, 2);
insert into places (place_name, id_location_type, id_parent) values ('Kovpakovskyi area', 3, 3);
insert into places (place_name, id_location_type, id_parent) values ('Zarechnyi area', 3, 3);
insert into places (place_name, id_location_type, id_parent) values ('Building�1', 4,5);
insert into places (place_name, id_location_type, id_parent) values ('Building�2', 4, 5);
insert into places (place_name, id_location_type, id_parent) values ('Building�3', 4, 5);

insert into device_type (device_name) values ('Router');
insert into device_type (device_name) values ('Slot');
insert into device_type (device_name) values ('Card');
insert into device_type (device_name) values ('Port');

insert into devices (device_name, id_device_type, id_parent, id_place) values ('Router�1', 1, null, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Slot�2', 2, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Card�2', 3, 2, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Slot�1', 2, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Port�1', 4, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Port�2', 4, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Port�3', 4, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Port�4', 4, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Slot�4', 2, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Card�2', 3, 10, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Slot�5', 2, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Router�2', null, 1, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Slot�5', 2, 24, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Router�3', 1, null, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Router�4', 1, null, 7);
insert into devices (device_name, id_device_type, id_parent, id_place) values ('Router�5', 1, null, 7);
