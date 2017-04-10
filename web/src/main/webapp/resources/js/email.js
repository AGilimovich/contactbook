var form = document.getElementById("form");

var previousSelectedIndex = 0;

var btnSendEmail = document.getElementById("btn-send-email");
var inputSubject = document.getElementById("input-subject");
var inputEmailAddresses = document.getElementById("input-email-addresses");
var btnSubmitForm = document.getElementById("btn-submit-form");
var emailBodies = document.getElementsByName("email-body");
var templateSelect = document.getElementById("template-select");
inputEmailAddresses.oninvalid = function () {
    'use strict'
    inputEmailAddresses.setCustomValidity('Поле "Кому" не может быть пустым');
}
inputEmailAddresses.oninput = function () {
    'use strict'
    inputEmailAddresses.setCustomValidity('');
}

window.onload = function () {
    'use strict'
    if (!isBlank(inputEmailAddresses.value)) {
        emailBodies[0].oninvalid = function () {
            this.setCustomValidity('Поле "Текст" не может быть пустым');
        }
        emailBodies[0].oninput = function () {
            this.setCustomValidity('');
        }
    }
}

templateSelect.onchange = function () {
    'use strict'
    var previousTemplateBody = document.getElementById("email-body[" + previousSelectedIndex + "]");
    previousTemplateBody.className = "text-field white-space-pre hidden";
    previousTemplateBody.required = false;
    var newTemplateBody = emailBodies[templateSelect.selectedIndex];
    if (templateSelect.selectedIndex == 0) {
        newTemplateBody.required = true;
    }
    newTemplateBody.className = "white-space-pre text-field";
    previousSelectedIndex = templateSelect.selectedIndex;
}

btnSendEmail.onclick = function () {
    'use strict'
    var emailBody = document.getElementById("email-body[" + previousSelectedIndex + "]");
    if (isBlank(inputEmailAddresses.value)) {
        if (confirm('Адрес электронной почты не указан, поэтому письмо не будет отправлено')) {
            emailBodies[templateSelect.selectedIndex].required = false;
            btnSubmitForm.click();
        }
    }
    if (previousSelectedIndex === 0) {
        if (!isBlank(inputEmailAddresses.value) && !isBlank(emailBody.value)) {
            if (isBlank(inputSubject.value)) {
                if (confirm('Отправить это письмо без темы?')) {
                    btnSubmitForm.click();
                }
            } else {
                btnSubmitForm.click();
            }

        } else {
            btnSubmitForm.click();
        }
    } else {
        if (isBlank(inputSubject.value)) {
            if (confirm('Отправить это письмо без темы?')) {
                btnSubmitForm.click();
            }
        }
    }

}

function isBlank(str) {
    'use strict'
    return (!str || /^\s*$/.test(str));
}