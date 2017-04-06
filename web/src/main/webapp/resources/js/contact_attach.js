//attach popup element
var attachPopup = document.getElementById('attach-popup');
//button - add new attachment
var btnAddAttach = document.getElementById("btn-add-attach");
//button - edit selected attachment
var btnEditAttach = document.getElementById("btn-edit-attach");
//button - delete selected attachments
var btnDeleteAttaches = document.getElementById("btn-delete-attach");
//table with attaches
var attachTable = document.getElementById("attach-table");
var anchorUndoAttach = document.getElementById("anchor-undo-attach");

// hidden div with attach meta data which then are submitted to server
var hiddenMetaDiv = document.getElementById("hidden-div");

//--------------------------------------------------------
//table columns
//-------------------------------
//checkbox in table representing selected attachments
var attachCheckBoxes = document.getElementsByName("attachIsSelected");
var uploadDate = document.getElementsByName("attachUploadDate");
var attachName = document.getElementsByName("attachName");
var attachComment = document.getElementsByName("attachComment");

//-------------------------------

//tooltips
var deleteAttachTooltip = document.getElementById("delete-attach-tooltip");
var editAttachTooltip = document.getElementById("edit-attach-tooltip");


// Elements on attach popup
//-------------------------------
var divInputFileContainer = document.getElementById("file-container");
var inputAttachName = document.getElementById("file-name-input");
var inputAttachComment = document.getElementsByName("inputAttachComment");
//-------------------------------


var attachments = [];

function findIndexOfAttachment(attachment) {
    'use strict'
    for (var i = 0; i < attachments.length; i++) {
        if (attachments[i] === attachment) return i;

    }

}


var phones = [];


var STATUS = {
    NONE: "NONE",
    NEW: "NEW",
    EDITED: "EDITED",
    DELETED: "DELETED"
}

//Create array of attachments received from server
window.onload = function () {
    'use strict'
    //create array of attachment objects
    if (typeof attachCheckBoxes !== "undefined" && attachCheckBoxes.length > 0) {
        //get attachments
        for (var i = 0; i < attachCheckBoxes.length; i++) {
            var extractor = new FileNameExtractor(attachName[i].innerText);
            var attachment = new Attachment(attachCheckBoxes[i].value, extractor.getName(), extractor.getExtension(), uploadDate[i].innerText, attachComment[i].innerText, STATUS.NONE);
            attachment.setAttachMetaInput(document.getElementsByName("attachMeta[" + i + "]")[0]);
            attachment.setAttachCheckBox(attachCheckBoxes[i]);
            attachments.push(attachment);
        }
    }
    //create array of phone object
    if (typeof phonesCheckBoxes !== "undefined" && phonesCheckBoxes.length > 0) {
        for (var i = 0; i < phonesCheckBoxes.length; i++) {
            var phoneTypeValue;
            if (phoneType[i].innerText === "Мобильный") {
                phoneTypeValue = "MOBILE";
            } else  phoneTypeValue = "HOME";
            var phone = new Phone(phonesCheckBoxes[i].value, countryCode[i].innerText, operatorCode[i].innerText, phoneNumber[i].innerText, phoneTypeValue, phoneComment[i].innerText, STATUS.NONE);
            var hiddenInput = document.getElementsByName("phone[" + i + "]")[0];
            phone.setHiddenInput(hiddenInput);
            phones.push(phone);
        }
    }
}

