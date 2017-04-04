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
// hidden div with hidden phone inputs which then are submitted to server
var hiddenDiv = document.getElementById("hidden-div");

//table columns
//-------------------------------
//checkbox in table 
var phonesCheckBoxes = document.getElementsByName("phoneIsSelected");
var countryCode = document.getElementsByName("countryCode");
var operatorCode = document.getElementsByName("operatorCode");
var phoneNumber = document.getElementsByName("phoneNumber");
var phoneType = document.getElementsByName("phoneType");
var phoneComment = document.getElementsByName("phoneComment");
//-------------------------------
//tooltips
var deletePhoneTooltip = document.getElementById("delete-phone-tooltip");
var editPhoneTooltip = document.getElementById("edit-phone-tooltip");


// Elements on phone popup
//-------------------------------
var btnSavePhone = document.getElementById("btn-save-phone");
var btnUndoPhone = document.getElementById("btn-undo-phone");
var inputCountryCode = document.getElementsByName("inputCountryCode");
var inputOperatorCode = document.getElementsByName("inputOperatorCode");
var inputPhoneNumber = document.getElementsByName("inputPhoneNumber");
var inputPhoneTypes = document.getElementsByName("inputPhoneType");
var inputPhoneTypeMobile = document.getElementById("input-phone-type-mobile");
var inputPhoneTypeHome = document.getElementById("input-phone-type-home");
var inputPhoneComment = document.getElementsByName("inputPhoneComment");
//-------------------------------

var hiddenPhoneTable = document.getElementById("hidden-phone-table");

function Phone(id, countryCode, operatorCode, phoneNumber, phoneType, phoneComment, status) {
    'use strict'
    this.id = id;
    this.countryCode = countryCode;
    this.operatorCode = operatorCode;
    this.phoneNumber = phoneNumber;
    this.phoneType = phoneType;
    this.phoneComment = phoneComment;
    this.status = status;
    var hiddenInput;

    return {
        setId: function (id) {
            id = id;
        },
        setCountryCode: function (c) {
            countryCode = c;
        },
        setOperatorCode: function (c) {
            operatorCode = c;
        },
        setPhoneNumber: function (n) {
            phoneNumber = n;
        },
        setPhoneType: function (t) {
            phoneType = t;
        },
        setPhoneComment: function (c) {
            phoneComment = c;
        },
        setStatus: function (s) {
            status = s;
        },
        getId: function () {
            return id;
        },
        getCountryCode: function () {
            return countryCode;
        },
        getOperatorCode: function () {
            return operatorCode;
        },
        getPhoneNumber: function () {
            return phoneNumber;
        },
        getPhoneType: function () {
            return phoneType;
        },
        getPhoneComment: function () {
            return phoneComment;
        },
        getStatus: function () {
            return status;
        },
        setHiddenInput: function (i) {
            hiddenInput = i;
        },
        getHiddenInput: function () {
            return hiddenInput;
        }
    }

}

//function called when phone form submitted
var save;
// action - show phone creating popup window
btnAddPhone.onclick = function () {
    'use strict'
    //reset input values of popup form
    inputCountryCode[0].value = "";
    inputOperatorCode[0].value = "";
    inputPhoneNumber[0].value = "";
    inputPhoneComment[0].value = "";
    inputPhoneTypeHome.checked = true;

    save = function () {
        saveNew();
        return false;
    }
    phonePopup.className += " show";
}

//action - hide phone creating or editing popup
btnUndoPhone.onclick = function () {
    'use strict'
    phonePopup.className = "popup";
}


//action - delete selected phones
btnDeletePhones.onclick = function () {
    'use strict'
    var counter = 0;
    for (var i = 0; i < phonesCheckBoxes.length;) {
        if (phonesCheckBoxes[i].checked) {
            counter++;
            //todo popup acknowledge deleting
            // deleteHiddenInput(phonesCheckBoxes[i].value);
            var phone = phones[i];
            if (phone.getStatus() != STATUS.NEW) {
                phone.setStatus(STATUS.DELETED);
            }
            deleteHiddenInput(phone);
            phoneTable.deleteRow(i);
            phones.splice(i, 1);
            // i = 0;
            //todo deleting hidden inputs
        } else i++;
    }
    if (counter == 0) {
        deletePhoneTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            deletePhoneTooltip.className = "tooltiptext";
        }, 2000);
    }
}


