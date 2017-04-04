var photo = document.getElementById('photo');
var photoPopup = document.getElementById('photo-popup');
var inputPhotoFile = document.getElementsByName("photoFile");
var btnFindPhoto = document.getElementById("btn-find-photo");
var btnSavePhoto = document.getElementById("btn-save-photo");
var btnUndoPhoto = document.getElementById("btn-undo-photo");

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
    inputPhotoFile[0].value = "";
    photoPopup.className = "popup";
}


//----------------------------------------------------------------------

var photo = document.getElementById('photo');
var photoInput= document.getElementsByName("photoFile");
function loadImg(){
    'use strict'
    var reader = new FileReader();
    reader.readAsDataURL(photoInput[0].files[0]);
    reader.onload = function (e) {
        photo.setAttribute("src", e.target.result);

    }


}