function Attachment(id, name, extension, uploadDate, comment, status) {
    'use strict'
    this.id = id;
    this.name = name;
    this.extension = extension;
    this.uploadDate = uploadDate;
    this.comment = comment;
    this.status = status;
    var attachMetaInput;
    var attachFileInput;
    var attachCheckBox;

    return {
        setAttachMetaInput: function (input) {
            attachMetaInput = input;
        },
        setAttachFileInput: function (input) {
            attachFileInput = input;
        },
        getAttachMetaInput: function () {
            return attachMetaInput;
        },
        getAttachFileInput: function () {
            return attachFileInput;
        },
        getId: function () {
            return id;
        },
        getName: function () {
            return name;
        },
        getExtension: function () {
            return extension;
        },
        getComment: function () {
            return comment;
        },
        getUploadDate: function () {
            return uploadDate;
        },
        getStatus: function () {
            return status;
        },
        getAttachCheckBox: function () {
            return attachCheckBox;
        },
        setName: function (n) {
            name = n;
        },
        setExtension: function (e) {
            extension = e;
        },
        setComment: function (c) {
            comment = c;
        },
        setStatus: function (s) {
            status = s;
        },
        setAttachCheckBox: function (checkBox) {
            attachCheckBox = checkBox;
        }


    }
}


//function called on submitting attachment form
var saveAttach;

btnAddAttach.onclick = function () {
    'use strict'
    var newInput = newAttachFileInput();
    newInput.required = true;
    //reset values of of popup form inputs
    inputAttachName.value = "";
    inputAttachComment[0].value = "";
    attachPopup.className += " show";
    inputAttachName.disabled = true;
    newInput.onchange = function () {
        inputAttachName.disabled = false;
        inputAttachName.value = new FileNameExtractor(newInput.files[0].name).getName();

    }
    saveAttach = function () {
        saveNewAttach(newInput);
        changeFormAttribute(newInput, "main-form");
        return false;
    }
    anchorUndoAttach.onclick = function () {
        cancelAttachCreation(newInput);
    }

}

btnDeleteAttaches.onclick = function () {
    'use strict'
    var counter = 0;
    for (var i = 0; i < attachCheckBoxes.length;) {
        if (attachCheckBoxes[i].checked) {
            counter++;
            var attachment = attachments[i];
            if (attachment.getStatus() != STATUS.NEW) {
                attachment.setStatus(STATUS.DELETED);
            }
            deleteAttachTableRow(i);
            deleteAttachMetaInput(attachment);
            deleteAttachFile(attachment);
            attachments.splice(i, 1);
        } else i++;
    }
    if (counter == 0) {
        deleteAttachTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            deleteAttachTooltip.className = "tooltiptext";
        }, 2000);
    }
}

btnEditAttach.onclick = function () {
    'use strict'
    var countSelected = 0;
    var checkedIndex;
    // iterate through checkboxes to find checked one
    for (var i = 0; i < attachCheckBoxes.length; i++) {
        if (attachCheckBoxes[i].checked) {
            countSelected++;
            checkedIndex = i;
        }
    }


    //if no checked checkboxes
    if (countSelected == 0) {
        editAttachTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            editAttachTooltip.className = "tooltiptext";
        }, 2000);
        // if checked more than one checkbox
    } else if (countSelected > 1) {
        editAttachTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            editAttachTooltip.className = "tooltiptext";
        }, 2000);
    }
    else {
        //fill inputs with values

        inputAttachName.value = new FileNameExtractor(attachName[checkedIndex].innerText).getName();
        inputAttachComment[0].value = attachComment[checkedIndex].innerText;
        var attachment = attachments[checkedIndex];
        var fileInput = attachment.getAttachFileInput();
        if (typeof fileInput !== "undefined") {
            fileInput.onchange = function () {
                inputAttachName.value = new FileNameExtractor(fileInput.files[0].name).getName();

            }
        }
        if (typeof fileInput !== "undefined")
            fileInput.className = "form-control";
        saveAttach = function () {
            editExistingAttach(attachment);
            return false;
        }
        anchorUndoAttach.onclick = function () {
            cancelAttachEditing(attachment);
        }
        attachPopup.className += " show";
    }


}

cancelAttachCreation = function (input) {
    'use strict'
    input.parentNode.removeChild(input);
    attachPopup.className = "popup";
}

