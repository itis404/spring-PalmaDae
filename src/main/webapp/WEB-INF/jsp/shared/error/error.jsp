<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="/assets/css/auth.css">

    <style>
        .error-box {
            text-align: center;
            padding: 30px;
        }

        .error-title {
            color: #ff0004;
            font-size: 28px;
            margin-bottom: 10px;
        }

        .error-message {
            color: #333;
            margin-bottom: 20px;
            font-size: 16px;
            word-break: break-word;
        }

        .error-details {
            background: #f8f8f8;
            padding: 10px;
            border-radius: 8px;
            font-size: 12px;
            color: #666;
            text-align: left;
            max-height: 200px;
            overflow: auto;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #ff0004;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
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