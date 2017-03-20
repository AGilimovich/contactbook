<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/contact.css">
    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <script src="/resources/js/contact_phones.js" charset="utf-8" defer></script>
    <script src="/resources/js/attaches.js" charset="utf-8" defer></script>
    <script src="/resources/js/contact_photo.js" charset="utf-8" defer></script>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span class="navbar-brand">> Создание/редактирование контакта</span>
        </div>
    </div>
</nav>


<div class="container-fluid">
    <form id="main-form" action="${pageContext.request.contextPath}/${action}" method="post" enctype="multipart/form-data"
          accept-charset="UTF-8" class="form-horizontal">
        <div class="row">

            <!--Photo-->
            <div class="col-md-1">

                <div class="photo-container">
                    <img src="/file?id=${contact.photo}" height="100%" alt="photo" id="photo">
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


                        <input type="radio" name="gender"
                               value="male" ${contact.gender.name() =='MALE'?'checked':''} ${contact == null ?'checked':''}>
                        м

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
                            <div class="col-md-5">
                                холост / не замужем
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
                    <button type="button" id="btn-delete-phones" class="btn">Удалить</button>
                    <button type="button" id="btn-add-phone" class="btn">Добавить</button>
                    <button type="button" id="btn-edit-phone" class="btn">Редактировать</button>

                </div>

                <table id="phone-table" class="tbl" width="100%">
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td width="6%"><input type="checkbox" value="${phone.id}" name="phoneIsSelected"></td>
                            <td name="countryCode" width="5%">${phone.countryCode}</td>
                            <td width="1%" align="right">(</td>
                            <td name="operatorCode" width="3%" align="center">${phone.operatorCode}</td>
                            <td width="1%" align="left">)</td>
                            <td name="phoneNumber" width="10%">${phone.phoneNumber}</td>
                            <td name="phoneType" align="center" width="20%">${phone.phoneType.name()}</td>
                            <td name="phoneComment" width="54%">${phone.comment}</td>
                        </tr>
                    </c:forEach>

                </table>


                <%----------------------------------Attachments---------------------------------------%>
                <h3>Список присоединений</h3>
                <div class="table-btns">
                    <button type="button" id="btn-delete-attach" class="btn">Удалить</button>
                    <button type="button" id="btn-add-attach" class="btn">Добавить</button>
                    <button type="button" id="btn-edit-attach" class="btn">Редактировать</button>

                </div>
                <table id="attach-table" class="tbl" width="100%">
                    <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                        <tr>
                            <td width="6%"><input type="checkbox" name="attachIsSelected"></td>
                            <td width="20%" name="attachName"><a name="attachLink"
                                                                 href="/file?id=${attachment.file}">${attachment.name}</a>
                            </td>

                            <td align="center" width="20%" name="attachUploadDate">
                                <fmt:formatDate value="${attachment.uploadDate}" var="formattedDate"
                                                type="date" pattern="MM-dd-yyyy HH:mm:ss"/>
                                    ${formattedDate}
                            </td>
                            <td width="54%" name="attachComment">${attachment.comment}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <%--Buttons for form--%>
        <div class="row">
            <div class="controls-group">
                <button class="btn" type="submit">Сохранить</button>
                <a href="${pageContext.request.contextPath}/">
                    <button type="button" class="btn">Отменить</button>
                </a>
            </div>
        </div>


        <%---------------------------------hidden inputs------------------------------------%>
        <div id="hidden-div" class="hidden">
            <%--cloning visible phone table but with input instead of labels--%>
            <table id="hidden-table">
                <c:forEach var="phone" items="${phones}" varStatus="counter">
                    <input type="text" name="phone"
                           value="countryCode=${phone.countryCode}&operatorCode=${phone.operatorCode}&number=${phone.phoneNumber}&type=${phone.phoneType.name()}&comment=${phone.comment}">
                </c:forEach>
            </table>
            <%--hidden input for attachments metadata--%>
            <table id="attach-hidden-table">
                <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                    <fmt:formatDate value="${attachment.uploadDate}" var="formattedDate"
                                    type="date" pattern="MM.dd.yyyy HH:mm:ss"/>
                    <input type="text" name="attachMeta[${attachment.id}]"
                           value="id=${attachment.id}&name=${attachment.name}&uploadDate=${formattedDate}&comment=${attachment.comment}&status=NONE">
                </c:forEach>
            </table>
        </div>
        <%--------------------------------------------------------------------------------%>

        <%--Add photo POPUP--%>
        <div id="photo-popup" class="popup">
            <div class="popup-content">
                <p>Путь к картинке:</p>
                <%--<input type="file" accept="image/*,image/jpeg" name="photoFile" class="form-control">--%>
                <input type="file" name="photoFile" accept="image/jpeg,image/png,image/gif" class="form-control"
                       onchange="loadImg()">

                <div class="row controls-group">
                    <button class="btn" id="btn-find-photo" type="button">Найти</button>
                    <button id="btn-save-photo" class="btn" type="button">Сохранить</button>
                    <button id="btn-undo-photo" type="button" class="btn">Отменить</button>
                </div>
            </div>
        </div>

    </form>

    <%--Add phone POPUP--%>
    <div id="phone-popup" class="popup">
        <div class="popup-content">
            <form onsubmit="return savePhone()">
                <p>Код страны в формате +XXX:</p>
                <input type="tel" pattern="[\+]\d{3}" class="form-control" name="inputCountryCode" required>

                <p>Код оператора в формате XX:</p>
                <input type="tel" pattern="\d{2}" class="form-control" name="inputOperatorCode" required>

                <p>Телефонный номер в формате XXXXXXX:</p>
                <input type="tel" pattern="\d{7}" class="form-control" name="inputPhoneNumber" required>

                <input type="radio" id="input-phone-type-home" name="inputPhoneType" value="home" checked> Дом.
                <input type="radio" id="input-phone-type-mobile" name="inputPhoneType" value="mobile"> Моб.

                <p>Комментарий:</p>
                <input type="text" class="form-control" name="inputPhoneComment">

                <div class="controls-group">
                    <button id="btn-save-phone" type="submit" class="btn">Сохранить</button>
                    <button id="btn-undo-phone" type="button" class="btn">Отменить</button>
                </div>
            </form>
        </div>
    </div>

    <%--Add attach POPUP--%>
    <div id="attach-popup" class="popup">
        <div class="popup-content">
            <form onsubmit="return saveAttach()">
                <div id="file-container">
                    <%--<input type="file" name="attachFile" class="" form="main-form">--%>
                </div>
                <p>Имя файла:</p>
                <input type="text" class="form-control" name="inputAttachName">

                <p>Комментарий:</p>
                <input type="text" class="form-control" name="inputAttachComment">

                <div class="row controls-group">
                    <button id="btn-save-attach" class="btn" type="button">Сохранить</button>
                    <button id="btn-undo-attach" class="btn" type="button">Отменить</button>
                </div>
            </form>
        </div>
    </div>


</div>


</body>
</html>