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
//checkbox in table representing selected phones
var phonesCheckBoxes = document.getElementsByName("phoneIsSelected");
var countryCode = document.getElementsByName("countryCode");
var operatorCode = document.getElementsByName("operatorCode");
var phoneNumber = document.getElementsByName("phoneNumber");
var phoneType = document.getElementsByName("phoneType");
var phoneComment = document.getElementsByName("phoneComment");
//-------------------------------


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





// action - show phone creating popup window
btnAddPhone.onclick = function () {
    //reset input values of popup form
    inputCountryCode[0].value = "+";
    inputOperatorCode[0].value = "";
    inputPhoneNumber[0].value = "";
    inputPhoneComment[0].value = "";
    inputPhoneTypeHome.checked = true;
    btnSavePhone.onclick = function () {
        saveNew();
    }
    phonePopup.className += " show";
}

//action - hide phone creating or editing popup
btnUndoPhone.onclick = function () {
    phonePopup.className = "popup";
}

//action - delete selected phones
btnDeletePhones.onclick = function () {
    for (var i = 0; i < phonesCheckBoxes.length;) {
        if (phonesCheckBoxes[i].checked) {
            //todo popup acknowledge deleting
            // deleteHiddenInput(phonesCheckBoxes[i].value);
            deleteHiddenInput(i);
            phoneTable.deleteRow(i);
            // i = 0;
            //todo deleting hidden inputs
        } else i++;
    }

}
//function for deleting hidden input with specified index
function deleteHiddenInput(index) {
    var id = phones[index].getId();
    var hiddenInput = document.getElementsByName("phone[" + id + "]");

    hiddenInput[0].parentNode.removeChild(hiddenInput[0]);

}


//action - show phone editing popup; fills inputs with values
btnEditPhone.onclick = function () {

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
        //todo popup: select one item
        // if checked more than one checkbox
    } else if (countSelected > 1) {
        //todo popup: select one item
    }
    else {

        //fill inputs with values
        inputCountryCode[0].value = countryCode[checkedIndex].innerHTML;
        inputOperatorCode[0].value = operatorCode[checkedIndex].innerHTML;
        inputPhoneNumber[0].value = phoneNumber[checkedIndex].innerHTML;
        if (phoneType[checkedIndex].innerHTML == "MOBILE") {
            inputPhoneTypeMobile.checked = true;
        } else inputPhoneTypeHome.checked = true;
        inputPhoneComment[0].value = phoneComment[checkedIndex].innerHTML;
        btnSavePhone.onclick = function () {
            editExisting(checkedIndex);
        }

        phonePopup.className += " show";
    }
}


//function for creation of a new row in phone table and filling it with data
function createRow(table, countryCode, operatorCode, phoneNumber, phoneType, phoneComment) {
    var rows = table.rows;
    var row = table.insertRow(rows);

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

    cellCountryCode.innerHTML = countryCode;
    cellCountryCode.setAttribute("name", "countryCode");

    cellOpenBracket.innerHTML = "(";
    cellOpenBracket.setAttribute("align", "right");

    cellOperatorCode.innerHTML += operatorCode;
    cellOperatorCode.setAttribute("name", "operatorCode");
    cellOperatorCode.setAttribute("align", "center");

    cellCloseBracket.innerHTML = ")";
    cellCloseBracket.setAttribute("align", "left");


    cellPhoneNumber.innerHTML = phoneNumber;
    cellPhoneNumber.setAttribute("name", "phoneNumber");


    cellPhoneType.innerHTML = phoneType;
    cellPhoneType.setAttribute("name", "phoneType");
    cellPhoneType.setAttribute("align", "center");

    cellPhoneComment.setAttribute("name", "phoneComment");
    cellPhoneComment.innerHTML = phoneComment;

}

//object for appending parameters with its values to string of request.
// contains to methods: 1) append parameter to string;
//                      2)return result string
function Appendable(n, v) {
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
function createHiddenInputForPhone(countryCode, operatorCode, phoneNumber, phoneType, phoneComment) {
    var value = new Appendable("countryCode", countryCode).append("operatorCode", operatorCode).append("number", phoneNumber).append("type", phoneType).append("comment", phoneComment).value();
    var phoneHiddenInput = document.createElement("input");
    var id = new Date().getTime();
    phones.push(new Phone(id));
    phoneHiddenInput.setAttribute("name", "phone[" + id + "]");
    phoneHiddenInput.setAttribute("value", value);
    hiddenDiv.appendChild(phoneHiddenInput);
}

//function for adding new phone to table and hidden input using data from inputs
function saveNew() {
    //get data from inputs of popup window
    countryCode = inputCountryCode[0].value;
    operatorCode = inputOperatorCode[0].value;
    phoneNumber = inputPhoneNumber[0].value;
    if (inputPhoneTypes[1].checked) {
        phoneType = "MOBILE";
    } else phoneType = "HOME";
    phoneComment = inputPhoneComment[0].value;
    createRow(phoneTable, countryCode, operatorCode, phoneNumber, phoneType, phoneComment);
    createHiddenInputForPhone(countryCode, operatorCode, phoneNumber, phoneType, phoneComment);
    //close popup
    phonePopup.className = "popup";
}

//function for editing existing phone
function editExisting(index) {
    //edit data in the phone table
    var row = phoneTable.rows[index];
    var cellCountryCode = row.cells[1];
    var cellOperatorCode = row.cells[3];
    var cellPhoneNumber = row.cells[5];
    var cellPhoneType = row.cells[6];
    var cellPhoneComment = row.cells[7];
    var countryCode = cellCountryCode.innerHTML = inputCountryCode[0].value;
    var operatorCode = cellOperatorCode.innerHTML = inputOperatorCode[0].value;
    var phoneNumber = cellPhoneNumber.innerHTML = inputPhoneNumber[0].value;

    if (inputPhoneTypes[1].checked) {
        var phoneType = cellPhoneType.innerHTML = "MOBILE";
    } else var phoneType = cellPhoneType.innerHTML = "HOME";
    var phoneComment = cellPhoneComment.innerHTML = inputPhoneComment[0].value;
    //close popup
    phonePopup.className = "popup";
    editHiddenInput(index, countryCode, operatorCode, phoneNumber, phoneType, phoneComment)
}

//function for setting new value to hidden input
function editHiddenInput(index, countryCode, operatorCode, phoneNumber, phoneType, phoneComment) {
    var id = phones[index].getId();
    var hiddenInput = document.getElementsByName("phone[" + id + "]");
    var value = new Appendable("countryCode", countryCode).append("operatorCode", operatorCode).append("number", phoneNumber).append("type", phoneType).append("comment", phoneComment).value();
    hiddenInput[0].setAttribute("value", value);
}









