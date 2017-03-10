<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="../../../WEB-INF/lib/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../resources/css/main.css">
    <script src="../resources/js/main.js" defer></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <span class="navbar-brand">Справочник контактов</span>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="/search"><span class="glyphicon glyphicon-search"></span> Поиск
                    контактов</a></li>
                <li><a href="/email"><span class="glyphicon glyphicon-envelope"></span> Отправка Email</a></li>

            </ul>

        </div>
    </div>
</nav>
<div class="container-fluid">

    <div class="row">
        <form action="">
            <button class="btn btn-danger btn-delete">Удалить</button>
        </form>
        <form action="">
            <button class="btn btn-primary btn-add">Добавить <span class="glyphicon glyphicon-plus"></span></button>
        </form>
    </div>

    <div class="row">
        <table class="table">
            <tr valign="middle">
                <td id="table-checkbox" width="5%" align="middle"><input type="checkbox" name="isSelected"></td>

                <td width="110px">
                    <div class="photo-container">
                        <%--<img src="../../../resources/male.jpg" alt="" height="100%" class="photo">--%>
                            <img src="../resources/images/male.jpg" alt="" height="100%" class="photo">
                    </div>
                </td>
                <td>
                    <p><a href="" class="name">Гилимович Александр Сергеевич <span
                            class="glyphicon glyphicon-pencil"></span></a></p>

                    <div class="row">
                        <div class="col-md-2">
                            <p><b>Дата рождения:</b></p>
                        </div>
                        <div class="col-md-2">
                            <p><b>Домашний адрес:</b></p>
                        </div>
                        <div class="col-md-2">
                            <p><b>Место работы:</b></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <p>12.11.1990 г.</p>
                        </div>
                        <div class="col-md-2">
                            <p>г. Минск, ул.Селицкого, 77-112</p>
                        </div>
                        <div class="col-md-2">
                            <p>Тунеядец</p>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="row">
        <div class="col-md-10">
            <div class="pages">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-2">
            <div class="items-display">
                <span>Отображать контактов:</span>
                <select name="display-items" class="form-control">
                    <option value="ten">10</option>
                    <option value="twenty">20</option>
                </select>
            </div>
        </div>
    </div>
</div>
</body>
</html>