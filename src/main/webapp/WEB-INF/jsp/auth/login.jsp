<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/assets/css/auth.css">
</head>
<body class="auth-body">
<div class="auth-div">
    <h1 class="auth-h1">Donor Track</h1>
    <h2>Впишите ваши данные для входа</h2>

    <c:if test="${param.error != null}">
        <div class="error">
            Invalid username or password
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/auth/login">
        <label class="auth-label" for="auth">
            Username:
        </label>
        <input type="text" id="auth" name="username"
                placeholder="Введите ваш username" required class="auth-input">

        <label class="auth-label" for="password">
            Password:
        </label>
        <input type="password" id="password" name="password"
               placeholder="Введите ваш пароль" required class="auth-input">

        <label class="checkbox">
            <input type="checkbox" name="remember-me">
            <span class="checkmark"></span>
            Remember me?
        </label>

        <div class="wrap">
            <button class="auth-button" type="submit">Login</button>
        </div>
    </form>
    <p>Не зарегистрированы?
        <a href="${pageContext.request.contextPath}/auth/registration" style="text-decoration: none;">
            Создать Аккаунт
        </a>
    </p>
</div>
</body>
</html>