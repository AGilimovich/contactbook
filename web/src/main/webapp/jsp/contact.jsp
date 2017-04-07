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
            <a href="${pageContext.request.contextPath}/" class="navbar-brand">На главную</a>
        </div>

    </div>
</nav>


<div class="container-fluid">
    <form id="main-form" action="${pageContext.request.contextPath}/${action}" method="post"
          enctype="multipart/form-data"
          accept-charset="UTF-8" class="form-horizontal">
        <div class="row">

            <a class="cancel-anchor close" href="${pageContext.request.contextPath}/">
            </a>

            <!--Photo-->
            <div class="col-lg-2 col-md-3">

                <div class="photo-container">
                    <img src="${pageContext.request.contextPath}/image?id=${photo.storedName}" height="100%" alt="photo"
                         id="photo">
                </div>

            </div>
            <!--Form-->
            <div class="col-lg-4 col-md-9">
                <div class="row">
                    <div class="col-md-6">

                        <p class="form-text"><span>Имя</span><span class="red-text">*</span>:</p>
                        <input id="name-input" type="text" name="name" value="${contact.name}" class="form-control"
                               pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" required>


                        <p class="form-text"><span>Фамилия</span><span class="red-text">*</span>:</p>
                        <input id="surname-input" type="text" name="surname" value="${contact.surname}"
                               class="form-control" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" required>

                        <p class="notification">* Поля, обязательные для заполнения</p>

                        <p class="form-text">Отчество:</p>
                        <input id="patronymic-input" type="text" name="patronymic" value="${contact.patronymic}"
                               pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" class="form-control">


                        <p class="form-text">Дата рождения:</p>
                        <fmt:formatDate value="${contact.dateOfBirth}" var="formattedDateOfBirth"
                                        type="date" pattern="dd.MM.yyyy"/>
                        <input id="date-input" type="text" name="dateOfBirth" value="${formattedDateOfBirth}"
                               placeholder="ДД.ММ.ГГГГ"
                               pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$"
                               class="form-control">


                        <p class="form-text">Пол:</p>


                        <input id="gender-radio-male" type="radio" name="gender"
                               value="male" ${contact.gender.name() =='MALE'?'checked':''} ${contact == null ?'checked':''}>
                        м

                        <input id="gender-radio-female" type="radio" name="gender"
                               value="female" ${contact.gender.name() =='FEMALE'?'checked':''}> ж

                        <p class="form-text">Гражданство:</p>
                        <input type="text" name="citizenship" value="${contact.citizenship}" class="form-control">

                        <p class="form-text">Семейное положение:</p>

                        <div>
                            <input class="family-status-radio" type="radio" name="familyStatus"
                                   value="married" ${contact.familyStatus.name() =='MARRIED'?'checked':''} ${contact == null ?'checked':''}>

                            <span>женат / замужем</span>
                        </div>
                        <div>

                            <input class="family-status-radio" type="radio" name="familyStatus"
                                   value="single" ${contact.familyStatus.name() =='SINGLE'?'checked':''} >


                            <span>холост / не замужем</span>
                        </div>

                        <p class="form-text">Веб-сайт:</p>
                        <input type="text" name="website" value="${contact.website}" class="form-control">

                        <p class="form-text">Email:</p>
                        <input id="email-input" type="email"
                               name="email" value="${contact.email}" class="form-control">

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

            <div class="col-lg-6 col-md-12">
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


                <table id="phone-table" class="tbl tbl-phone" width="100%">
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td width="6%"><input type="checkbox" value="${phone.id}" name="phoneIsSelected"></td>
                            <td name="countryCode" width="5%">${phone.countryCode}</td>
                            <td width="1%" align="right">(</td>
                            <td name="operatorCode" width="3%" align="center">${phone.operatorCode}</td>
                            <td width="1%" align="left">)</td>
                            <td name="phoneNumber" width="15%">${phone.phoneNumber}</td>
                            <td name="phoneType" align="center" width="19%">${phone.phoneType.toString()}</td>
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
                <table id="attach-table" class="tbl tbl-attach" width="100%">
                    <c:forEach var="attachment" items="${attachments}" varStatus="counter">
                        <tr>
                            <td width="6%"><input type="checkbox" name="attachIsSelected"
                                                  value="${attachment.getAttachment().id}"></td>
                            <td width="25%" name="attachName"><a name="attachLink"
                                                                 href="/file?id=${attachment.getFile().storedName}"
                                                                 download="${attachment.getAttachment().name}">${attachment.getAttachment().name}</a>
                            </td>

                            <td align="center" width="19%" name="attachUploadDate">
                                <fmt:formatDate value="${attachment.getAttachment().uploadDate}" var="formattedDate"
                                                type="date" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    ${formattedDate}
                            </td>
                            <td width="50%" name="attachComment">${attachment.getAttachment().comment}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <%--Buttons for form--%>
        <div class="row">
            <button class="btn btn-primary btn-save" type="submit">Сохранить</button>

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
                <a id="anchor-undo-photo" class="close close-popup">
                </a>
                <div class="popup-content-padding">
                    <p>Путь к картинке:</p>
                    <input type="button" id="loadFile" class="btn" value="Найти"/>
                    <input id="inputPhotoFile" type="file" name="photoFile"
                           accept="image/jpeg,image/png,image/gif" class="form-control">

                    <button id="btn-save-photo" class="btn btn-primary" type="button">Сохранить</button>


                </div>
            </div>
        </div>

    </form>

    <%--Add phone POPUP--%>
    <div id="phone-popup" class="popup">
        <div class="popup-content">
            <a id="anchor-undo-phone" class="close close-popup">
            </a>
            <div class="popup-content-padding">
                <form id="phone-form">

                    <p class="form-text">Код страны:</p>
                    <input id="country-code-input" type="tel" pattern="\d{3}"
                           class="form-control input-margin input-inline"
                           name="inputCountryCode"
                           placeholder="XXX" required>

                    <p class="form-text">Код оператора:</p>
                    <input id="operator-code-input" type="tel" pattern="\d{2}" class="form-control input-margin"
                           name="inputOperatorCode"
                           placeholder="XX" required>

                    <p class="form-text">Телефонный номер:</p>
                    <input id="phone-number-input" type="tel" pattern="\d{7}" class="form-control input-margin"
                           name="inputPhoneNumber"
                           placeholder="XXXXXXX" required>
                    <p class="form-text">Тип телефона:</p>


                    <input type="radio" id="input-phone-type-home" class="input-margin" name="inputPhoneType"
                           value="home" checked>

                    <span>Домашний</span>
                    <input type="radio" id="input-phone-type-mobile" class="input-margin" name="inputPhoneType"
                           value="mobile">
                    <span>Мобильный</span>


                    <p class="form-text">Комментарий:</p>
                    <input type="text" class="form-control input-margin" name="inputPhoneComment">


                    <button id="btn-save-phone" type="submit" class="btn margin-top btn-primary">Сохранить</button>

                </form>
            </div>
        </div>
    </div>

    <%--Add attach POPUP--%>
    <div id="attach-popup" class="popup">
        <div class="popup-content attach-popup">
            <a id="anchor-undo-attach" class="close close-popup">
            </a>
            <div class="popup-content-padding">
                <form id="attachForm">
                    <div id="file-container" class="padding-top">
                    </div>
                    <p class="form-text" id="file-name-label">Имя файла:</p>
                    <input type="text" id="file-name-input" class="form-control input-margin" name="inputAttachName"
                           required>

                    <p class="form-text">Комментарий:</p>
                    <input type="text" class="form-control input-margin" name="inputAttachComment">


                    <button id="btn-save-attach" class="btn margin-top btn-primary" type="submit">Сохранить</button>

                </form>
            </div>
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