INSERT INTO gender(genderValue) VALUES ("male");
INSERT INTO gender(genderValue) VALUES ("female");
INSERT INTO family_status(familyStatusValue) VALUES("married");
INSERT INTO family_status(familyStatusValue) VALUES("single");
INSERT INTO phone_type(phoneTypeValue) VALUES("home");
INSERT INTO phone_type(phoneTypeValue) VALUES("mobile");

INSERT INTO addresses(country, city, street, house, apartment,zipCode) VALUES("РБ","Минск","ул.Селицкого","77","112","220075");
INSERT INTO contacts(name,surname,patronymic,dateOfBirth,gender,citizenship,familyStatus,website,email,placeOfWork,address,photo)
SELECT "Александр", "Гилимович", "Сергеевич","1990-11-12", gender.genderId, "белорус", f.familyStatusId, NULL, "a.gilimovich@gmail.com", "безработный", last_insert_id(), NULL
FROM gender, family_status AS f WHERE f.familyStatusValue="single" AND gender.genderValue="male";

INSERT INTO attachments(uploadDate, attachName, comment, file, contact) VALUES ("2017-03-12 14:20:51", "Имя присоединения","без комментариев", NULL, last_insert_id());

INSERT INTO phones(countryCode, operatorCode,phoneNumber,phoneType,comment,contact) SELECT "+375","44","7852202", phone_type.phoneTypeId,"без комментариев", c.contactId FROM contacts AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phoneTypeValue = "mobile";
INSERT INTO phones(countryCode, operatorCode,phoneNumber,phoneType,comment,contact) SELECT "+375","17","3182709", phone_type.phoneTypeId,"без комментариев", c.contactId FROM contacts AS c, phone_type WHERE c.surname="Гилимович" AND phone_type.phoneTypeValue = "home";



SELECT
    c.contactId,
    c.name,
    c.surname,
    c.patronymic,
    c.dateOfBirth,
    c.citizenship,
    c.website,
    c.email,
    c.placeOfWork,
    c.photo,
    p.countryCode,
    p.operatorCode,
    p.phoneNumber,
    phone_type.phoneTypeValue,
    p.comment,
    gender.genderValue,
    family_status.familyStatusValue,
    a.country,
    a.city,
    a.street,
    a.house,
    a.apartment,
    a.zipCode,
    at.uploadDate,
    at.attachName,
    at.comment,
    at.file
FROM
    contacts AS c
        INNER JOIN
    gender ON c.gender = gender.genderId
        INNER JOIN
    family_status ON c.familyStatus = family_status.familyStatusId
        INNER JOIN
    addresses AS a ON c.address = a.addressId
        RIGHT OUTER JOIN
    attachments AS at ON at.contact = c.contactId
        RIGHT OUTER JOIN
    (phones AS p
    INNER JOIN phone_type ON p.phoneType = phone_type.phoneTypeId) ON p.contact = c.contactId;

    -- Retrieve all contacts
    SELECT
    c.*, gender.genderValue, family_status.familyStatusValue, a.*, at.*
FROM
    contacts AS c
        INNER JOIN
    gender ON c.gender = gender.genderId
        INNER JOIN
    family_status ON c.familyStatus = family_status.familyStatusId
        INNER JOIN
    addresses AS a ON c.address = a.addressId;

    -- Retrieve all phone numbers for specified contact
    SELECT
    p.countryCode,
    p.operatorCode,
    p.phoneNumber,
    phone_type.phoneTypeValue,
    p.comment
    FROM phones AS p
    LEFT OUTER JOIN phone_type ON p.phoneType = phone_type.phoneTypeId
    WHERE p.contact = 1;

    SELECT
    attachId,
    attachName,
    uploadDate,
    comment,
    file
    FROM attachments
    WHERE contact = 1;

