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


// Elements on attach popup
//-------------------------------
var divInputFileContainer = document.getElementById("file-container");
var btnSaveAttach = document.getElementById("btn-save-attach");
var btnUndoAttach = document.getElementById("btn-undo-attach");
var inputFile = document.getElementsByName("attachFile");
var inputAttachName = document.getElementsByName("inputAttachName");
var inputAttachComment = document.getElementsByName("inputAttachComment");
//-------------------------------



var attachments = [];

function findIndexOfAttachment(attachment) {
    for (var i = 0; i < attachments.length; i++) {
        if (attachments[i] === attachment) return i;

    }

}


var STATUS = {
    NONE: "NONE",
    NEW: "NEW",
    EDITED: "EDITED",
    DELETED: "DELETED"
}

//Create array of attachments received from server
window.onload = function () {
    if (typeof attachCheckBoxes !== "undefined" && attachCheckBoxes.length > 0) {
        //get attachments
        for (var i = 0; i < attachCheckBoxes.length; i++) {
            var attachment = new Attachment(attachCheckBoxes[i].value, attachName[i].innerText, uploadDate[i].innerText, attachComment[i].innerText, STATUS.NONE);
            attachment.setAttachMetaInput(document.getElementsByName("attachMeta[" + i + "]")[0]);
            attachment.setAttachCheckBox(attachCheckBoxes[i]);
            attachments.push(attachment);

        }
    }
}

function Attachment(id, name, uploadDate, comment, status) {
    this.id = id;
    this.name = name;
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


function geIndexOfAttachment(attachment) {
    for (var i = 0; i < attachments.length; i++) {
        if (attachments[i].getId() === id) return attachments[i];
    }
    return null;
}


btnAddAttach.onclick = function () {
    var newInput = newAttachFileInput();
    //reset values of of popup form inputs
    inputAttachName[0].value = "";
    inputAttachComment[0].value = "";
    attachPopup.className += " show";
    btnSaveAttach.onclick = function () {
        saveNewAttach(newInput);
    }
    btnUndoAttach.onclick = function () {
        cancelAttachCreation(newInput);
    }

}

btnDeleteAttaches.onclick = function () {
    for (var i = 0; i < attachCheckBoxes.length;) {
        if (attachCheckBoxes[i].checked) {
            var attachment = attachments[i];
            //todo popup acknowledge deleting
            attachment.setStatus(STATUS.DELETED);
            deleteAttachTableRow(i);
            setDeleteAttachMetaInput(attachment);
            deleteAttachFile(attachment);
            attachments.splice(i, 1);
        } else i++;
    }
}

btnEditAttach.onclick = function () {

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
        //todo popup: select one item
        // if checked more than one checkbox
    } else if (countSelected > 1) {
        //todo popup: select one item
    }
    else {
        //fill inputs with values
        inputAttachName[0].value = attachName[checkedIndex].innerText;
        inputAttachComment[0].value = attachComment[checkedIndex].innerText;
        var attachment = attachments[checkedIndex];
        var fileInput = attachment.getAttachFileInput();
        if (typeof fileInput !== "undefined")
            fileInput.className = "";
        btnSaveAttach.onclick = function () {
            editExistingAttach(attachment);
        }
        btnUndoAttach.onclick = function () {
            cancelAttachEditing(attachment);
        }
        attachPopup.className += " show";
    }


}

cancelAttachCreation = function (input) {
    input.parentNode.removeChild(input);
    attachPopup.className = "popup";
}

cancelAttachEditing = function (attachment) {
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined")
        fileInput.className = "hidden";
    attachPopup.className = "popup";
}

function saveNewAttach(input) {
    var attachmentName = inputAttachName[0].value;
    var attachmentUploadDate = dateToString(new Date());
    var attachmentComment = inputAttachComment[0].value;
    var attachmentId = new Date().getTime();
    input.setAttribute("name", "attachFile[" + attachmentId + "]");
    var attachment = new Attachment(attachmentId, attachmentName, attachmentUploadDate, attachmentComment, STATUS.NEW);
    attachment.setAttachFileInput(input);
    newAttachTableRow(attachment);
    newAttachMetaInput(attachment);
    attachments.push(attachment);
    attachment.getAttachFileInput().className = "hidden";
    attachPopup.className = "popup";

}

function editExistingAttach(attachment) {
    attachment.setStatus(STATUS.EDITED);
    var attachmentName = inputAttachName[0].value;
    var attachmentComment = inputAttachComment[0].value;
    attachment.setName(attachmentName);
    attachment.setComment(attachmentComment);
    editAttachMetaInput(attachment);
    editAttachTableRow(attachment);
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined")
        fileInput.className = "hidden";
    attachPopup.className = "popup";
}

//--------------------------------------------------------

function newAttachTableRow(attachment) {
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
    // var attachmentLink = document.createElement("a");
    // attachmentLink.setAttribute("name", "attachLink");
    // cellName.appendChild(attachmentLink);
    cellName.innerText = attachment.getName();

    cellUploadDate.innerText = attachment.getUploadDate();
    cellUploadDate.setAttribute("name", "attachUploadDate");

    cellComment.innerText = attachment.getComment();
    cellComment.setAttribute("name", "attachComment");
}


function newAttachMetaInput(attachment) {
    var value = new Appendable("id", attachment.getId()).append("name", attachment.getName()).append("uploadDate", attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
    var attachHiddenMetaInput = document.createElement("input");
    attachHiddenMetaInput.setAttribute("name", "attachMeta[" + attachment.getId() + "]");
    attachHiddenMetaInput.setAttribute("value", value);
    hiddenMetaDiv.appendChild(attachHiddenMetaInput);
    attachment.setAttachMetaInput(attachHiddenMetaInput);
}

function newAttachFileInput() {
    var newInput = document.createElement("input");
    newInput.setAttribute("type", "file");
    newInput.setAttribute("form", "main-form");
    divInputFileContainer.appendChild(newInput);
    return newInput;
}

function editAttachTableRow(attachment) {
    //edit data in the attach table

    var row = attachTable.rows[findIndexOfAttachment(attachment)];
    var cellAttachName = row.cells[1];
    var cellAttachUploadDate = row.cells[2];
    var cellAttachComment = row.cells[3];

    cellAttachName.innerText = inputAttachName[0].value;
    cellAttachComment.innerText = inputAttachComment[0].value;
    cellAttachUploadDate.innerText;
}

function editAttachMetaInput(attachment) {
    var attachMetaInput = attachment.getAttachMetaInput();
    var value = new Appendable("id", attachment.getId()).append("name", attachment.getName()).append(attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
    attachment.getAttachMetaInput().setAttribute("value", value);
}

function deleteAttachTableRow(row) {
    attachTable.deleteRow(row);
}

function setDeleteAttachMetaInput(attachment) {
    // attachMetaInput.parentNode.removeChild(attachMetaInput);
    var value = new Appendable("id", attachment.getId()).append("name", attachment.getName()).append(attachment.getUploadDate()).append("comment", attachment.getComment()).append("status", attachment.getStatus()).value();
    attachment.getAttachMetaInput().setAttribute("value", value);
}

function deleteAttachFile(attachment) {
    var fileInput = attachment.getAttachFileInput();
    if (typeof fileInput !== "undefined")
        fileInput.parentNode.removeChild(attachment.getAttachFileInput());
}

//utility function for converting date to string in the format "dd.MM.YYYY HH:mm:ss"
function dateToString(date) {
    //function adds zeros if value < 10
    function addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }

    return addZero(date.getDate()) + "." + (addZero(date.getMonth() + 1)) + "." + date.getFullYear() + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}