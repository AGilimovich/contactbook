<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/email.css">

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span class="navbar-brand">> Отправка email</span>
        </div>
    </div>
</nav>
<div class="container-fluid ">
    <form action="/email" method="post">
        <div class="row">
            <div class="col-md-6 well offset20px">
                <div class="row buffer-top">
                    <p>Кому:</p>
                    <input type="text" name="addressee" class="form-control" required>
                </div>
                <div class="row">
                    <p>Тема:</p>
                    <input type="text" name="theme" class="form-control">
                </div>
                <div class="row">
                    <p>Шаблон:</p>
                    <select name="template" class="form-control"></select>
                </div>
                <div class="row">
                    <p>Текст:</p>
                    <input id="text-field" name="text" type="text" class="form-control" required>
                </div>
                <div class="row">
                    <div class="control-group buffer-top">
                        <button class="btn btn-success">Отправить</button>
                        <button class="btn btn-danger">Отменить</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>