cancelAttachEditing = function (attachment) {
    'use strict'
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined")
        fileInput.className = "hidden";
    attachPopup.className = "popup";
}

function saveNewAttach(input) {
    'use strict'
    var attachmentName = inputAttachName.value;
    var attachmentExtension = new FileNameExtractor(input.files[0].name).getExtension();
    // var attachmentUploadDate = dateToString(new Date());
    var attachmentUploadDate = "";
    var attachmentComment = inputAttachComment[0].value;
    var attachmentId = new Date().getTime();
    input.setAttribute("name", "attachFile[" + attachmentId + "]");
    var attachment = new Attachment(attachmentId, attachmentName, attachmentExtension, attachmentUploadDate, attachmentComment, STATUS.NEW);
    attachment.setAttachFileInput(input);
    newAttachTableRow(attachment);
    newAttachMetaInput(attachment);
    attachments.push(attachment);
    attachment.getAttachFileInput().className = "hidden";
    attachPopup.className = "popup";

}

function editExistingAttach(attachment) {
    'use strict'
    if (attachment.getStatus() !== STATUS.NEW)
        attachment.setStatus(STATUS.EDITED);
    var attachmentName = inputAttachName.value;
    var attachmentComment = inputAttachComment[0].value;
    attachment.setName(attachmentName);
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined") {
        attachment.setExtension(new FileNameExtractor(fileInput.files[0].name).getExtension());
        fileInput.className = "hidden";
    }
    attachment.setComment(attachmentComment);
    editAttachMetaInput(attachment);
    editAttachTableRow(attachment);


    attachPopup.className = "popup";
}

//--------------------------------------------------------

function newAttachTableRow(attachment) {
    'use strict'
    var rows = attachTable.rows;
    var row = attachTable.insertRow(-1);

    // insert cells into inserted row
    var cellCheckbox = row.insertCell(0);
    cellCheckbox.setAttribute("width", "6%")
    var cellName = row.insertCell(1);
    cellName.setAttribute("width", "20%")

    var cellUploadDate = row.insertCell(2);
    cellUploadDate.setAttribute("width", "20%")
    cellUploadDate.setAttribute("align", "center");
    var cellComment = row.insertCell(3);
    cellComment.setAttribute("width", "54%")

    // ------------------add checkbox into cell[0]
    var checkbox = document.createElement("input");
    checkbox.setAttribute("type", "checkbox");
    cellCheckbox.appendChild(checkbox);
    checkbox.value = rows.length;
    checkbox.setAttribute("name", "attachIsSelected");

    attachment.setAttachCheckBox(checkbox);

    cellName.setAttribute("name", "attachName");
    var fullName;
    attachment.getExtension() === '' ? fullName = attachment.getName() : fullName = attachment.getName() + "." + attachment.getExtension();
    cellName.innerText = fullName;

    // cellUploadDate.innerText = attachment.getUploadDate();
    cellUploadDate.setAttribute("name", "attachUploadDate");

    cellComment.innerText = attachment.getComment();
    cellComment.setAttribute("name", "attachComment");
}


function newAttachMetaInput(attachment) {
    'use strict'
    // var value = new Appendable("id", attachment.getId()).append("name", attachment.getName()+"."+attachment.getExtension()).append("uploadDate", attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
    var fullName;
    attachment.getExtension() === '' ? fullName = attachment.getName() : fullName = attachment.getName() + "." + attachment.getExtension();

    var value = new Appendable("id", attachment.getId()).append("name", fullName).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();

    var attachHiddenMetaInput = document.createElement("input");
    attachHiddenMetaInput.setAttribute("name", "attachMeta[" + attachment.getId() + "]");
    attachHiddenMetaInput.setAttribute("value", value);
    hiddenMetaDiv.appendChild(attachHiddenMetaInput);
    attachment.setAttachMetaInput(attachHiddenMetaInput);
}

