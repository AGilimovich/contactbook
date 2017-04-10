<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/email.css">

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}?pageNumber=0" class="navbar-brand">На главную</a>
        </div>

    </div>
</nav>
<div class="container-fluid ">

    <form id="form" action="${pageContext.request.contextPath}/send" method="post">
        <div class="row">
            <div class="col-lg-6 col-md-8 well offset20px">
                <div class="row">
                    <a class="cancel-anchor close" href="${pageContext.request.contextPath}?pageNumber=0">
                    </a>
                </div>
                <div class="row buffer-top">
                    <p class="form-text">Кому:</p>
                    <input id="input-email-addresses" type="text" name="emailAddresses" required
                           value="${emailListTemplate.getTemplate().render()}" disabled
                           class="form-control">
                </div>
                <div class="row">
                    <p class="form-text">Тема:</p>
                    <input id="input-subject" type="text" name="subject" value="${email.subject}" class="form-control">
                </div>
                <div class="row">
                    <p class="form-text">Шаблон:</p>
                    <select id="template-select" name="template" value="${template.getValue().getDescription()}"
                            class="form-control"
                            onchange="showTemplate(this.selectedIndex);">
                        <c:forEach var="template" items="${templates}" varStatus="status">
                            <option value="${template.getTemplate().getName()}">${template.getDescription()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="row">
                    <p class="form-text">Текст:</p>
                    <c:forEach var="template" items="${templates}" varStatus="status">
                        <textarea name="email-body"
                                  id="email-body[${status.index}]" ${status.index == 0? 'required' : 'disabled'}

                                  class="${status.index == 0?'text-field white-space-pre' : 'text-field white-space-pre hidden'}">${template.getTemplate().render()}</textarea>
                    </c:forEach>


                </div>
                <div class="row">
                    <div class="control-group buffer-top">
                        <button class="btn btn-primary" id="btn-send-email" type="button">Отправить</button>

                    </div>
                </div>
            </div>
        </div>
        <div class="hidden">
            <button type="submit" id="btn-submit-form"></button>
        </div>
    </form>
</div>


<footer>
    <script src="${pageContext.request.contextPath}/resources/js/email.js" charset="utf-8"></script>
</footer>
</body>
</html>