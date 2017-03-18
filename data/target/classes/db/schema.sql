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
genderId INT UNSIGNED NOT NULL AUTO_INCREMENT,
genderValue VARCHAR(6) NOT NULL,

PRIMARY KEY (genderId)
);



CREATE TABLE family_status(
familyStatusId INT UNSIGNED NOT NULL AUTO_INCREMENT,
familyStatusValue VARCHAR(7) NOT NULL,

PRIMARY KEY (familyStatusId)
);

CREATE TABLE phone_type(
phoneTypeId INT UNSIGNED NOT NULL AUTO_INCREMENT,
phoneTypeValue VARCHAR(6) NOT NULL,

PRIMARY KEY (phoneTypeId)
);

CREATE TABLE addresses(
addressId INT UNSIGNED NOT NULL AUTO_INCREMENT,
country VARCHAR(30),
city VARCHAR(15),
street VARCHAR(30),
house VARCHAR(10),
apartment VARCHAR(10),
zipCode VARCHAR(10),

PRIMARY KEY(addressId)
);

CREATE TABLE contacts(
contactId INT UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(20),
surname VARCHAR(20),
patronymic VARCHAR(20),
dateOfBirth DATE,
gender INT UNSIGNED NOT NULL,
citizenship VARCHAR(20),
familyStatus INT UNSIGNED NOT NULL,
website VARCHAR(20),
email VARCHAR(50),
placeOfWork VARCHAR(20),
address INT UNSIGNED NOT NULL,
photo VARCHAR(50),

FOREIGN KEY (gender) REFERENCES gender(genderId),
FOREIGN KEY (address) REFERENCES addresses(addressId),
FOREIGN KEY (familyStatus) REFERENCES family_status(familyStatusId),
PRIMARY KEY (contactId)
);

CREATE TABLE attachments (
    attachId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    attachName VARCHAR(50),
    uploadDate DATETIME NOT NULL,
    comment VARCHAR(100),
    file VARCHAR(50),
    contact INT UNSIGNED NOT NULL,
    FOREIGN KEY (contact)
        REFERENCES contacts (contactId),
    PRIMARY KEY (attachId)
);

CREATE TABLE phones(
phoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
countryCode VARCHAR(10) NOT NULL,
operatorCode VARCHAR(10) NOT NULL,
phoneNumber VARCHAR(15) NOT NULL,
phoneType INT UNSIGNED NOT NULL,
comment VARCHAR(100),
contact INT UNSIGNED NOT NULL,


FOREIGN KEY (contact) REFERENCES contacts(contactId),
FOREIGN KEY (phoneType) REFERENCES phone_type(phoneTypeId),
PRIMARY KEY (phoneId)
);










