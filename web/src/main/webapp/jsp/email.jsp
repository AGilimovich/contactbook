<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/email.css">
    <script src="${pageContext.request.contextPath}/resources/js/email.js" charset="utf-8" defer></script>

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
    <form id="form" action="/send" method="post">
        <div class="row">
            <div class="col-md-6 well offset20px">
                <div class="row buffer-top">
                    <p class="form-text">Кому:</p>
                    <input type="text" name="emailAddresses" value="${emailListTemplate.getTemplate().render()}"
                           class="form-control"
                           required>
                </div>
                <div class="row">
                    <p class="form-text">Тема:</p>
                    <input type="text" name="subject" value="${email.subject}" class="form-control">
                </div>
                <div class="row">
                        <p class="form-text">Шаблон:</p>
                        <select name="template" value="${template.getValue().getDescription()}" class="form-control"
                                onchange="showTemplate(this.selectedIndex);">
                            <c:forEach var="template" items="${templates}" varStatus="status">
                                <option value="${status.index}">${template.getValue().getDescription()}</option>
                            </c:forEach>
                        </select>
                </div>
                <%--<input name="emailBody" value="${templates[0].render()}" type="text" class="hidden">--%>

                <div class="row">
                    <p class="form-text">Текст:</p>
                    <c:forEach var="template" items="${templates}" varStatus="status">
                        <textarea name="email-body"
                                  id="email-body[${status.index}]" ${status.index == 0?'' : 'disabled'}

                                  class="${status.index == 0?'text-field white-space-pre' : 'text-field white-space-pre hidden'}">${template.getValue().getTemplate().render()}</textarea>
                    </c:forEach>


                </div>
                <div class="row">
                    <div class="control-group buffer-top">
                        <button class="btn btn-success" type="submit">Отправить</button>
                        <a href="${pageContext.request.contextPath}/">
                            <button class="btn" type="button">Отменить</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>