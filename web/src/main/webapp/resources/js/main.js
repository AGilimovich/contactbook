/**
 * Created by Aleksandr on 15.03.2017.
 */

var selectAllCheckbox = document.getElementById("selectAll");
var checkboxes = document.getElementsByName("isSelected");
var btnDelete = document.getElementById("btn-delete-contacts");
var btnSubmitDelete = document.getElementById("btn-submit-delete");
//tooltip
var deleteContactTooltip = document.getElementById("delete-contact-tooltip");


function selectAllContacts() {
    'use strict'
    if (selectAllCheckbox.checked == true) {
        for (var i = 0; i < checkboxes.length; i++)
            checkboxes[i].checked = true;
    } else {
        for (var i = 0; i < checkboxes.length; i++)
            checkboxes[i].checked = false;
    }
}

btnDelete.onclick = function () {
    'use strict'
    var count = 0;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            count++
        }
    }
    if (count === 0) {
        deleteContactTooltip.className = "tooltiptext show-tooltip";
        setTimeout(function () {
            deleteContactTooltip.className = "tooltiptext";
        }, 2000);
    } else {
        btnSubmitDelete.click();
    }
}

//--------------------------------------------
var btnSubmitCount = document.getElementById("btn-submit-item-count");
// var displayItemsSelector = document.getElementById("display-items");

var submitCount = function () {
    'use strict'
    btnSubmitCount.click();
}
