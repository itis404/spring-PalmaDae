<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>404 - Страница не найдена</title>
    <link rel="stylesheet" href="/assets/css/404.css">
</head>

<body class="notfound-body">

<div class="notfound-wrapper">

    <div class="notfound-card">

        <div class="notfound-code">404</div>

        <h1 class="notfound-title">Страница не найдена</h1>

        <p class="notfound-text">
            Запрашиваемая страница не существует или была перемещена.
        </p>

        <a class="notfound-button" href="${pageContext.request.contextPath}/home">
            Вернуться на главную
        </a>

    </div>

</div>

</body>
</html>