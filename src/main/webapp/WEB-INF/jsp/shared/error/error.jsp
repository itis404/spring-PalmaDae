<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="/assets/css/error.css">
</head>

<body class="auth-body">

<div class="auth-div">

    <div class="error-box">

        <h1 class="error-title">Ошибка</h1>

        <c:if test="${not empty exception}">
            <div class="error-message">
                    ${exception.message}
            </div>

            <div class="error-details">
                <c:if test="${not empty message}">
                    <div class="error-message">
                            ${message}
                    </div>
                </c:if>

                <c:if test="${empty message and not empty exception}">
                    <div class="error-message">
                            ${exception.message}
                    </div>

                    <div class="error-details">
                            ${exception}
                    </div>
                </c:if>

                <c:if test="${empty message and empty exception}">
                    <div class="error-message">
                        Что-то пошло не так
                    </div>
                </c:if>
            </div>
        </c:if>

        <c:if test="${empty exception}">
            <div class="error-message">
                Что-то пошло не так
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/home"
           class="back-link">
            Вернуться на главную
        </a>

    </div>

</div>

</body>
</html>