<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Confirm Registration</title>
    <link rel="stylesheet" href="/assets/css/auth.css">
</head>

<body class="auth-body">

<div class="auth-div">
    <h1 class="auth-h1">Donor Track</h1>
    <h2>Подтверждение регистрации</h2>

    <p class="info">
        Мы отправили код подтверждения на вашу почту
    </p>

    <c:if test="${not empty error}">
        <div class="error">
                ${error}
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/auth/registration/confirm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="email" value="${pendingUser.email}"/>

        <label class="auth-label" for="code">
            Код подтверждения:
        </label>

        <input type="text"
               id="code"
               name="code"
               placeholder="Введите 6-значный код"
               required
               class="auth-input"
               autofocus>

        <div class="wrap">
            <button class="auth-button" type="submit">
                Подтвердить
            </button>
        </div>
    </form>

    <p>
        Вернуться к
        <a href="${pageContext.request.contextPath}/auth/registration"
           style="text-decoration: none;">
            регистрации
        </a>
    </p>

</div>

</body>
</html>