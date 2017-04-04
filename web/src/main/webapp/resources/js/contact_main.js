var nameInput = document.getElementById("name-input");
var surnameInput = document.getElementById("surname-input");
var patronymicInput = document.getElementById("patronymic-input");
var dateInput = document.getElementById("date-input");
var emailInput = document.getElementById("email-input");

nameInput.oninvalid = function () {
    'use strict'
    nameInput.setCustomValidity('Имя может содержать только буквы');
}
nameInput.oninput = function () {
    'use strict'
    nameInput.setCustomValidity('');
}

surnameInput.oninvalid = function () {
    'use strict'
    surnameInput.setCustomValidity('Фамилия может содержать только буквы');
}
surnameInput.oninput = function () {
    'use strict'
    surnameInput.setCustomValidity('');
}

patronymicInput.oninvalid = function () {
    'use strict'
    patronymicInput.setCustomValidity('Отчество может содержать только буквы');
}
patronymicInput.oninput = function () {
    'use strict'
    patronymicInput.setCustomValidity('');
}

dateInput.oninvalid = function () {
    'use strict'
    dateInput.setCustomValidity('Введите дату в формате: ДД.ММ.ГГГГ');
}
dateInput.oninput = function () {
    'use strict'
    dateInput.setCustomValidity('');
}

emailInput.oninvalid = function () {
    'use strict'
    emailInput.setCustomValidity('Недопустимый email адрес');
}
emailInput.oninput = function () {
    'use strict'
    emailInput.setCustomValidity('');
}