function deleteHiddenInput(phone) {
    'use strict'
    var hiddenInput = phone.getHiddenInput();
    if (typeof hiddenInput != "undefined") {
        if (phone.getStatus() == STATUS.NEW) {
            hiddenInput.parentNode.removeChild(hiddenInput);
        } else {
            var value = new Appendable("id", phone.getId()).append("countryCode", phone.getCountryCode()).append("operatorCode", phone.getOperatorCode()).append("phoneNumber", phone.getPhoneNumber()).append("phoneType", phone.getPhoneType()).append("phone", phone.getPhoneComment()).append("status", phone.getStatus()).value();
            hiddenInput.setAttribute("value", value);
        }
    }

}


//action - show phone editing popup; fills inputs with values
btnEditPhone.onclick = function () {
    'use strict'
    //reload arrays containing phones data
    var phonesCheckBoxes = document.getElementsByName("phoneIsSelected");
    var countryCode = document.getElementsByName("countryCode");
    var operatorCode = document.getElementsByName("operatorCode");
    var phoneNumber = document.getElementsByName("phoneNumber");
    var phoneType = document.getElementsByName("phoneType");
    var phoneComment = document.getElementsByName("phoneComment");

    var countSelected = 0;
    var checkedIndex;
    //iterate through checkboxes to find checked one
    for (var i = 0; i < phonesCheckBoxes.length; i++) {
        if (phonesCheckBoxes[i].checked) {
            countSelected++;
            checkedIndex = i;
        }
    }
    //if no checked checkboxes
    if (countSelected == 0) {
        editPhoneTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            editPhoneTooltip.className = "tooltiptext";
        }, 2000);

        // if checked more than one checkbox
    } else if (countSelected > 1) {
        editPhoneTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            editPhoneTooltip.className = "tooltiptext";
        }, 2000);
    }
    else {

        //fill inputs with values
        inputCountryCode[0].value = deletePlus(countryCode[checkedIndex].innerHTML);
        inputOperatorCode[0].value = operatorCode[checkedIndex].innerHTML;
        inputPhoneNumber[0].value = phoneNumber[checkedIndex].innerHTML;
        if (phoneType[checkedIndex].innerHTML === "Мобильный") {
            inputPhoneTypeMobile.checked = true;
        } else inputPhoneTypeHome.checked = true;
        inputPhoneComment[0].value = phoneComment[checkedIndex].innerHTML;
        save = function () {
            editExisting(checkedIndex);
            return false;
        }
        phonePopup.className += " show";
    }
}

function deletePlus(text) {
    'use strict'
    return text.replace("+", "");
}

//function for creation of a new row in phone table and filling it with data
function createRow(table, phone) {
    'use strict'
    var rows = table.rows;
    var row = table.insertRow(-1);

    // insert cells into inserted row
    var cellCheckbox = row.insertCell(0);
    cellCheckbox.setAttribute("width", "6%")
    var cellCountryCode = row.insertCell(1);
    cellCountryCode.setAttribute("width", "5%")
    var cellOpenBracket = row.insertCell(2);
    cellOpenBracket.setAttribute("width", "1%")

    var cellOperatorCode = row.insertCell(3);
    cellOperatorCode.setAttribute("width", "3%")

    var cellCloseBracket = row.insertCell(4);
    cellCloseBracket.setAttribute("width", "1%")

    var cellPhoneNumber = row.insertCell(5);
    cellPhoneNumber.setAttribute("width", "10%")

    var cellPhoneType = row.insertCell(6);
    cellPhoneType.setAttribute("width", "20%")

    var cellPhoneComment = row.insertCell(7);
    cellPhoneComment.setAttribute("width", "54%")


    // ------------------add checkbox into cell[0]
    var checkbox = document.createElement("input");
    checkbox.setAttribute("type", "checkbox");
    cellCheckbox.appendChild(checkbox);
    checkbox.value = rows.length;
    checkbox.setAttribute("name", "phoneIsSelected");

    cellCountryCode.innerHTML = phone.getCountryCode();
    cellCountryCode.setAttribute("name", "countryCode");

    cellOpenBracket.innerHTML = "(";
    cellOpenBracket.setAttribute("align", "right");

    cellOperatorCode.innerHTML += phone.getOperatorCode();
    cellOperatorCode.setAttribute("name", "operatorCode");
    cellOperatorCode.setAttribute("align", "center");

    cellCloseBracket.innerHTML = ")";
    cellCloseBracket.setAttribute("align", "left");


    cellPhoneNumber.innerHTML = phone.getPhoneNumber();
    cellPhoneNumber.setAttribute("name", "phoneNumber");

    if (phone.getPhoneType() === "MOBILE") {
        cellPhoneType.innerHTML = "Мобильный";
    } else cellPhoneType.innerHTML = "Домашний";

    cellPhoneType.setAttribute("name", "phoneType");
    cellPhoneType.setAttribute("align", "center");

    cellPhoneComment.setAttribute("name", "phoneComment");
    cellPhoneComment.innerHTML = phone.getPhoneComment();

}

