USE aleksandr_gilimovich;
INSERT INTO gender(gender_value) VALUES ("male");
INSERT INTO gender(gender_value) VALUES ("female");
INSERT INTO family_status(family_status_value) VALUES("married");
INSERT INTO family_status(family_status_value) VALUES("single");
INSERT INTO phone_type(phone_type_value) VALUES("home");
INSERT INTO phone_type(phone_type_value) VALUES("mobile");

INSERT INTO `file_storage` VALUES();
INSERT INTO fileId(name,stored_name, file_storage_id) VALUES("фото", "имя на диске", 1);
INSERT INTO `file_storage` VALUES();
INSERT INTO fileId(name,stored_name, file_storage_id) VALUES("аттач", "аттач на диске", 2);

INSERT INTO addressId(country, city, street, house, apartment,zip_code) VALUES("РБ","Минск","ул.Селицкого","77","112","220075");

INSERT INTO contactId(name,surname,patronymic,date_of_birth,gender,citizenship,family_status,website,email,place_of_work,file_storage_id)
SELECT "Александр", "Гилимович", "Сергеевич","1990-11-12", gender.gender_id, "белорус", f.family_status_id, NULL, "a.gilimovich@gmail.com", "безработный", 1
FROM gender, family_status AS f WHERE f.family_status_value="single" AND gender.gender_value="male";

INSERT INTO contact_address(address_id, contact_id) VALUES (1,1);

INSERT INTO attachment(upload_date, attach_name, comment, file_storage_id, contactId) VALUES ("2017-03-12 14:20:51", "Имя присоединения","без комментариев", 2, 1);

INSERT INTO phone(country_code, operator_code,phone_number,phone_type,comment,contactId) SELECT "+375","44","7852202", phone_type.phone_type_id,"без комментариев", c.contact_id FROM contactId AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phone_type_value = "mobile";
INSERT INTO phone(country_code, operator_code,phone_number,phone_type,comment,contactId) SELECT "+375","17","3182709", phone_type.phone_type_id,"без комментариев", c.contact_id FROM contactId AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phone_type_value = "home";
