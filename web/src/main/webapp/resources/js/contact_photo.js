var photo = document.getElementById('photo');
var photoPopup = document.getElementById('photo-popup');
var inputPhotoFile = document.getElementById("inputPhotoFile");
var btnSavePhoto = document.getElementById("btn-save-photo");
var btnUndoPhoto = document.getElementById("btn-undo-photo");
var btnLoadFile = document.getElementById("loadFile");


//By click on photo opening popup window
photo.onclick = function () {
    'use strict'
    photoPopup.className += " show";
}

btnSavePhoto.onclick = function () {
    'use strict'
    photoPopup.className = "popup";
}

btnUndoPhoto.onclick = function () {
    'use strict'
    inputPhotoFile.value = "";
    photoPopup.className = "popup";
}

btnLoadFile.onclick = function () {
    inputPhotoFile.click();
}

inputPhotoFile.onchange = function () {
    loadImg();
}
//----------------------------------------------------------------------

var photo = document.getElementById('photo');
var photoInput = document.getElementsByName("photoFile");
function loadImg() {
    'use strict'
    var reader = new FileReader();
    reader.readAsDataURL(photoInput[0].files[0]);
    reader.onload = function (e) {
        photo.setAttribute("src", e.target.result);

    }


}