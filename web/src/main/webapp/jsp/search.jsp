<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/search.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span class="navbar-brand">> Поиск контакта</span>
        </div>
    </div>
</nav>

<div class="container-fluid">

    <form action="/find" method="post">
        <div class="top-buffer row">
            <div class="col-md-8">
                <div class="row well">
                    <div class="col-md-4">
                        <p>Фамилия:</p>
                        <input type="text" name="surname" class="form-control">
                    </div>
                    <div class="col-md-4">
                        <p>Имя:</p>
                        <input type="text" name="name" class="form-control">
                    </div>
                    <div class="col-md-4">
                        <p>Отчество:</p>
                        <input type="text" name="patronymic" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="top-buffer row">
            <div class="col-md-8">
                <div class="row well">
                    <div class="col-md-4">
                        <p>Дата рождения:</p>
                        <span>с </span><input type="date" name="fromDate" class="date form-control"> <span>по</span> <input id='toDate' name="toDate"
                                                                                                            type="date"
                                                                                                            class="date form-control">
                    </div>
                    <div class="col-md-2">
                        <p>Пол:</p>
                        <select name="gender" class="gender form-control">
                            <option value="any">любой</option>
                            <option value="male">м</option>
                            <option value="female">ж</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <p>Семейное положение:</p>
                        <select name="familyStatus" class="family form-control">
                            <option value="any" selected>любое</option>
                            <option value="single">холост/не замужем</option>
                            <option value="married">женат/замужем</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <p>Гражданство:</p>
                        <input type="text" name="citizenship" class="form-control">
                    </div>
                </div>

            </div>
        </div>
        <div class="top-buffer row">
            <div class="col-md-8">
                <div class="row well">
                    <h3 style="margin-left:15px">Адрес проживания</h3>

                    <div class="col-md-3">
                        <p>Страна:</p>
                        <input type="text" name="country" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <p>Город:</p>
                        <input type="text" name="city" class="form-control">
                    </div>
                    <div class="col-md-2">
                        <p>Улица:</p>
                        <input type="text" name="street" class="form-control">
                    </div>
                    <div class="col-md-1">

                        <p>Дом:</p>
                        <input type="text" name="house" class="form-control">
                    </div>
                    <div class="col-md-1">

                        <p>Квартира:</p>
                        <input type="text" name="apartment" class="form-control">
                    </div>
                    <div class="col-md-2">

                        <p>Почтовый индекс:</p>
                        <input type="text" name="zipCode" class="form-control">
                    </div>
                </div>
            </div>

        </div>
        <div class="top-buffer row">
            <div class="col-md-8">
                <button type="submit" class="btn btn-search btn-success">Поиск</button>
            </div>
        </div>

    </form>
</div>
</body>
</html>