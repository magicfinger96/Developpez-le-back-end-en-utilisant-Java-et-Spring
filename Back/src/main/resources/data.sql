CREATE DATABASE rentalddb;
USE rentalddb;

CREATE TABLE `USER` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `RENTAL` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `MESSAGE` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `USER_index` ON `USER` (`email`);

ALTER TABLE `RENTAL` ADD FOREIGN KEY (`owner_id`) REFERENCES `USER` (`id`);

ALTER TABLE `MESSAGE` ADD FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`);

ALTER TABLE `MESSAGE` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTAL` (`id`);
