var form = document.getElementById("form");
var bodyDiv = document.getElementById("body-div");
var bodyInput = document.getElementsByName("bodyInput");
var emailBody = document.getElementsByName("emailBody");



var previousSelectedIndex = 0;

function showTemplate(index) {
    var previousTemplateDiv = document.getElementById("body-div[" + previousSelectedIndex + "]");
    previousTemplateDiv.className = "text-field white-space-pre hidden";
    var newTemplateDiv = document.getElementById("body-div[" + index + "]");
    newTemplateDiv.className = "white-space-pre text-field";
    previousSelectedIndex = index;

    emailBody[0].setAttribute("value",newTemplateDiv.innerHTML);

}