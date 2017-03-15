//ATTACHES
//--------------------------------------------------------
var attachPopup = document.getElementById('attach-popup');

var btnAddAttach = document.getElementById("btn-add-attach");

var btnEditAttach = document.getElementById("btn-edit-attach");

var btnUndoAttach = document.getElementById("btn-undo-attach");

var btnSaveAttach = document.getElementById("btn-save-attach");

btnAddAttach.onclick = function () {

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

//----------------------------------------------------------------------

