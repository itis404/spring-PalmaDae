<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <link rel="stylesheet" href="/assets/css/auth.css">
</head>

<body class="auth-body">
    <div class="auth-div">

        <h2 class="auth-h1">Registration Form</h2>

        <form:form method="post"
                   modelAttribute="userDto"
                   action="${pageContext.request.contextPath}/auth/registration">

            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <div class="form-group">

                <label class="auth-label">Имя пользователя:</label>

                <form:input path="username"
                            cssClass="auth-input"
                            placeholder="Введите имя пользователя"/>

                <form:errors path="username" />

            </div>

            <div class="form-group">

                <label class="auth-label">Пароль:</label>

                <form:password path="password"
                               cssClass="auth-input"
                               placeholder="Введите пароль"/>

                <form:errors path="password" />

            </div>

            <div class="form-group">

                <label class="auth-label">Подтвердите пароль:</label>

                <form:password path="passCorrect"
                               cssClass="auth-input"
                               placeholder="Повторите пароль"/>

                <form:errors path="passCorrect" />

            </div>

            <div class="form-group">

                <label class="auth-label">Электронная почта:</label>

                <form:input path="email"
                            type="email"
                            cssClass="auth-input"
                            placeholder="Введите email"/>

                <form:errors path="email" />

            </div>

            <button type="submit" class="auth-button">
                Зарегистрироваться
            </button>

        </form:form>
        <p>Уже зарегистрированы?
            <a href="${pageContext.request.contextPath}/auth/login" style="text-decoration: none;">
                Войти
            </a>
        </p>

    </div>

</body>