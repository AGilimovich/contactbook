// phones popup element
var phonePopup = document.getElementById('phone-popup');
//button - add phone
var btnAddPhone = document.getElementById("btn-add-phone");
//button - edit selected phone
var btnEditPhone = document.getElementById("btn-edit-phone");
//button - delete selected phones
var btnDeletePhones = document.getElementById("btn-delete-phones");
//table with phones
var phoneTable = document.getElementById("phone-table");
// hidden table with phones
var hiddenDiv = document.getElementById("hidden-div");

//table columns
//-------------------------------
//checkbox in table representing selected phones
var phonesCheckBoxes = document.getElementsByName("phoneIsSelected");
var countryCode = document.getElementsByName("country-code");
var operatorCode = document.getElementsByName("operator-code");
var phoneNumber = document.getElementsByName("phone-number");
var phoneType = document.getElementsByName("phone-type");
var phoneComment = document.getElementsByName("phone-comment");
//-------------------------------


// Elements on phone popup
//-------------------------------
var btnSavePhone = document.getElementById("btn-save-phone");
var btnUndoPhone = document.getElementById("btn-undo-phone");
var inputCountryCode = document.getElementsByName("input-country-code");
var inputOperatorCode = document.getElementsByName("input-operator-code");
var inputPhoneNumber = document.getElementsByName("input-phone-number");
var inputPhoneTypes = document.getElementsByName("input-phone-type");
var inputPhoneTypeMobile = document.getElementById("input-phone-type-mobile");
var inputPhoneTypeHome = document.getElementById("input-phone-type-home");
var inputPhoneComment = document.getElementsByName("input-phone-comment");
//-------------------------------

//hidden inputs
//-------------------------------


//-------------------------------

//Variable represents mode: either editing existing phone or adding new one. It influences behaviour of the Save button
const MODE = {
    EDIT: {name: "edit"},
    ADD: {name: "add"}
};
var currentMode;
var lastGeneratedPhoneId = {type: "generated", value: 0};

// action - show phone creating popup
btnAddPhone.onclick = function () {
    //reset input values of popup form
    inputCountryCode[0].value = "+";
    inputOperatorCode[0].value = "";
    inputPhoneNumber[0].value = "";
    inputPhoneComment[0].value = "";
    inputPhoneTypeHome.checked = true;
    currentMode = MODE.ADD;
    phonePopup.className += " show";
}

//action - hide phone editing popup
btnUndoPhone.onclick = function () {
    phonePopup.className = "popup";
}

//action - delete selected phones
btnDeletePhones.onclick = function () {
    var phoneId = document.getElementsByName("phoneId");
    for (var i = 0; i < phonesCheckBoxes.length; i++) {
        if (phonesCheckBoxes[i].checked) {
            //todo popup acknowledge deleting
            phoneTable.deleteRow(i);
        }
    }
}

