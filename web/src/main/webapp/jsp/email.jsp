<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/email.css">
    <script src="/resources/js/email.js" defer></script>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/" class="navbar-brand">Справочник контактов</a>
        </div>
        <div class="navbar-header">
            <span class="navbar-brand">> Отправка email</span>
        </div>
    </div>
</nav>
<div class="container-fluid ">
    <form id="form" onsubmit="moveDataToInput()" action="/send" method="post">
        <div class="row">
            <div class="col-md-6 well offset20px">
                <div class="row buffer-top">
                    <p>Кому:</p>
                    <input type="text" name="emailAddresses" value="${email.emailAddresses}" class="form-control"
                           required>
                </div>
                <div class="row">
                    <p>Тема:</p>
                    <input type="text" name="subject" value="${email.subject}" class="form-control">
                </div>
                <div class="row">
                    <p>Шаблон:</p>
                    <c:forEach var="template" items="${templates}">
                        <select name="template" value="${template.name}" class="form-control"></select>
                    </c:forEach>
                </div>
                <div class="row">
                    <p>Текст:</p>
                    <input name="bodyInput" type="text" class="hidden">
                    <div id="body-div" class="text-field" contenteditable="true" value="${email.body}"></div>
                </div>
                <div class="row">
                    <div class="control-group buffer-top">
                        <button class="btn btn-success" type="submit">Отправить</button>
                        <a href="${pageContext.request.contextPath}/">
                            <button class="btn btn-danger" type="button">Отменить</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>