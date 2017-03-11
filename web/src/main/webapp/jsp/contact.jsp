<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                        <input type="text" class="form-control" required>

                        <p>Фамилия:</p>
                        <input type="text" class="form-control" required>

                        <p>Отчество:</p>
                        <input type="text" class="form-control" required>

                        <p>Дата рождения:</p>
                        <input type="date" class="form-control" required>

                        <p>Пол:</p>
                        <input type="radio" name="gender" value="male" required> м
                        <input type="radio" name="gender" value="female"> ж

                        <p>Гражданство:</p>
                        <input type="text" class="form-control" required>

                        <p>Семейное положение:</p>
                        <input type="text" class="form-control" required>

                        <p>Веб-сайт:</p>
                        <input type="text" class="form-control">

                        <p>Email:</p>
                        <input type="email" class="form-control" required>

                        <p>Текущее место работы:</p>
                        <input type="text" class="form-control">
                    </div>
                    <div class="col-md-6">
                        <h3>Адрес</h3>

                        <p>Страна:</p>
                        <input type="text" class="form-control">

                        <p>Город:</p>
                        <input type="text" class="form-control">

                        <p>Улица:</p>
                        <input type="text" class="form-control">

                        <p>Дом:</p>
                        <input type="text" class="form-control">

                        <p>Квартира:</p>
                        <input type="text" class="form-control">
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
                    <tr>
                        <td width="6%"><input type="checkbox"></td>
                        <td width="30%">+375(44)7852202</td>
                        <td width="14%">Мобильный</td>
                        <td width="50%">Комментарий</td>
                    </tr>
                    <tr>
                        <td width="6%"><input type="checkbox"></td>
                        <td width="30%">+375(44)7852202</td>
                        <td width="14%">Мобильный</td>
                        <td width="50%">Комментарий</td>
                    </tr>
                </table>


                <h3>Список присоединений</h3>
                <div class="table-btns">
                    <button type="button" id="btn-delete-attach" class="btn">Удалить</button>
                    <button type="button" id="btn-add-attach" class="btn">Добавить</button>
                    <button type="button" id="btn-edit-attach" class="btn">Редактировать</button>

                </div>
                <table class="tbl" width="100%">
                    <tr>
                        <td width="6%"><input type="checkbox"></td>
                        <td width="30%">Имя файла присоединения</td>
                        <td width="14%">Дата загрузки</td>
                        <td width="50%">Комментарий</td>
                    </tr>
                    <tr>
                        <td width="6%"><input type="checkbox"></td>
                        <td width="30%">Имя файла присоединения</td>
                        <td width="14%">Дата загрузки</td>
                        <td width="50%">Комментарий</td>
                    </tr>
                </table>


            </div>


        </div>
        <div class="row">
            <div class="controls-group">
                <button class="btn" type="submit">Сохранить</button>
                <button type="button" class="btn">Отменить</button>
            </div>
        </div>
    </form>
</div>

<div id="phone-popup" class="popup">
    <div class="popup-content">
        <form action="/contact/phone" method="post">
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
                <button id="btn-save-phone" type="submit" class="btn">Сохранить</button>
                <button id="btn-undo-phone" class="btn">Отменить</button>
            </div>
        </form>
    </div>
</div>


<div id="attach-popup" class="popup">
    <div class="popup-content">
        <form action="/contact/attach" method="post">

            <input type="file" required>

            <p>Имя файла:</p>
            <input type="text" required class="form-control">

            <p>Комментарий:</p>
            <input type="text" class="form-control">

            <div class="row controls-group">
                <button id="btn-save-attach" class="btn" type="submit">Сохранить</button>
                <button id="btn-undo-attach" class="btn">Отменить</button>
            </div>
        </form>
    </div>
</div>


<div id="photo-popup" class="popup">
    <div class="popup-content">
        <form action="/contact/photo" method="post">
            <p>Путь к картинке:</p>
            <input type="file" accept="image/*,image/jpeg" required class="form-control">

            <div class="row controls-group">
                <button class="btn">Найти</button>
                <button id="btn-save-photo" class="btn" type="submit">Сохранить</button>
                <button id="btn-undo-photo" class="btn">Отменить</button>
            </div>
        </form>
    </div>
</div>


</body>
</html>