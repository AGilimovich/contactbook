USE aleksandr_gilimovich;
INSERT INTO gender(genderValue) VALUES ("male");
INSERT INTO gender(genderValue) VALUES ("female");
INSERT INTO family_status(familyStatusValue) VALUES("married");
INSERT INTO family_status(familyStatusValue) VALUES("single");
INSERT INTO phone_type(phoneTypeValue) VALUES("home");
INSERT INTO phone_type(phoneTypeValue) VALUES("mobile");

INSERT INTO addresses(country, city, street, house, apartment,zipCode) VALUES("РБ","Минск","ул.Селицкого","77","112","220075");

INSERT INTO contacts(name,surname,patronymic,dateOfBirth,gender,citizenship,familyStatus,website,email,placeOfWork,address,photo)
SELECT "Александр", "Гилимович", "Сергеевич","1990-11-12", gender.genderId, "белорус", f.familyStatusId, NULL, "a.gilimovich@gmail.com", "безработный", 1, NULL
FROM gender, family_status AS f WHERE f.familyStatusValue="single" AND gender.genderValue="male";

INSERT INTO attachments(uploadDate, attachName, comment, file, contact) VALUES ("2017-03-12 14:20:51", "Имя присоединения","без комментариев", NULL, 1);

INSERT INTO phones(countryCode, operatorCode,phoneNumber,phoneType,comment,contact) SELECT "+375","44","7852202", phone_type.phoneTypeId,"без комментариев", c.contactId FROM contacts AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phoneTypeValue = "mobile";
INSERT INTO phones(countryCode, operatorCode,phoneNumber,phoneType,comment,contact) SELECT "+375","17","3182709", phone_type.phoneTypeId,"без комментариев", c.contactId FROM contacts AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phoneTypeValue = "home";
