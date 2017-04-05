var form = document.getElementById("form");

 var previousSelectedIndex = 0;

function showTemplate(index) {
    'use strict'
    var previousTemplateBody = document.getElementById("email-body[" + previousSelectedIndex + "]");
    previousTemplateBody.className = "text-field white-space-pre hidden";
    previousTemplateBody.disabled = true;
    var newTemplateDiv = document.getElementById("email-body[" + index + "]");
    newTemplateDiv.className = "white-space-pre text-field";
    newTemplateDiv.disabled = false;
    previousSelectedIndex = index;
}
