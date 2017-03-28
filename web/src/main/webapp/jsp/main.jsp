<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
    <script src="${pageContext.request.contextPath}/resources/js/main.js" defer></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/search"><span
                        class="glyphicon glyphicon-search"></span> Поиск
                    контактов</a></li>
                <li>
                    <a href="javascript:{}" onclick="document.getElementById('main-form').submit();">
                        <span class="glyphicon glyphicon-envelope"></span> Отправка Email
                    </a>
                </li>

            </ul>

        </div>
    </div>
</nav>


<div class="container-fluid">
    <form id="main-form" action="${pageContext.request.contextPath}/email">
        <div class="row">

            <button type="submit" formaction="${pageContext.request.contextPath}/add" formmethod="get"
                    class="btn btn-primary btn-add">
                Добавить <span
                    class="glyphicon glyphicon-plus"></span></button>
            <button type="submit" formaction="${pageContext.request.contextPath}/delete" formmethod="post"
                    class="btn btn-danger btn-delete">
                Удалить
            </button>

        </div>

        <div class="row">
            <table class="table">
                <tr>
                    <td class="table-checkbox" width="5%" align="middle">
                        <input type="checkbox" id="selectAll" onchange="selectAllContacts()">
                    </td>
                </tr>
                <c:forEach var="contact" items="${contacts}" varStatus="status">

                    <tr valign="middle" class="${status.index<10? 'contact-entry':'contact-entry hidden'}">
                        <td class="table-checkbox" width="5%" align="middle">
                            <input type="checkbox" name="isSelected" value="${contact.contactId}">
                        </td>

                        <td width="150px">
                            <div class="photo-container">
                                <img src="${pageContext.request.contextPath}/image?id=${contact.photo}" onerror="this.onerror=null;this.src='imagefound.gif';" alt="" height="100%" class="photo">
                            </div>
                        </td>
                        <td>
                            <div class="row">
                                <p><a href="${pageContext.request.contextPath}/edit?id=${contact.contactId}"
                                      class="name">${contact.surname} ${contact.name} ${contact.patronymic}
                                </a>
                                </p>
                            </div>
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="row">
                                        <p><b>Дата рождения:</b></p>
                                    </div>
                                    <div class="row">
                                        <p>${contact.dateOfBirth}</p>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="row">
                                        <p><b>Домашний адрес:</b></p>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <div class="row">Страна:</div>
                                            <div class="row">Город:</div>
                                            <div class="row">Улица:</div>
                                            <div class="row">Дом:</div>
                                            <div class="row">Квартира:</div>
                                        </div>


                                        <div class="col-md-7">
                                            <div class="row">${contact.country}</div>
                                            <div class="row">${contact.city}</div>
                                            <div class="row">${contact.street}</div>
                                            <div class="row">${contact.house}</div>
                                            <div class="row">${contact.apartment}</div>

                                        </div>


                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="row">
                                        <p><b>Место работы:</b></p>
                                    </div>
                                    <div class="row">
                                        <p>${contact.placeOfWork}</p>

                                    </div>

                                </div>


                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="row">
            <div class="col-md-10">
                <div class="pages">
                    <ul class="pagination">
                        <li class="page-item-prev hidden"><a class="page-link" href="javascript:{}"
                                                             onclick="goToPrevPage();">Previous</a>
                        </li>
                        <c:forEach begin="1" end="${fn:length(contacts)}" varStatus="status" step="10">
                            <li class="page-item"><a class="page-link" href="javascript:{}"
                                                     onclick="goToPage(${status.count});">${status.count}</a></li>
                        </c:forEach>
                        <li class="${fn:length(contacts)>10? 'page-item-next': 'page-item-next hidden'}"><a
                                class="page-link" href="javascript:{}" onclick="goToNextPage();">Next</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <div class="items-display">
                    <span>Отображать контактов:</span>
                    <select id="display-items" class="form-control" onchange="changeDisplayingItemsCount()">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>

                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>