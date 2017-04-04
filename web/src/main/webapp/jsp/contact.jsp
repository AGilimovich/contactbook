<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/contact.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">


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
    <form id="main-form" action="${pageContext.request.contextPath}/${action}" method="post"
          enctype="multipart/form-data"
          accept-charset="UTF-8" class="form-horizontal">
        <div class="row">

            <!--Photo-->
            <div class="col-md-2">

                <div class="photo-container">
                    <img src="${pageContext.request.contextPath}/image?id=${photo.storedName}" height="100%" alt="photo"
                         id="photo">
                </div>

            </div>
            <!--Form-->
            <div class="col-md-4">
                <div class="row">
                    <div class="col-md-6">

                        <p class="form-text"><span>Имя</span><span class="red-text">*</span>:</p>
                        <input id="name-input" type="text" name="name" value="${contact.name}" class="form-control" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" required>


                        <p class="form-text"><span>Фамилия</span><span class="red-text">*</span>:</p>
                        <input id="surname-input" type="text" name="surname" value="${contact.surname}" class="form-control" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" required>

                        <p class="notification">* Поля, обязательные для заполнения</p>

                        <p class="form-text">Отчество:</p>
                        <input id="patronymic-input" type="text" name="patronymic" value="${contact.patronymic}" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" class="form-control">


                        <p class="form-text">Дата рождения:</p>
                        <fmt:formatDate value="${contact.dateOfBirth}" var="formattedDateOfBirth"
                                        type="date" pattern="dd.MM.yyyy"/>
                        <input id="date-input" type="text" name="dateOfBirth" value="${formattedDateOfBirth}" placeholder="ДД.ММ.ГГГГ"
                               pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" class="form-control">


                        <p class="form-text">Пол:</p>


                        <input type="radio" name="gender"
                               value="male" ${contact.gender.name() =='MALE'?'checked':''} ${contact == null ?'checked':''}>
                        м

                        <input type="radio" name="gender"
                               value="female" ${contact.gender.name() =='FEMALE'?'checked':''}> ж

                        <p class="form-text">Гражданство:</p>
                        <input type="text" name="citizenship" value="${contact.citizenship}" class="form-control">

                        <p class="form-text">Семейное положение:</p>

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
                        <p class="form-text">Веб-сайт:</p>
                        <input type="text" name="website" value="${contact.website}" class="form-control">

                        <p class="form-text">Email:</p>
                        <input id="email-input" type="email" name="email" value="${contact.email}" class="form-control">

                        <p class="form-text">Текущее место работы:</p>
                        <input type="text" name="placeOfWork" value="${contact.placeOfWork}" class="form-control">
                    </div>
                    <div class="col-md-6">
                        <h3>Адрес</h3>

                        <p class="form-text">Страна:</p>
                        <input type="text" name="country" value="${address.country}" class="form-control">

                        <p class="form-text">Город:</p>
                        <input type="text" name="city" value="${address.city}" class="form-control">

                        <p class="form-text">Улица:</p>
                        <input type="text" name="street" value="${address.street}" class="form-control">

                        <p class="form-text">Дом:</p>
                        <input type="text" name="house" value="${address.house}" class="form-control">

                        <p class="form-text">Квартира:</p>
                        <input type="text" name="apartment" value="${address.apartment}" class="form-control">

                        <p class="form-text">Почтовый индекс:</p>
                        <input type="text" name="zipCode" value="${address.zipCode}" class="form-control">
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <h3>Список контактных телефонов</h3>

                <div class="table-btns">
                    <div class="tooltip-error">
                        <button type="button" id="btn-delete-phones" class="btn">Удалить</button>
                        <span class="tooltiptext" id="delete-phone-tooltip">Выберите телефоны</span>
                    </div>

                    <button type="button" id="btn-add-phone" class="btn">Добавить</button>
                    <div class="tooltip-error">
                        <button type="button" id="btn-edit-phone" class="btn">Редактировать</button>
                        <span class="tooltiptext" id="edit-phone-tooltip">Выберите один телефон</span>
                    </div>
                </div>


                <table id="phone-table" class="tbl" width="100%">
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td width="6%"><input type="checkbox" value="${phone.id}" name="phoneIsSelected"></td>
                            <td name="countryCode" width="5%">${phone.countryCode}</td>
                            <td width="1%" align="right">(</td>
                            <td name="operatorCode" width="3%" align="center">${phone.operatorCode}</td>
                            <td width="1%" align="left">)</td>
                            <td name="phoneNumber" width="14%">${phone.phoneNumber}</td>
                            <td name="phoneType" align="center" width="20%">${phone.phoneType.toString()}</td>
                            <td name="phoneComment" width="50%">${phone.comment}</td>
                        </tr>
                    </c:forEach>

                </table>


                <%----------------------------------Attachments---------------------------------------%>
                <h3>Список присоединений</h3>

                <div class="table-btns">
                    <div class="tooltip-error">
                        <button type="button" id="btn-delete-attach" class="btn">Удалить</button>
                        <span class="tooltiptext" id="delete-attach-tooltip">Выберите присоединения</span>
                    </div>

                    <button type="button" id="btn-add-attach" class="btn">Добавить</button>
                    <div class="tooltip-error">
                        <button type="button" id="btn-edit-attach" class="btn">Редактировать</button>
                        <span class="tooltiptext" id="edit-attach-tooltip">Выберите одно присоединение</span>
                    </div>


                </div>
                <table id="attach-table" class="tbl" width="100%">
                    <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                        <tr>
                            <td width="6%"><input type="checkbox" name="attachIsSelected"
                                                  value="${attachment.getAttachment().id}"></td>
                            <td width="20%" name="attachName"><a name="attachLink"
                                                                 href="/file?id=${attachment.getFile().storedName}"
                                                                 download="${attachment.getAttachment().name}">${attachment.getAttachment().name}</a>
                            </td>

                            <td align="center" width="20%" name="attachUploadDate">
                                <fmt:formatDate value="${attachment.getAttachment().uploadDate}" var="formattedDate"
                                                type="date" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    ${formattedDate}
                            </td>
                            <td width="54%" name="attachComment">${attachment.getAttachment().comment}</td>
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
            <table id="hidden-phone-table">
                <c:forEach var="phone" items="${phones}" varStatus="counter">
                    <input type="text" name="phone[${counter.index}]"
                           value="id=${phone.id}&countryCode=${phone.countryCode}&operatorCode=${phone.operatorCode}&number=${phone.phoneNumber}&type=${phone.phoneType.name()}&comment=${phone.comment}&status=NONE">
                </c:forEach>
            </table>
            <%--hidden input for attachments metadata--%>
            <table id="attach-hidden-table">
                <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                    <fmt:formatDate value="${attachment.getAttachment().uploadDate}" var="formattedDate"
                                    type="date" pattern="dd.MM.yyyy HH:mm:ss"/>
                    <input type="text" name="attachMeta[${counter.index}]"
                           value="id=${attachment.getAttachment().id}&name=${attachment.getAttachment().name}&uploadDate=${formattedDate}&comment=${attachment.getAttachment().comment}&status=NONE">
                </c:forEach>
            </table>
        </div>
        <%--------------------------------------------------------------------------------%>

        <%--Add photo POPUP--%>
        <div id="photo-popup" class="popup">
            <div class="popup-content photo-popup-content">
                <p>Путь к картинке:</p>
                <input type="button" id="loadFile" class="btn" value="Найти"/>
                <input id="inputPhotoFile" type="file" name="photoFile"
                       accept="image/jpeg,image/png,image/gif" class="form-control">
                <div class="row controls-group">

                    <button id="btn-save-photo" class="btn popup-button" type="button">Сохранить</button>
                    <button id="btn-undo-photo" type="button" class="btn popup-button">Отменить</button>
                </div>
            </div>
        </div>

    </form>

    <%--Add phone POPUP--%>
    <div id="phone-popup" class="popup">
        <div class="popup-content">
            <form id="phone-form">

                <p class="form-text">Код страны:</p>
                <input id="country-code-input" type="tel" pattern="\d{3}" class="form-control input-margin input-inline" name="inputCountryCode"
                       placeholder="XXX" required>

                <p class="form-text">Код оператора:</p>
                <input id="operator-code-input" type="tel" pattern="\d{2}" class="form-control input-margin" name="inputOperatorCode"
                       placeholder="XX" required>

                <p class="form-text">Телефонный номер:</p>
                <input id="phone-number-input" type="tel" pattern="\d{7}" class="form-control input-margin" name="inputPhoneNumber"
                       placeholder="XXXXXXX" required>
                <p class="form-text">Тип телефона:</p>
                <div class="row">
                    <div class="col-md-1 col-md-offset-2">
                        <input type="radio" id="input-phone-type-home" class="input-margin" name="inputPhoneType"
                               value="home" checked>
                    </div>
                    <div class="col-md-2">Домашний</div>

                    <div class="col-md-1 col-md-offset-1">
                        <input type="radio" id="input-phone-type-mobile" class="input-margin" name="inputPhoneType"
                               value="mobile">
                    </div>
                    <div class="col-md-2">Мобильный</div>


                </div>
                <p class="form-text">Комментарий:</p>
                <input type="text" class="form-control input-margin" name="inputPhoneComment">


                <div class="controls-group">
                    <button id="btn-save-phone" type="submit" class="btn popup-button">Сохранить</button>
                    <button id="btn-undo-phone" type="button" class="btn popup-button">Отменить</button>
                </div>
            </form>
        </div>
    </div>

    <%--Add attach POPUP--%>
    <div id="attach-popup" class="popup">
        <div class="popup-content attach-popup">
            <form id="attachForm">
                <div id="file-container">
                </div>
                <p class="form-text" id="file-name-label">Имя файла:</p>
                <input type="text" id="file-name-input" class="form-control input-margin" name="inputAttachName"
                       required>

                <p class="form-text">Комментарий:</p>
                <input type="text" class="form-control input-margin" name="inputAttachComment">

                <div class="row controls-group">
                    <button id="btn-save-attach" class="btn popup-button" type="submit">Сохранить</button>
                    <button id="btn-undo-attach" class="btn popup-button" type="button">Отменить</button>
                </div>
            </form>
        </div>
    </div>

</div>
<footer>
    <script src="${pageContext.request.contextPath}/resources/js/contact_phones.js" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/resources/js/contact_attach.js" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/resources/js/contact_photo.js" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/resources/js/contact_main.js" charset="utf-8"></script>
</footer>


</body>
</html>