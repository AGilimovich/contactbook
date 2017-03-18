var form = document.getElementById("form");
var bodyDiv = document.getElementById("body-div");
var bodyInput = document.getElementsByName("bodyInput");

//copies data from div to input on submit
function moveDataToInput() {
    bodyInput[0].setAttribute("value", bodyDiv.innerHTML);
}
