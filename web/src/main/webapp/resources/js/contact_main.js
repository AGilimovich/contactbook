var nameInput = document.getElementById("name-input");
var surnameInput = document.getElementById("surname-input");
var patronymicInput = document.getElementById("patronymic-input");
var dateInput = document.getElementById("date-input");
var emailInput = document.getElementById("email-input");

var validateButton = document.getElementById("btn-validate-form");
var submitButton = document.getElementById("btn-submit-form");
var form = document.getElementById("main-form");


dateInput.checkValidity = function () {
    var dateString = document.getElementById("date-input").value;
    if (dateString !== '') {
        if (validateDate(dateString)) {
            var date = parseDate(dateString);
            if (date > new Date()) {
                dateInput.setCustomValidity('Дата рождения не может быть в будущем');
            } else {
                dateInput.setCustomValidity('');
            }
        } else {
            dateInput.setCustomValidity('Некорректно введена дата');
        }
    }

}

validateButton.onclick = function () {
    'use strict'
    dateInput.checkValidity();
    submitButton.click()
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


function parseDate(strDate) {
    'use strict'
    var dateParts = strDate.split(".");
    return new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);
}

function validateDate(strDate) {
    var dateParts = strDate.split(".");
    var composedDate = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

    return composedDate.getDate() == dateParts[0] &&
        composedDate.getMonth() == (dateParts[1] - 1) &&
        composedDate.getFullYear() == dateParts[2];
}
