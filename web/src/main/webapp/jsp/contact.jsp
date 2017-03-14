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
    <form action="${pageContext.request.contextPath}/${action}" method="post" class="form-horizontal">
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
                        <input type="text" name="name" value="${contact.name}" class="form-control">

                        <p>Фамилия:</p>
                        <input type="text" name="surname" value="${contact.surname}" class="form-control">

                        <p>Отчество:</p>
                        <input type="text" name="patronymic" value="${contact.patronymic}" class="form-control"
                        >

                        <p>Дата рождения:</p>
                        <input type="date" name="dateOfBirth" value="${contact.dateOfBirth}" class="form-control">

                        <p>Пол:</p>


                        <input type="radio" name="gender" value="male" ${contact.gender.name() =='MALE'?'checked':''} ${contact == null ?'checked':''}> м

                        <input type="radio" name="gender"
                               value="female" ${contact.gender.name() =='FEMALE'?'checked':''}> ж

                        <p>Гражданство:</p>
                        <input type="text" name="citizenship" value="${contact.citizenship}" class="form-control">

                        <p>Семейное положение:</p>

                        <div class="row">
                            <div class="col-md-1"><input type="radio" name="familyStatus"
                                                         value="married" ${contact.familyStatus.name() =='MARRIED'?'checked':''} ${contact == null ?'checked':''}>
                            </div>
                            <div class="col-md-4"><span>женат / замужем</span></div>
                            <div class="col-md-1"><input type="radio" name="familyStatus"
                                                         value="single" ${contact.familyStatus.name() =='SINGLE'?'checked':''} >
                            </div>
                            <div class="col-md-6">
                                <span>холост / не замужем</span>
                            </div>
                        </div>
                        <p>Веб-сайт:</p>
                        <input type="text" name="website" value="${contact.website}" class="form-control">

                        <p>Email:</p>
                        <input type="email" name="email" value="${contact.email}" class="form-control">

                        <p>Текущее место работы:</p>
                        <input type="text" name="placeOfWork" value="${contact.placeOfWork}" class="form-control">
                    </div>
                    <div class="col-md-6">
                        <h3>Адрес</h3>

                        <p>Страна:</p>
                        <input type="text" name="country" value="${address.country}" class="form-control">

                        <p>Город:</p>
                        <input type="text" name="city" value="${address.city}" class="form-control">

                        <p>Улица:</p>
                        <input type="text" name="street" value="${address.street}" class="form-control">

                        <p>Дом:</p>
                        <input type="text" name="house" value="${address.house}" class="form-control">

                        <p>Квартира:</p>
                        <input type="text" name="apartment" value="${address.apartment}" class="form-control">

                        <p>Почтовый индекс:</p>
                        <input type="text" name="zipCode" value="${address.zipCode}" class="form-control">
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
                            <td width="6%"><input type="checkbox" name="phoneIsSelected"></td>
                            <td width="20%">${phone.countryCode} (${phone.operatorCode}) ${phone.phoneNumber}</td>
                            <td align="center" width="20%">${phone.phoneType.name()}</td>
                            <td width="54%">${phone.comment}</td>
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
                            <td width="6%"><input type="checkbox" name="attachIsSelected"></td>
                            <td width="20%">${attachment.name}</td>

                            <td align="center" width="20%">
                                <fmt:formatDate value="${attachment.uploadDate}" var="formattedDate"
                                                type="date" pattern="MM-dd-yyyy HH:mm:ss"/>
                                    ${formattedDate}
                            </td>
                            <td width="54%">${attachment.comment}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <%--Buttons for form--%>
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
                <input type="tel" class="form-control" name="countryCode">

                <p>Код оператора:</p>
                <input type="tel" class="form-control" name="operatorCode">

                <p>Телефонный номер:</p>
                <input type="tel" class="form-control" name="phoneNumber">

                <input type="radio" name="phoneType" value="home"> Дом.
                <input type="radio" name="phoneType" value="mobile"> Моб.

                <p>Комментарий:</p>
                <input type="text" class="form-control" name="phoneComment">

                <div class="controls-group">
                    <button id="btn-save-phone" type="button" class="btn">Сохранить</button>
                    <button id="btn-undo-phone" class="btn">Отменить</button>
                </div>
            </div>
        </div>

        <%--Add file POPUP--%>
        <div id="attach-popup" class="popup">
            <div class="popup-content">

                <input type="file" name="file">

                <p>Имя файла:</p>
                <input type="text" class="form-control" name="fileName">

                <p>Комментарий:</p>
                <input type="text" class="form-control" name="attachComment">

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
                <input type="file" accept="image/*,image/jpeg" name="photoFile" class="form-control">

                <div class="row controls-group">
                    <button class="btn" type="button">Найти</button>
                    <button id="btn-save-photo" class="btn" type="button">Сохранить</button>
                    <button id="btn-undo-photo" class="btn">Отменить</button>
                </div>
            </div>
        </div>

    </form>
</div>


</body>
</html>