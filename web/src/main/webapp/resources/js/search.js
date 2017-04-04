var fromDateInput = document.getElementById("from-date");
var toDateInput = document.getElementById("to-date");

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