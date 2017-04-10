<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}" class="navbar-brand title">Справочник контактов</a>
        </div>
        <div id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/search"><span
                        class="glyphicon glyphicon-search"></span> Поиск
                    контактов</a></li>
                <li>
                    <a id="link-email">
                        <span class="glyphicon glyphicon-envelope"></span> Отправка Email
                    </a>
                </li>

            </ul>

        </div>
    </div>
</nav>


<div class="container-fluid content">
    <div class="row ${searchDTO==null? 'hidden':''}">
        <div class="col-md-3 well search-result">
            <h4>Параметры поиска:</h4>
            <p class="${searchDTO.name==null? 'hidden':''}">Имя = ${searchDTO.name}</p>
            <p class="${searchDTO.surname==null? 'hidden':''}">Фамилия = ${searchDTO.surname}</p>
            <p class="${searchDTO.patronymic==null? 'hidden':''}">Отчество = ${searchDTO.patronymic}</p>
            <fmt:formatDate value="${searchDTO.fromDate}"
                            var="formattedDateOfBirth"
                            type="date" pattern="dd.MM.yyyy"/>
            <p class="${searchDTO.fromDate==null? 'hidden':''}">Дата рождения >= ${formattedDateOfBirth}</p>
            <fmt:formatDate value="${searchDTO.toDate}"
                            var="formattedDateOfBirth"
                            type="date" pattern="dd.MM.yyyy"/>
            <p class="${searchDTO.toDate==null? 'hidden':''}">Дата рождения <= ${formattedDateOfBirth}</p>
            <p class="${searchDTO.gender==null? 'hidden':''}">Пол =
                <c:choose>
                    <c:when test="${searchDTO.gender.name().equals('MALE')}">
                        мужской
                    </c:when>
                    <c:when test="${searchDTO.gender.name().equals('FEMALE')}">
                        женский
                    </c:when>
                </c:choose>
            </p>
            <p class="${searchDTO.familyStatus==null? 'hidden':''}">Семейное положение =
                <c:choose>
                    <c:when test="${searchDTO.familyStatus.name().equals('MARRIED')}">
                        женат/замужем
                    </c:when>
                    <c:when test="${searchDTO.familyStatus.name().equals('SINGLE')}">
                        холост/не замужем
                    </c:when>
                </c:choose>
            </p>
            <p class="${searchDTO.citizenship==null? 'hidden':''}">Гражданство = ${searchDTO.citizenship}</p>
            <p class="${searchDTO.country==null? 'hidden':''}">Страна = ${searchDTO.country}</p>
            <p class="${searchDTO.city==null? 'hidden':''}">Город = ${searchDTO.city}</p>
            <p class="${searchDTO.street==null? 'hidden':''}">Улица = ${searchDTO.street}</p>
            <p class="${searchDTO.house==null? 'hidden':''}">Дом = ${searchDTO.house}</p>
            <p class="${searchDTO.apartment==null? 'hidden':''}">Квартира = ${searchDTO.apartment}</p>
            <p class="${searchDTO.zipCode==null? 'hidden':''}">Почтовый индекс = ${searchDTO.zipCode}</p>
        </div>
    </div>
    <form id="main-form" action="${pageContext.request.contextPath}/email">

        <div class="row">
            <div class="col-md-10 col-sm-10">
                <div class="row">
                    <table class="table">
                        <tr class="${contacts.size()>0 ? '':'hidden'}">
                            <td class="table-checkbox" width="5%" align="middle">
                                <input type="checkbox" id="selectAll">
                            </td>
                        </tr>
                        <c:forEach var="contact" items="${contacts}" varStatus="status">

                            <tr>
                                <td class="table-checkbox" width="5%" align="middle">
                                    <input type="checkbox" name="isSelected" value="${contact.contactId}">
                                </td>

                                <td width="230px" class="td-photo" valign="middle">
                                    <div class="photo-container">
                                        <img src="${pageContext.request.contextPath}/image?id=${contact.photo}"
                                             alt="photo" height="100%" class="photo">
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
                                        <div class="col-md-3 col-sm-3">
                                            <div class="row">
                                                <p><b>Дата рождения:</b></p>
                                            </div>
                                            <div class="row">
                                                <fmt:formatDate value="${contact.dateOfBirth}"
                                                                var="formattedDateOfBirth"
                                                                type="date" pattern="dd.MM.yyyy"/>
                                                <p>${formattedDateOfBirth}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-sm-3">
                                            <div class="row">
                                                <p><b>Домашний адрес:</b></p>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3 left-padding-0">
                                                    Страна:
                                                </div>
                                                <div class="col-md-9">
                                                        ${contact.country}
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3 left-padding-0">Город:</div>
                                                <div class="col-md-9">${contact.city}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3 left-padding-0">Улица:</div>
                                                <div class="col-md-9">${contact.street}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3 left-padding-0">Дом:</div>
                                                <div class="col-md-9">${contact.house}</div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3 left-padding-0">Квартира:</div>
                                                <div class="col-md-9"><p>${contact.apartment}</p></div>
                                            </div>

                                        </div>

                                        <div class="col-md-3 col-sm-6">
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
            </div>
            <div class="col-md-2 col-sm-2">
                <div class="btn-group-vertical navbar-right btn-container">
                    <button type="submit" formaction="${pageContext.request.contextPath}/add" formmethod="get"
                            class="btn btn-default">
                        Создать
                    </button>

                    <div class="tooltip-error">
                        <button type="button" id="btn-delete-contacts"
                                class="btn btn-default ${contacts.size()>0 ? '':'hidden'}">
                            Удалить
                        </button>
                        <span class="tooltiptext" id="delete-contact-tooltip">Выберите контакты</span>
                    </div>
                    <button type="submit" id="btn-submit-delete"
                            formaction="${pageContext.request.contextPath}/delete?pageNumber=0" formmethod="post"
                            class="hidden">
                    </button>
                </div>
            </div>

        </div>


        <div class="row">
            <div class="pages">

                <ul class="pagination">
                    <c:forEach begin="${pageNumber==0?1:pageNumber}"
                               end="${pageNumber==pagesCount-1?pageNumber+1:pageNumber+2}" varStatus="counter">
                        <li class="page ${counter.index == pageNumber+1 ? ' active':''} ${pagesCount == 0 ?'hidden':''}">
                            <a href="${pageContext.request.contextPath}?pageNumber=${counter.index-1}"> ${counter.index}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${pagesCount < 3 ?'hidden':''}">
                        <div id="page-list" class="page-list">
                            <c:forEach begin="1" end="${pagesCount}" varStatus="counter">
                                <a href="${pageContext.request.contextPath}?pageNumber=${counter.index-1}">${counter.index}</a>
                            </c:forEach>
                        </div>
                        <a id="anchor-select-page">...</a>

                    </li>


                </ul>
            </div>

            <div class="items-display ${contacts.size() == 0 ?'hidden':''}">
                <span class="display-items-span">Контактов на странице:</span>
                <select id="display-items" name="contactsOnPage" class="form-control">
                    <option value="10" ${contactsOnPage == 10 ? 'selected': ''}>10</option>
                    <option value="20" ${contactsOnPage == 20 ? 'selected': ''}>20</option>
                </select>
            </div>
            <input type="submit" id="btn-submit-item-count" form="main-form"
                   formaction="${pageContext.request.contextPath}/count" class="hidden">

        </div>
    </form>
</div>
<footer>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</footer>
</body>
</html>