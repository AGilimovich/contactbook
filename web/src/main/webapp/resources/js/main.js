/**
 * Created by Aleksandr on 15.03.2017.
 */
var displayItems = document.getElementsByName("display-items");

var rows = document.getElementsByClassName("contact-entry");
var displayItemsSelector = document.getElementById("display-items");
var pageItems = document.getElementsByClassName("page-item");
var pageItemNext = document.getElementsByClassName("page-item-next");
var pageItemPrev = document.getElementsByClassName("page-item-prev");
var selectAllCheckbox = document.getElementById("selectAll");
var checkboxes = document.getElementsByName("isSelected");
var btnDelete = document.getElementById("btn-delete-contacts");
var btnSubmitDelete = document.getElementById("btn-submit-delete");
//tooltip
var deleteContactTooltip = document.getElementById("delete-contact-tooltip");

var currentPage = 1;
var step = 10;
var lastPage = Math.ceil(rows.length / 10);


btnDelete.onclick = function () {
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


function selectAllContacts() {
    if (selectAllCheckbox.checked == true) {
        for (var i = step * (currentPage - 1); i < step * (currentPage - 1) + step; i++)
            checkboxes[i].checked = true;
    } else {
        for (var i = step * (currentPage - 1); i < step * (currentPage - 1) + step; i++)
            checkboxes[i].checked = false;
    }
}

function goToPage(page) {
    if (page <= lastPage) {

        for (var i = 0; i < rows.length; i++) {
            rows[i].className = "contact-entry hidden";
        }
        for (var i = step * (page - 1); i < step * (page - 1) + step; i++) {
            if (typeof rows[i] !== "undefined")
                rows[i].className = "contact-entry";
        }
        currentPage = page;
        if (currentPage === lastPage) {
            pageItemNext[0].className = "page-item-next hidden";
        } else {
            pageItemNext[0].className = "page-item-next";
        }
        if (currentPage === 1) {
            pageItemPrev[0].className = "page-item-prev hidden";
        } else {
            pageItemPrev[0].className = "page-item-prev";
        }
        for (var i = 0; i < pageItems.length; i++) {

            if (pageItems[i].className.indexOf("hidden") != -1) {
                pageItems[i].className == "page-item hidden";
            } else
                pageItems[i].className = "page-item";
        }
        pageItems[page - 1].className += " active";

    }
}

function changeDisplayingItemsCount() {
    step = displayItemsSelector.options[displayItemsSelector.selectedIndex].value;
    lastPage = Math.ceil(rows.length / step);
    for (var i = 0; i < pageItems.length; i++) {
        if (i < lastPage) {
            pageItems[i].className = "page-item";
        } else {
            pageItems[i].className = "page-item hidden";
        }
    }
    goToPage(1);
}

function goToPrevPage() {
    if (currentPage !== 1) {
        goToPage(currentPage - 1);

    }
}

function goToNextPage() {
    if (currentPage !== lastPage) {
        goToPage(currentPage + 1);

    }
}

