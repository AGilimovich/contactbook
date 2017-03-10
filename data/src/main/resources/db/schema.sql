USE contact_book;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS phones;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS family_status;
DROP TABLE IF EXISTS phone_type;



CREATE TABLE gender(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(6) NOT NULL,

PRIMARY KEY (id)
);



CREATE TABLE family_status(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(7) NOT NULL,

PRIMARY KEY (id)
);

CREATE TABLE phone_type(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
value VARCHAR(6) NOT NULL,

PRIMARY KEY (id)
);

CREATE TABLE addresses(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
country VARCHAR(15),
city VARCHAR(15),
street VARCHAR(15),
house VARCHAR(10),
apartment VARCHAR(10),
zipCode VARCHAR(10),

PRIMARY KEY(id)
);

CREATE TABLE contacts(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(20),
surname VARCHAR(20),
patronymic VARCHAR(20),
dateOfBirth DATE,
gender INT UNSIGNED NOT NULL,
citizenship VARCHAR(20),
familyStatus INT UNSIGNED NOT NULL,
webSite VARCHAR(20),
email VARCHAR(20),
placeOfWork VARCHAR(20),
address INT UNSIGNED NOT NULL,
photo BLOB,

FOREIGN KEY (gender) REFERENCES gender(id),
FOREIGN KEY (address) REFERENCES addresses(id),
FOREIGN KEY (familyStatus) REFERENCES family_status(id),
PRIMARY KEY (id)
);

CREATE TABLE attachments(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
uploadDate DATETIME NOT NULL,
comment VARCHAR(100),
file BLOB,
contact INT UNSIGNED NOT NULL,

FOREIGN KEY (contact) REFERENCES contacts(id),
PRIMARY KEY (id)
);

CREATE TABLE phones(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
countryCode VARCHAR(10) NOT NULL,
operatorCode VARCHAR(10) NOT NULL,
phoneNumber VARCHAR(15) NOT NULL,
phoneType INT UNSIGNED NOT NULL,
comment VARCHAR(100),
contact INT UNSIGNED NOT NULL,


FOREIGN KEY (contact) REFERENCES contacts(id),
FOREIGN KEY (phoneType) REFERENCES phone_type(id),
PRIMARY KEY (id)
);