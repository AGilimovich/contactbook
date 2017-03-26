CREATE DATABASE  IF NOT EXISTS `aleksandr_gilimovich`;
USE `aleksandr_gilimovich`;

DROP TABLE IF EXISTS `fileId`;

DROP TABLE IF EXISTS `attachment`;
DROP TABLE IF EXISTS `phone`;
DROP TABLE IF EXISTS `contactId`;
DROP TABLE IF EXISTS `gender`;
DROP TABLE IF EXISTS `addressId`;
DROP TABLE IF EXISTS `family_status`;
DROP TABLE IF EXISTS `phone_type`;
DROP TABLE IF EXISTS `file_storage`;
DROP TABLE IF EXISTS `contact_address`;


CREATE TABLE `gender`(
`gender_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`gender_value` VARCHAR(6) NOT NULL,

PRIMARY KEY (`gender_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;



CREATE TABLE `family_status`(
`family_status_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`family_status_value` VARCHAR(7) NOT NULL,

PRIMARY KEY (`family_status_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;

CREATE TABLE `phone_type`(
`phone_type_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`phone_type_value` VARCHAR(6) NOT NULL,

PRIMARY KEY (`phone_type_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;

CREATE TABLE `addressId`(
`address_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`country` VARCHAR(50),
`city` VARCHAR(50),
`street` VARCHAR(30),
`house` VARCHAR(20),
`apartment` VARCHAR(10),
`zip_code` VARCHAR(10),

PRIMARY KEY(`address_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;

CREATE TABLE `file_storage`(
`file_storage_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
PRIMARY KEY (`file_storage_id`)
)DEFAULT CHARSET utf8mb4 ENGINE InnoDB;

CREATE TABLE `contactId`(
`contact_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`surname` VARCHAR(50) NOT NULL,
`patronymic` VARCHAR(50),
`date_of_birth` DATE,
`gender` INT UNSIGNED NOT NULL,
`citizenship` VARCHAR(50),
`family_status` INT UNSIGNED NOT NULL,
`website` VARCHAR(20),
`email` VARCHAR(50),
`place_of_work` VARCHAR(50),
`file_storage_id` BIGINT UNSIGNED,
FOREIGN KEY (`gender`) REFERENCES `gender`(`gender_id`),
FOREIGN KEY (`family_status`) REFERENCES `family_status`(`family_status_id`),
FOREIGN KEY (`file_storage_id`) REFERENCES `file_storage`(`file_storage_id`),
PRIMARY KEY (`contact_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;




CREATE TABLE `contact_address`(
`contact_address_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`contact_id` BIGINT UNSIGNED NOT NULL,
`address_id` BIGINT UNSIGNED NOT NULL,
PRIMARY KEY(`contact_address_id`),
FOREIGN KEY (`contact_id`) REFERENCES `contactId`(`contact_id`) ON DELETE CASCADE,
FOREIGN KEY (`address_id`) REFERENCES `addressId`(`address_id`) ON DELETE CASCADE
)DEFAULT CHARSET utf8mb4 ENGINE InnoDB;




CREATE TABLE `attachment` (
    `attach_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `attach_name` VARCHAR(50),
    `upload_date` DATETIME NOT NULL,
    `comment` VARCHAR(100),
    `file_storage_id` BIGINT UNSIGNED,
    `contactId` BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (`contactId`)
        REFERENCES `contactId` (`contact_id`),
	FOREIGN KEY (`file_storage_id`) REFERENCES `file_storage`(`file_storage_id`),
    PRIMARY KEY (`attach_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;




CREATE TABLE `fileId`(
`file_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50),
`stored_name` VARCHAR(50) UNIQUE,
`file_storage_id` BIGINT UNSIGNED NOT NULL,
FOREIGN KEY (`file_storage_id`) REFERENCES `file_storage`(`file_storage_id`),
PRIMARY KEY(`file_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;




CREATE TABLE `phone`(
`phone_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`country_code` VARCHAR(10) NOT NULL,
`operator_code` VARCHAR(10) NOT NULL,
`phone_number` VARCHAR(15) NOT NULL,
`phone_type` INT UNSIGNED NOT NULL,
`comment` VARCHAR(100),
`contactId` BIGINT UNSIGNED NOT NULL,


FOREIGN KEY (`contactId`) REFERENCES `contactId`(`contact_id`),
FOREIGN KEY (`phone_type`) REFERENCES `phone_type`(`phone_type_id`),
PRIMARY KEY (`phone_id`)
) DEFAULT CHARSET utf8mb4 ENGINE InnoDB;



CREATE USER IF NOT EXISTS gilimovich@localhost IDENTIFIED BY '1234567';
GRANT ALL PRIVILEGES ON * . * TO gilimovich@localhost;














