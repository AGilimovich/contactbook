

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