function newAttachFileInput() {
    'use strict'
    var newInput = document.createElement("input");
    newInput.setAttribute("type", "file");
    newInput.className = "form-control";
    setValidationMessageToInput(newInput);
    //newInput.setAttribute("form", "main-form");
    divInputFileContainer.appendChild(newInput);
    return newInput;
}

function changeFormAttribute(input, newForm) {
    'use strict'
    input.setAttribute("form", newForm);

}

function editAttachTableRow(attachment) {
    'use strict'
    //edit data in the attach table
    var row = attachTable.rows[findIndexOfAttachment(attachment)];
    var cellAttachName = row.cells[1];
    var cellAttachUploadDate = row.cells[2];
    var cellAttachComment = row.cells[3];
    var fullName;
    attachment.getExtension() === '' ? fullName = attachment.getName() : fullName = attachment.getName() + "." + attachment.getExtension();
    if (cellAttachName.childNodes[0].nodeType === 1) {
        cellAttachName.childNodes[0].innerText = fullName;
    } else {
        cellAttachName.innerText = fullName;
    }
    // inputAttachName.value;
    cellAttachComment.innerText = inputAttachComment[0].value;
    cellAttachUploadDate.innerText;
}

function editAttachMetaInput(attachment) {
    'use strict'
    var fullName;
    attachment.getExtension() === '' ? fullName = attachment.getName() : fullName = attachment.getName() + "." + attachment.getExtension();
    var value = new Appendable("id", attachment.getId()).append("name", fullName).append("uploadDate", attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
    attachment.getAttachMetaInput().setAttribute("value", value);
}

function deleteAttachTableRow(row) {
    'use strict'
    attachTable.deleteRow(row);
}

function deleteAttachMetaInput(attachment) {
    'use strict'
    var input = attachment.getAttachMetaInput()
    if (typeof input != "undefined") {
        if (attachment.getStatus() == STATUS.NEW) {
            input.parentNode.removeChild(input);
        } else {
            var fullName;
            attachment.getExtension() === '' ? fullName = attachment.getName() : fullName = attachment.getName() + "." + attachment.getExtension();
            var value = new Appendable("id", attachment.getId()).append("name", fullName).append("uploadDate", attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
            attachment.getAttachMetaInput().setAttribute("value", value);
        }
    }

}

function deleteAttachFile(attachment) {
    'use strict'
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined")
        fileInput.parentNode.removeChild(attachment.getAttachFileInput());
}

//function for converting date to string in the format "dd.MM.yyyy hh:mm:ss"
function dateToString(date) {
    'use strict'
    //function adds zeros if value < 10
    function addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }

    return addZero(date.getDate()) + "." + (addZero(date.getMonth() + 1)) + "." + date.getFullYear() + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

var attachForm = document.getElementById("attachForm");
attachForm.onsubmit = function () {
    return saveAttach();
}


inputAttachName.oninvalid = function () {
    'use strict'
    inputAttachName.setCustomValidity('Имя не может быть пустым');
}
inputAttachName.oninput = function () {
    'use strict'
    inputAttachName.setCustomValidity('');
}


function setValidationMessageToInput(input) {
    'use strict'
    input.oninvalid = function () {
        input.setCustomValidity('Файл не выбран');
    };
    input.onchange = function () {
        input.setCustomValidity('');
        inputAttachName.setCustomValidity('');
    }
    input.onclick = function () {
        input.setCustomValidity('');
        inputAttachName.setCustomValidity('');
    }
}

function FileNameExtractor(fullName) {
    'use strict'
    this.fullName = fullName;
    var regex = /([^\/\\:&*"<>]+)\.([^\s\/\\:&*"<>]+)/;
    return {
        getName: function () {
            var match = regex.exec(fullName);
            if (match !== null)
                return match[1];
            else return fullName;
        },
        getExtension: function () {
            var match = regex.exec(fullName);
            if (match !== null)
                return match[2];
            else return '';
        }
    }
}



