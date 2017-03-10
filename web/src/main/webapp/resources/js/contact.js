var phonePopup = document.getElementById('phone-popup');

var btnAddPhone = document.getElementById("btn-add-phone");

var btnEditPhone = document.getElementById("btn-edit-phone");

var btnUndoPhone = document.getElementById("btn-undo-phone");

var btnSavePhone = document.getElementById("btn-save-phone");

btnAddPhone.onclick = function () {


    //phonePopup.style.display = "block";
    phonePopup.className += " show";
}

btnUndoPhone.onclick = function () {
    phonePopup.className = "popup";
}


var attachPopup = document.getElementById('attach-popup');

var btnAddAttach = document.getElementById("btn-add-attach");

var btnEditAttach = document.getElementById("btn-edit-attach");

var btnUndoAttach = document.getElementById("btn-undo-attach");

var btnSaveAttach = document.getElementById("btn-save-attach");

btnAddAttach.onclick = function (ev) {

    attachPopup.className += " show";
}

btnUndoAttach.onclick = function () {
    attachPopup.className = "popup";
}


var photo = document.getElementById('photo');
var photoPopup = document.getElementById('photo-popup');
var btnUndoPhoto = document.getElementById("btn-undo-photo");
photo.onclick = function () {
    photoPopup.className += " show";
}
btnUndoPhoto.onclick = function () {
    photoPopup.className = "popup";
}