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
            <a href="${pageContext.request.contextPath}?pageNumber=0" class="navbar-brand">На главную</a>
        </div>

    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <a class="cancel-anchor close" href="${pageContext.request.contextPath}?pageNumber=0">
        </a>
    </div>
    <form action="${pageContext.request.contextPath}/find?pageNumber=0" method="post">
        <div class="top-buffer row ">
            <div class="col-md-12">
                <div class="row well">

                    <div class="col-md-4">
                        <p class="p-margin">Фамилия:</p>
                        <input type="text" id="surname-input" name="surname" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" class="form-control">
                    </div>
                    <div class="col-md-4">
                        <p class="p-margin">Имя:</p>
                        <input type="text" id="name-input" name="name" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" class="form-control">
                    </div>
                    <div class="col-md-4">
                        <p class="p-margin">Отчество:</p>
                        <input type="text" id="patronymic-input"  name="patronymic" pattern="^[A-Za-zА-Яа-яЁё]{1,50}$" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="top-buffer row">
            <div class="col-lg-12">
                <div class="row well">
                    <div class="col-lg-4">

                            <p class="p-margin">Дата рождения:</p>


                            <span>с </span><input type="text" placeholder="ДД.ММ.ГГГГ" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" id="from-date" name="fromDate" class="date form-control"> <span>по</span>
                            <input id="to-date" name="toDate"
                                   type="text" placeholder="ДД.ММ.ГГГГ" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$"
                                   class="date form-control">

                    </div>
                    <div class="col-lg-1">

                            <p class="p-margin">Пол:</p>


                            <select name="gender" class="gender form-control">
                                <option value="any">любой</option>
                                <option value="male">м</option>
                                <option value="female">ж</option>
                            </select>

                    </div>
                    <div class="col-lg-2 col-lg-offset-1">

                            <p class="p-margin">Семейное положение:</p>



                            <select name="familyStatus" class="family form-control">
                                <option value="any" selected>любое</option>
                                <option value="single">холост/не замужем</option>
                                <option value="married">женат/замужем</option>
                            </select>


                    </div>
                    <div class="col-lg-3 col-lg-offset-1">

                            <p class="p-margin">Гражданство:</p>
                            <input type="text" name="citizenship" class="form-control">


                    </div>
                </div>

            </div>
        </div>
        <div class="top-buffer row">
            <div class="col-md-12">
                <div class="row well">
                    <h3 class="margin-left-15px">Адрес проживания</h3>

                    <div class="col-md-3">
                        <p class="p-margin">Страна:</p>
                        <input type="text" name="country" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <p class="p-margin">Город:</p>
                        <input type="text" name="city" class="form-control">
                    </div>
                    <div class="col-md-2">
                        <p class="p-margin">Улица:</p>
                        <input type="text" name="street" class="form-control">
                    </div>
                    <div class="col-md-1">

                        <p class="p-margin">Дом:</p>
                        <input type="text" name="house" class="form-control">
                    </div>
                    <div class="col-md-1">

                        <p class="p-margin">Квартира:</p>
                        <input type="text" name="apartment" class="form-control">
                    </div>
                    <div class="col-md-2">

                        <p class="p-margin">Почтовый индекс:</p>
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