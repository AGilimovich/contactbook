<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <span class="navbar-brand">Справочник контактов</span>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="${pageContext.request.contextPath}/search"><span
                        class="glyphicon glyphicon-search"></span> Поиск
                    контактов</a></li>
                <li><a href="${pageContext.request.contextPath}/email"><span
                        class="glyphicon glyphicon-envelope"></span> Отправка Email</a></li>

            </ul>

        </div>
    </div>
</nav>


<div class="container-fluid">
    <form action="">
        <div class="row">
            <button type="submit" formaction="contact/delete" formmethod="post" class="btn btn-danger btn-delete">
                Удалить
            </button>
            <button type="submit" formaction="contact/add" formmethod="get" class="btn btn-primary btn-add">
                Добавить <span
                    class="glyphicon glyphicon-plus"></span></button>

        </div>

        <div class="row">
            <table class="table">
                <c:forEach var="contact" items="${contacts}" varStatus="status">

                    <tr valign="middle">
                        <td id="table-checkbox" width="5%" align="middle">
                            <input type="checkbox" name="isSelected" value="${contact.contactId}">
                        </td>

                        <td width="110px">
                            <div class="photo-container">
                                    <%--<img src="/resources/images/male.jpg" alt="" height="100%" class="photo">--%>
                                    <img src=${contact.photo} alt="" height="100%" class="photo">

                            </div>
                        </td>
                        <td>
                            <p><a href="${pageContext.request.contextPath}/edit?id=${contact.contactId}"
                                  class="name">${contact.surname} ${contact.name} ${contact.patronymic}
                            </a>
                            </p>

                            <div class="row">
                                <div class="col-md-2">
                                    <p><b>Дата рождения:</b></p>
                                </div>
                                <div class="col-md-2">
                                    <p><b>Домашний адрес:</b></p>
                                </div>
                                <div class="col-md-2">
                                    <p><b>Место работы:</b></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-2">
                                        <%--<p>12.11.1990 г.</p>--%>
                                    <p>${contact.dateOfBirth}</p>
                                </div>
                                <div class="col-md-2">
                                    <p>${contact.country}, ул. ${contact.street},
                                        д.${contact.house}-${contact.apartment}</p>
                                </div>
                                <div class="col-md-2">
                                    <p>${contact.placeOfWork}</p>

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
                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <div class="items-display">
                    <span>Отображать контактов:</span>
                    <select name="display-items" class="form-control">
                        <option value="ten">10</option>
                        <option value="twenty">20</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>