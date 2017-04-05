var fromDateInput = document.getElementById("from-date");
var toDateInput = document.getElementById("to-date");
var nameInput = document.getElementById("name-input");
var surnameInput = document.getElementById("surname-input");
var patronymicInput = document.getElementById("patronymic-input");

fromDateInput.oninvalid = function () {
    'use strict'
    fromDateInput.setCustomValidity('Введите дату в формате: ДД.ММ.ГГГГ');
}
fromDateInput.oninput = function () {
    'use strict'
    fromDateInput.setCustomValidity('');
}

toDateInput.oninvalid = function () {
    'use strict'
    toDateInput.setCustomValidity('Введите дату в формате: ДД.ММ.ГГГГ');
}
toDateInput.oninput = function () {
    'use strict'
    toDateInput.setCustomValidity('');
}



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