CREATE DATABASE  IF NOT EXISTS contact_book;
USE contact_book;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS family_status;
DROP TABLE IF EXISTS phone_type;
DROP TABLE IF EXISTS contacts;

DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS phones;




CREATE TABLE gender(
gender_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(6) NOT NULL,

PRIMARY KEY (gender_id)
);



CREATE TABLE family_status(
family_status_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(7) NOT NULL,

PRIMARY KEY (family_status_id)
);

CREATE TABLE phone_type(
phone_type_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(6) NOT NULL,

PRIMARY KEY (phone_type_id)
);

CREATE TABLE addresses(
address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
country VARCHAR(15),
city VARCHAR(15),
street VARCHAR(15),
house VARCHAR(10),
apartment VARCHAR(10),
zipCode VARCHAR(10),

PRIMARY KEY(address_id)
);

CREATE TABLE contacts(
contact_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(20),
surname VARCHAR(20),
patronymic VARCHAR(20),
dateOfBirth DATE,
gender INT UNSIGNED NOT NULL,
citizenship VARCHAR(20),
familyStatus INT UNSIGNED NOT NULL,
webSite VARCHAR(20),
email VARCHAR(50),
placeOfWork VARCHAR(20),
address INT UNSIGNED NOT NULL,
photo VARCHAR(50),

FOREIGN KEY (gender) REFERENCES gender(gender_id),
FOREIGN KEY (address) REFERENCES addresses(address_id),
FOREIGN KEY (familyStatus) REFERENCES family_status(family_status_id),
PRIMARY KEY (contact_id)
);

CREATE TABLE attachments (
    attach_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    uploadDate DATETIME NOT NULL,
    comment VARCHAR(100),
    file VARCHAR(50),
    contact INT UNSIGNED NOT NULL,
    FOREIGN KEY (contact)
        REFERENCES contacts (contact_id),
    PRIMARY KEY (attach_id)
);

CREATE TABLE phones(
phone_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
countryCode VARCHAR(10) NOT NULL,
operatorCode VARCHAR(10) NOT NULL,
phoneNumber VARCHAR(15) NOT NULL,
phoneType INT UNSIGNED NOT NULL,
comment VARCHAR(100),
contact INT UNSIGNED NOT NULL,


FOREIGN KEY (contact) REFERENCES contacts(contact_id),
FOREIGN KEY (phoneType) REFERENCES phone_type(phone_type_id),
PRIMARY KEY (phone_id)
);





