var form = document.getElementById("form");

 // var emailBodies = document.getElementsByName("email-body");



var previousSelectedIndex = 0;

function showTemplate(index) {
    var previousTemplateBody = document.getElementById("email-body[" + previousSelectedIndex + "]");
    previousTemplateBody.className = "text-field white-space-pre hidden";
    previousTemplateBody.disabled = true;
    var newTemplateDiv = document.getElementById("email-body[" + index + "]");
    newTemplateDiv.className = "white-space-pre text-field";
    newTemplateDiv.disabled = false;
    previousSelectedIndex = index;
}