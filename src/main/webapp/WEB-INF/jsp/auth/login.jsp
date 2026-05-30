<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body class="auth-body">
<div class="auth-div">
    <h1 class="auth-h1">Donor Track</h1>
    <h2>Впишите ваши данные для входа</h2>

    <c:if test="${param.error != null}">
        <div class="error-block">
            Неверное имя пользователя или пароль
        </div>
    </c:if>

    <form:form method="post"
               modelAttribute="userDto"
               action="${pageContext.request.contextPath}/auth/login">

        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>

        <div class="form-group">
            <label class="auth-label">Имя пользователя:</label>
            <form:input path="username"
                        cssClass="auth-input"
                        placeholder="Введите ваш username"/>
        </div>

        <div class="form-group">
            <label class="auth-label">Пароль:</label>
            <form:password path="password"
                           cssClass="auth-input"
                           placeholder="Введите ваш пароль"/>
        </div>

        <div class="form-group">
            <label class="checkbox">
                <input type="checkbox" name="remember-me">
                <span class="checkmark"></span>
                Запомнить меня?
            </label>
        </div>

        <button type="submit" class="auth-button">
            Войти
        </button>
    </form:form>

    <p>
        Не зарегистрированы?
        <a href="${pageContext.request.contextPath}/auth/registration"
           style="text-decoration: none;">
            Создать аккаунт
        </a>
    </p>

    <p>
        Не хотите регаться?
        <a href="${pageContext.request.contextPath}/oauth/yandex"
           style="text-decoration: none;">
            Войти через Яндекс
        </a>
    </p>
</div>
</body>
</html>