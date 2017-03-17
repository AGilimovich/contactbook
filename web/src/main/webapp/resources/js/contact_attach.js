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

// hidden div with hidden attach inputs which then are submitted to server
var hiddenDiv = document.getElementById("hidden-div");

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
var btnSaveAttach = document.getElementById("btn-save-attach");
var btnUndoAttach = document.getElementById("btn-undo-attach");
var inputFile = document.getElementsByName("inputFile");
var inputAttachName = document.getElementsByName("inputAttachName");
var inputAttachComment = document.getElementsByName("inputAttachComment");

//-------------------------------

//represents mode: either editing existing attachment or adding new one. It determines behaviour of the Save button
const ATTACH_MODE = {
    EDIT: {name: "edit"},
    ADD: {name: "add"}
};
var currentMode;


// action - show attach creating popup window
btnAddAttach.onclick = function () {
    //reset input values of popup form
    // inputFile[0].value = "";
    inputAttachName[0].value = "";
    inputAttachComment[0].value = "";
    currentMode = ATTACH_MODE.ADD;
    attachPopup.className += " show";
}

//action - hide attach creating or editing popup
btnUndoAttach.onclick = function () {
    attachPopup.className = "popup";
}


//action - delete selected attachments
btnDeleteAttaches.onclick = function () {
    for (var i = 0; i < attachCheckBoxes.length;) {
        if (attachCheckBoxes[i].checked) {
            //todo popup acknowledge deleting
            deleteHiddenAttachInput(i);
            attachTable.deleteRow(i);
            i = 0;
            //todo deleting hidden inputs
        } else i++;
    }
}

//function for deleting hidden input with specified index
function deleteHiddenAttachInput(index) {
    var hiddenAttachInput = document.getElementsByName("attachment");
    hiddenAttachInput[index].parentNode.removeChild(hiddenAttachInput[index]);

}

//action - show attach editing popup; fills inputs with values
btnEditAttach.onclick = function () {

    //reload arrays containing attachments data
    var attachCheckBoxes = document.getElementsByName("attachIsSelected");
    var attachName = document.getElementsByName("attachName");
    var attachComment = document.getElementsByName("attachComment");

    currentMode = ATTACH_MODE.EDIT;

    var countSelected = 0;
    var checkedIndex;
    //iterate through checkboxes to find checked one
    for (var i = 0; i < attachCheckBoxes.length; i++) {
        if (attachCheckBoxes[i].checked) {
            countSelected++;
            currentMode.index = checkedIndex = i;
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
        inputAttachName[0].value = attachName[checkedIndex].innerHTML;
        inputAttachComment[0].value = attachComment[checkedIndex].innerHTML;


        attachPopup.className += " show";
    }
}


//function for creation of a new row in attachments table and filling it with data
function createAttachRow(table, attachName, attachDate, attachComment) {
    var rows = table.rows;
    var row = table.insertRow(rows);

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

    cellName.innerHTML = attachName;
    cellName.setAttribute("name", "attachName");


    cellUploadDate.innerHTML = attachDate;
    cellUploadDate.setAttribute("name", "attachUploadDate");


    cellComment.innerHTML = attachComment;
    cellComment.setAttribute("name", "attachComment");

}


//function for creating hidden input element
function createHiddenInputForAttach(attachFile, attachName, attachUploadDate, attachComment) {
    var value = new Appendable("file=" + attachFile).append("name", attachName).append("date", attachUploadDate).append("comment", attachComment).value();
    var attachHiddenInput = document.createElement("input");
    attachHiddenInput.setAttribute("name", "attachment");
    attachHiddenInput.setAttribute("value", value);
    hiddenDiv.appendChild(attachHiddenInput);
}

//utility function for converting date to string in the format "dd.MM.YYYY HH:mm:ss"
function dateToString(date) {
    //function adds zeros to hours, minutes or seconds if value < 10
    function addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }

    return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

//function for adding new attachment to table and hidden input using data from inputs
function saveNewAttach() {
    //get data from inputs of popup window
    var attachFile = inputFile[0].value;//todo how to get file input value&??
    attachName = inputAttachName[0].value;
    var attachUploadDate = dateToString(new Date());
    attachComment = inputAttachComment[0].value;

    createAttachRow(attachTable, attachName, attachUploadDate, attachComment);
    createHiddenInputForAttach(attachFile, attachName, attachUploadDate, attachComment);
    //close popup
    attachPopup.className = "popup";
}


//function for editing existing attach
function editExistingAttach() {
    //edit data in the attach table
    var row = attachTable.rows[currentMode.index];
    var cellAttachName = row.cells[1];
    var cellAttachUploadDate = row.cells[2];
    var cellAttachComment = row.cells[3];


    var attachName = cellAttachName.innerHTML = inputAttachName[0].value;
    var attachComment = cellAttachComment.innerHTML = inputAttachComment[0].value;
    var attachUploadDate = cellAttachUploadDate.innerHTML;
    //close popup
    attachPopup.className = "popup";
    editHiddenAttachInput(currentMode.index, attachUploadDate, attachName, attachComment);
}


//function for setting new value to the hidden input
function editHiddenAttachInput(index, attachUploadDate, attachName, attachComment) {
    var hiddenAttachInput = document.getElementsByName("attachment");
    //todo what about editing attach file and upload date
    var value = new Appendable("name=" + attachName).append(attachUploadDate).append("comment", attachComment).value();
    hiddenAttachInput[index].setAttribute("value", value);
}

//function - calls either saveNew or editExisting function
var saveAttach = function () {
    if (currentMode === ATTACH_MODE.ADD) {
        saveNewAttach();
    } else if (currentMode === ATTACH_MODE.EDIT) {
        editExistingAttach();
    } else {
        //error
    }
    return false;
}