//object for appending parameters with its values to string of request.
// contains to methods: 1) append parameter to string;
//                      2)return result string
function Appendable(n, v) {
    'use strict'
    var val = n + "=" + v;
    this.append = function (name, value) {
        val += ("&" + name + "=" + value);
        return this;
    }
    this.value = function () {
        return val;
    }
}

//function for creating hidden input element
function createHiddenInputForPhone(phone) {
    'use strict'
    var value = new Appendable("id", phone.getId()).append("countryCode", phone.getCountryCode()).append("operatorCode", phone.getOperatorCode()).append("number", phone.getPhoneNumber()).append("type", phone.getPhoneType()).append("comment", phone.getPhoneComment()).append("status", phone.getStatus()).value();
    var phoneHiddenInput = document.createElement("input");
    phoneHiddenInput.setAttribute("name", "phone[" + phone.getId() + "]");
    phoneHiddenInput.setAttribute("value", value);
    phones.push(phone);
    hiddenPhoneTable.appendChild(phoneHiddenInput);
    return phoneHiddenInput;
}

//function for adding new phone to table and hidden input using data from inputs
function saveNew() {
    'use strict'
    //get data from inputs of popup window
    countryCode = "+" + inputCountryCode[0].value;
    operatorCode = inputOperatorCode[0].value;
    phoneNumber = inputPhoneNumber[0].value;
    if (inputPhoneTypes[1].checked) {
        phoneType = "MOBILE";
    } else phoneType = "HOME";
    phoneComment = inputPhoneComment[0].value;
    var newPhone = new Phone(new Date().getTime(), countryCode, operatorCode, phoneNumber, phoneType, phoneComment, STATUS.NEW);
    createRow(phoneTable, newPhone);
    var hiddenInput = createHiddenInputForPhone(newPhone);
    newPhone.setHiddenInput(hiddenInput);
    phones.push(hiddenInput);
    //close popup
    phonePopup.className = "popup";
}

//function for editing existing phone
function editExisting(index) {
    'use strict'
    var phone = phones[index];
    //edit data in the phone table
    var row = phoneTable.rows[index];
    var cellCountryCode = row.cells[1];
    var cellOperatorCode = row.cells[3];
    var cellPhoneNumber = row.cells[5];
    var cellPhoneType = row.cells[6];
    var cellPhoneComment = row.cells[7];
    phone.setCountryCode(cellCountryCode.innerHTML = "+" + inputCountryCode[0].value);
    phone.setOperatorCode(cellOperatorCode.innerHTML = inputOperatorCode[0].value);
    phone.setPhoneNumber(phoneNumber = cellPhoneNumber.innerHTML = inputPhoneNumber[0].value);

    if (inputPhoneTypes[1].checked) {
        phone.setPhoneType("MOBILE");
        cellPhoneType.innerHTML = "Мобильный";
    } else {
        phone.setPhoneType("HOME");
        cellPhoneType.innerHTML = "Домашний";
    }
    phone.setPhoneComment(cellPhoneComment.innerHTML = inputPhoneComment[0].value);
    //close popup
    phonePopup.className = "popup";
    if (phone.getStatus() != STATUS.NEW)
        phone.setStatus(STATUS.EDITED);
    editHiddenInput(phone);

}

//function for setting new value to hidden input
function editHiddenInput(phone) {
    'use strict'
    var hiddenInput = phone.getHiddenInput();
    var value = new Appendable("id", phone.getId()).append("countryCode", phone.getCountryCode()).append("operatorCode", phone.getOperatorCode()).append("number", phone.getPhoneNumber()).append("type", phone.getPhoneType()).append("comment", phone.getPhoneComment()).append("status", phone.getStatus()).value();
    hiddenInput.setAttribute("value", value);
}









