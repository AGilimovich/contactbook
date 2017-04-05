var form = document.getElementById("form");

var previousSelectedIndex = 0;

function showTemplate(index) {
    'use strict'
    var previousTemplateBody = document.getElementById("email-body[" + previousSelectedIndex + "]");
    previousTemplateBody.className = "text-field white-space-pre hidden";
    previousTemplateBody.disabled = true;
    previousTemplateBody.required = false;
    var newTemplateBody = document.getElementById("email-body[" + index + "]");
    newTemplateBody.className = "white-space-pre text-field";
    newTemplateBody.value = newTemplateBody.innerHTML;
    newTemplateBody.disabled = false;
    previousTemplateBody.required = true;
    previousSelectedIndex = index;
}

var btnSendEmail = document.getElementById("btn-send-email");
var inputSubject = document.getElementById("input-subject");
var inputEmailAddresses = document.getElementById("input-email-addresses");
var btnSubmitForm = document.getElementById("btn-submit-form");
var emailBodies = document.getElementsByName("email-body");
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
    for (var i = 0; i < emailBodies.length; i++) {
        emailBodies[i].oninvalid = function () {
            this.setCustomValidity('Поле "Текст" не может быть пустым');
        }
        emailBodies[i].oninput = function () {
            
            this.setCustomValidity('');
        }
    }
}


btnSendEmail.onclick = function () {
    'use strict'
    var emailBody = document.getElementById("email-body[" + previousSelectedIndex + "]");

    if (isBlank(inputEmailAddresses.value) || isBlank(emailBody.value)) {
        btnSubmitForm.click();
        return;
    }

    if (isBlank(inputSubject.value)) {
        if (confirm('Отправить это письмо без темы?')) {
            btnSubmitForm.click();
        }
    }

}

function isBlank(str) {
    'use strict'
    return (!str || /^\s*$/.test(str));
}