//action - show phone editing popup; fills inputs with values
btnEditPhone.onclick = function () {
    // phonesCheckBoxes = document.getElementsByName("phoneIsSelected");
    currentMode = MODE.EDIT;

    var countSelected = 0;
    var checkedIndex;
    //iterate through checkboxes to find checked one
    for (var i = 0; i < phonesCheckBoxes.length; i++) {
        if (phonesCheckBoxes[i].checked) {
            countSelected++;
            checkedIndex = i;
            currentMode.id = checkedIndex;
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
        if (checkedIndex !== undefined) {
            //fill inputs with values
            inputCountryCode[0].value = countryCode[checkedIndex].innerHTML;
            inputOperatorCode[0].value = operatorCode[checkedIndex].innerHTML;
            inputPhoneNumber[0].value = phoneNumber[checkedIndex].innerHTML;
            if (phoneType[checkedIndex].innerHTML == "MOBILE") {
                inputPhoneTypeMobile.checked = true;
            } else inputPhoneTypeHome.checked = true;
            inputPhoneComment[0].value = phoneComment[checkedIndex].innerHTML;

            phonePopup.className += " show";
        }
    }


}


//utility function for auto-generating id's
function generatePhoneId() {
    lastGeneratedPhoneId.value++;
    return lastGeneratedPhoneId;

}

//function for creation of a new row in phone table and filling it with data
function createRow(table, id, countryCode, operatorCode, phoneNumber, phoneType, phoneComment) {
    var rows = table.rows;
    var row = table.insertRow(rows);
    // insert cells into inserted row
    var cellCheckbox = row.insertCell(0);
    var cellFullPhone = row.insertCell(1);
    var cellPhoneType = row.insertCell(2);
    cellPhoneType.setAttribute("align", "center");
    var cellPhoneComment = row.insertCell(3);
    // add checkbox into cell[0]
    var checkbox = document.createElement("input");
    checkbox.setAttribute("type", "checkbox");
    cellCheckbox.appendChild(checkbox);
    //add value to checkbox, format: g1, g2, g3...
    checkbox.value = id;
    checkbox.setAttribute("name", "phoneIsSelected");
    //add span countryCode
    var spanCountryCode = document.createElement('span');
    cellFullPhone.appendChild(spanCountryCode);
    spanCountryCode.setAttribute("name", "country-code");
    spanCountryCode.innerHTML = countryCode;
    cellFullPhone.innerHTML += " (";
    //add span operatorCode
    var spanOperatorCode = document.createElement('span');
    cellFullPhone.appendChild(spanOperatorCode);
    spanOperatorCode.setAttribute("name", "operator-code");
    spanOperatorCode.innerHTML = operatorCode;
    cellFullPhone.innerHTML += ") ";
    //add span phoneNumber
    var spanPhoneNumber = document.createElement('span');
    cellFullPhone.appendChild(spanPhoneNumber);
    spanPhoneNumber.setAttribute("name", "phone-number");
    spanPhoneNumber.innerHTML = phoneNumber;
    //setting property name of phone type column
    cellPhoneType.setAttribute("name", "phone-type");
    //add phone type description into cell[2]
    cellPhoneType.innerHTML = phoneType;
    //add comment into cell[3]
    cellPhoneComment.setAttribute("name", "phone-comment");
    cellPhoneComment.innerHTML = phoneComment;
}


//function for creating hidden inputs
function createHiddenInputsForPhone(id, countryCode, operatorCode, phoneNumber, phoneType, phoneComment) {

    var countryCodeHiddenInput = document.createElement("input");
    countryCodeHiddenInput.setAttribute("name", "countryCode" + "(" + id + ")");
    countryCodeHiddenInput.setAttribute("value", countryCode);

    var operatorCodeHiddenInput = document.createElement("input");
    operatorCodeHiddenInput.setAttribute("name", "operatorCode" + "(" + id + ")");
    operatorCodeHiddenInput.setAttribute("value", operatorCode);

    var phoneNumberHiddenInput = document.createElement("input");
    phoneNumberHiddenInput.setAttribute("name", "phoneNumber" + "(" + id + ")");
    phoneNumberHiddenInput.setAttribute("value", phoneNumber);

    var phoneTypeHiddenInput = document.createElement("input");
    phoneTypeHiddenInput.setAttribute("name", "phoneType" + "(" + id + ")");
    phoneTypeHiddenInput.setAttribute("value", phoneType);

    var phoneCommentHiddenInput = document.createElement("input");
    phoneCommentHiddenInput.setAttribute("name", "phoneComment" + "(" + id + ")");
    phoneCommentHiddenInput.setAttribute("value", phoneComment);

    hiddenDiv.appendChild(countryCodeHiddenInput);
    hiddenDiv.appendChild(operatorCodeHiddenInput);
    hiddenDiv.appendChild(phoneNumberHiddenInput);
    hiddenDiv.appendChild(phoneTypeHiddenInput);
    hiddenDiv.appendChild(phoneCommentHiddenInput);


}

//function for adding new phone to table using data from inputs
function saveNew() {
    //insert row into table
    var id = "g" + generatePhoneId().value;
    countryCode = inputCountryCode[0].value;
    operatorCode = inputOperatorCode[0].value;
    phoneNumber = inputPhoneNumber[0].value;
    if (inputPhoneTypes[1].checked) {
        phoneType = "MOBILE";
    } else phoneType = "HOME";

    phoneComment = inputPhoneComment[0].value;
    createRow(phoneTable, id, countryCode, operatorCode, phoneNumber, phoneType, phoneComment);
    createHiddenInputsForPhone(id, countryCode, operatorCode, phoneNumber, phoneType, phoneComment);
    //close popup
    phonePopup.className = "popup";
}

//function for editing existing phone
function editExisting() {
    var row = phoneTable.rows[currentMode.id];
    // insert cells into inserted row
    var cellFullPhone = row.cells[1];
    var cellPhoneType = row.cells[2];
    var cellPhoneComment = row.cells[3];

    cellFullPhone.innerHTML = inputCountryCode[0].value + " (" + inputOperatorCode[0].value + ") " + inputPhoneNumber[0].value;
    //add phone type description into cell[2]
    if (inputPhoneTypes[1].checked) {
        cellPhoneType.innerHTML = "MOBILE";
    } else cellPhoneType.innerHTML = "HOME";
    //add comment into cell[3]
    cellPhoneComment.innerHTML = inputPhoneComment[0].value;
    //close popup
    phonePopup.className = "popup";
    //todo create hidden inputs to submit data

}


//Save inserted data about phone
//callback function - either saveNew or editExisting function
btnSavePhone.onclick = function () {
    if (currentMode === MODE.ADD) {
        saveNew();
    } else if (currentMode === MODE.EDIT) {
        editExisting();
    } else {
        //error
    }
}








