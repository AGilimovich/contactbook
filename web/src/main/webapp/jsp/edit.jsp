<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/contact.css">
    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <script src="../resources/js/contact.js" defer></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span href="" class="navbar-brand">> Создание/редактирование контакта</span>
        </div>
    </div>
</nav>


<div class="container-fluid">
    <form action="/contact" method="post" class="form-horizontal">
        <div class="row">

            <!--Photo-->
            <div class="col-md-1">
                <div class="row">
                    <div class="photo-container">
                        <img src="/resources/images/male.jpg" alt="photo" height="100%" id="photo">
                    </div>
                </div>
            </div>
            <!--Form-->
            <div class="col-md-4">
                <div class="row">
                    <div class="col-md-6">
                        <p>Имя:</p>
                        <input type="text" value="${contact.name}" class="form-control" required>

                        <p>Фамилия:</p>
                        <input type="text" value="${contact.surname}" class="form-control" required>

                        <p>Отчество:</p>
                        <input type="text" value="${contact.patronymic}" class="form-control" required>

                        <p>Дата рождения:</p>
                        <input type="date" value="${contact.dateOfBirth}" class="form-control" required>

                        <p>Пол:</p>
                        <input type="radio" name="gender" value="male" required> м
                        <input type="radio" name="gender" value="female"> ж

                        <p>Гражданство:</p>
                        <input type="text" value="${contact.citizenship}" class="form-control" required>

                        <p>Семейное положение:</p>

                        <div class="row">
                            <div class="col-md-1"><input type="radio" name="familyStatus" value="marries" required>
                            </div>
                            <div class="col-md-4">женат / замужем</div>
                            <div class="col-md-1"><input type="radio" name="familyStatus" value="single"></div>
                            <div class="col-md-6">
                                холост / не замужем
                            </div>
                        </div>
                        <p>Веб-сайт:</p>
                        <input type="text" value="${contact.website}" class="form-control">

                        <p>Email:</p>
                        <input type="email" value="${contact.email}" class="form-control" required>

                        <p>Текущее место работы:</p>
                        <input type="text" value="${contact.placeOfWork}" class="form-control">
                    </div>
                    <div class="col-md-6">
                        <h3>Адрес</h3>

                        <p>Страна:</p>
                        <input type="text" value="${contact.address.country}" class="form-control">

                        <p>Город:</p>
                        <input type="text" value="${contact.address.city}" class="form-control">

                        <p>Улица:</p>
                        <input type="text" value="${contact.address.street}" class="form-control">

                        <p>Дом:</p>
                        <input type="text" value="${contact.address.house}" class="form-control">

                        <p>Квартира:</p>
                        <input type="text" value="${contact.address.apartment}" class="form-control">
                    </div>
                </div>
            </div>

            <div class="col-md-7">
                <h3>Список контактных телефонов</h3>

                <div class="table-btns">
                    <button type="button" id="btn-delete-phone" class="btn">Удалить</button>
                    <button type="button" id="btn-add-phone" class="btn">Добавить</button>
                    <button type="button" id="btn-edit-phone" class="btn">Редактировать</button>

                </div>

                <table class="tbl" width="100%">
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td width="6%"><input type="checkbox"></td>
                            <td width="30%">${phone.countryCode} (${phone.operatorCode}) ${phone.phoneNumber}"</td>
                            <td align="center" width="14%">${phone.phoneType.name()}</td>
                            <td width="50%">${phone.comment}</td>
                        </tr>
                    </c:forEach>

                </table>


                <h3>Список присоединений</h3>
                <div class="table-btns">
                    <button type="button" id="btn-delete-attach" class="btn">Удалить</button>
                    <button type="button" id="btn-add-attach" class="btn">Добавить</button>
                    <button type="button" id="btn-edit-attach" class="btn">Редактировать</button>

                </div>
                <table class="tbl" width="100%">
                    <c:forEach var="attachment" items="${attachments}">
                        <tr>
                            <td width="6%"><input type="checkbox"></td>
                            <td width="30%">${attachment.name}</td>

                            <td align="center" width="14%">
                                <fmt:formatDate value="${attachment.uploadDate}" var="formattedDate"
                                                type="date" pattern="MM-dd-yyyy HH:mm:ss"/>
                                    ${formattedDate}
                            </td>
                            <td width="50%">${attachment.comment}</td>
                        </tr>
                    </c:forEach>

                </table>


            </div>


        </div>
        <div class="row">
            <div class="controls-group">
                <button class="btn" type="submit">Сохранить</button>
                <button type="button" class="btn">Отменить</button>
            </div>
        </div>
        <%--Add phone POPUP--%>
        <div id="phone-popup" class="popup">
            <div class="popup-content">
                <p>Код страны:</p>
                <input type="tel" required class="form-control">

                <p>Код оператора:</p>
                <input type="tel" required class="form-control">

                <p>Телефонный номер:</p>
                <input type="tel" required class="form-control">

                <input type="radio" name="type" value="home" required> Дом.
                <input type="radio" name="type" value="mobile"> Моб.

                <p>Комментарий:</p>
                <input type="text" class="form-control">

                <div class="controls-group">
                    <button id="btn-save-phone" type="button" class="btn">Сохранить</button>
                    <button id="btn-undo-phone" class="btn">Отменить</button>
                </div>
            </div>
        </div>

        <%--Add file POPUP--%>
        <div id="attach-popup" class="popup">
            <div class="popup-content">

                <input type="file" required>

                <p>Имя файла:</p>
                <input type="text" required class="form-control">

                <p>Комментарий:</p>
                <input type="text" class="form-control">

                <div class="row controls-group">
                    <button id="btn-save-attach" class="btn" type="button">Сохранить</button>
                    <button id="btn-undo-attach" class="btn">Отменить</button>
                </div>
            </div>
        </div>

        <%--Add photo POPUP--%>
        <div id="photo-popup" class="popup">
            <div class="popup-content">
                <p>Путь к картинке:</p>
                <input type="file" accept="image/*,image/jpeg" required class="form-control">

                <div class="row controls-group">
                    <button class="btn">Найти</button>
                    <button id="btn-save-photo" class="btn" type="button">Сохранить</button>
                    <button id="btn-undo-photo" class="btn">Отменить</button>
                </div>
            </div>
        </div>

    </form>
</div>


</body>
</html>