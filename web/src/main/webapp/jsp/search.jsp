<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="../?search=false" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span class="navbar-brand">> Поиск контакта</span>
        </div>
    </div>
</nav>

<div class="container-fluid">

    <form action="${pageContext.request.contextPath}/find" method="post">
        <div class="top-buffer row">
            <div class="col-md-12">
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
            <div class="col-md-12">
                <div class="row well">
                    <div class="col-md-4">
                        <div class="row">
                            <p>Дата рождения:</p>
                        </div>
                        <div class="row">
                            <span>с </span><input type="text" placeholder="ДД.ММ.ГГГГ" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" id="from-date" name="fromDate" class="date form-control"> <span>по</span>
                            <input id="to-date" name="toDate"
                                   type="text" placeholder="ДД.ММ.ГГГГ" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                   class="date form-control">
                        </div>
                    </div>
                    <div class="col-md-1 ">
                        <div class="row">
                            <p>Пол:</p>
                        </div>
                        <div class="row">
                            <select name="gender" class="gender form-control">
                                <option value="any">любой</option>
                                <option value="male">м</option>
                                <option value="female">ж</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2 col-md-offset-1">
                        <div class="row">
                            <p>Семейное положение:</p>
                        </div>
                        <div class="row">

                            <select name="familyStatus" class="family form-control">
                                <option value="any" selected>любое</option>
                                <option value="single">холост/не замужем</option>
                                <option value="married">женат/замужем</option>
                            </select>
                        </div>

                    </div>
                    <div class="col-md-4">
                        <div class="row">
                            <p>Гражданство:</p>
                        </div>
                        <div class="row">

                            <input type="text" name="citizenship" class="form-control">
                        </div>

                    </div>
                </div>

            </div>
        </div>
        <div class="top-buffer row">
            <div class="col-md-12">
                <div class="row well">
                    <h3 class="margin-left-15px">Адрес проживания</h3>

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
            <div class="col-md-12">
                <button type="submit" class="btn btn-search btn-success">Поиск</button>
            </div>
        </div>

    </form>
</div>
<footer>
    <script src="${pageContext.request.contextPath}/resources/js/search.js"></script>
</footer>
</body>
</html>