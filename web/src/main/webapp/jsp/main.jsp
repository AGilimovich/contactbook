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
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="../${pageContext.request.contextPath}?search=false" class="navbar-brand">Справочник контактов</a>
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


<div class="container-fluid">
    <form id="main-form" action="${pageContext.request.contextPath}/email">
        <div class="row">

            <button type="submit" formaction="${pageContext.request.contextPath}/add" formmethod="get"
                    class="btn btn-primary btn-add">
                Добавить </button>

            <div class="tooltip-error">
                <button type="button" id="btn-delete-contacts" class="btn btn-danger btn-delete">
                    Удалить
                </button>
                <span class="tooltiptext" id="delete-contact-tooltip">Выберите контакты</span>
            </div>
            <button type="submit" id="btn-submit-delete"
                    formaction="${pageContext.request.contextPath}/delete" formmethod="post"
                    class="hidden">
            </button>


        </div>

        <div class="row">
            <table class="table">
                <tr>
                    <td class="table-checkbox" width="5%" align="middle">
                        <input type="checkbox" class="${contacts.size()>0 ? '':'hidden'}" id="selectAll">
                    </td>
                </tr>
                <c:forEach var="contact" items="${contacts}" varStatus="status">

                    <tr>
                        <td class="table-checkbox" width="5%" align="middle">
                            <input type="checkbox" name="isSelected" value="${contact.contactId}">
                        </td>

                        <td width="230px" class="td-photo">
                            <div class="photo-container">
                                <img src="${pageContext.request.contextPath}/image?id=${contact.photo}" alt="photo"
                                     height="100%" class="photo">
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
                                        <fmt:formatDate value="${contact.dateOfBirth}" var="formattedDateOfBirth"
                                                        type="date" pattern="dd.MM.yyyy"/>
                                        <p>${formattedDateOfBirth}</p>
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
            <div class="col-lg-10 col-md-9 col-sm-8 col-xs-6">
                <div class="pages">
                    <ul class="pagination">
                        <li class="page-item ${pageNumber == 0? ' hidden':''}">
                            <a href="../${pageContext.request.contextPath}?pageNumber=${pageNumber-1}&search=${isSearch}">Предыдущая</a>
                        </li>
                        <c:forEach begin="1" end="${pagesCount}" varStatus="counter">
                            <li class="page ${counter.index == pageNumber+1 ? ' active':''} ${pagesCount == 0 ?'hidden':''}">
                                <a href="../${pageContext.request.contextPath}?pageNumber=${counter.index-1}&search=${isSearch}"> ${counter.index}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${pageNumber == pagesCount-1 || pagesCount==0? ' hidden':''}">
                            <a href="../${pageContext.request.contextPath}?pageNumber=${pageNumber+1}&search=${isSearch}">Следующая</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-6">
                <div class="items-display">
                    <span class="display-items-span">Контактов на странице:</span>
                    <select id="display-items" name="contactsOnPage" class="form-control">
                        <option value="10" ${contactsOnPage == 10 ? 'selected': ''}>10</option>
                        <option value="20" ${contactsOnPage == 20 ? 'selected': ''}>20</option>
                    </select>
                </div>
                <input type="submit" id="btn-submit-item-count" form="main-form"
                       formaction="../${pageContext.request.contextPath}?search=${isSearch}" class="hidden">

            </div>
        </div>
    </form>
</div>
<footer>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</footer>
</body>
</html>