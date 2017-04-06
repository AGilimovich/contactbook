var photo = document.getElementById('photo');
var photoPopup = document.getElementById('photo-popup');
var inputPhotoFile = document.getElementById("inputPhotoFile");
var btnSavePhoto = document.getElementById("btn-save-photo");
var btnLoadFile = document.getElementById("loadFile");
var anchorUndoPhoto = document.getElementById("anchor-undo-photo");

//By click on photo opening popup window
photo.onclick = function () {
    'use strict'
    photoPopup.className += " show";
}

btnSavePhoto.onclick = function () {
    'use strict'
    // photo.setAttribute("src", image);
    photoPopup.className = "popup";
}
anchorUndoPhoto.onclick= function () {
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
        btnSavePhoto.onclick = function () {
            'use strict'
            photo.setAttribute("src", e.target.result);
            photoPopup.className = "popup";
        }

    }


}