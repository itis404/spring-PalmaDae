<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="/assets/css/profile-edit.css">
</head>

<body class="profile-edit-body">

<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />


<c:if test="${not empty successMessage}">
    <div class="success-message" style="background-color: #d4edda; color: #155724; padding: 10px; margin: 10px auto; width: 80%; border-radius: 5px; text-align: center;">
            ${successMessage}
    </div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="error-message" style="background-color: #f8d7da; color: #721c24; padding: 10px; margin: 10px auto; width: 80%; border-radius: 5px; text-align: center;">
            ${errorMessage}
    </div>
</c:if>

<div class="profile-edit-container">

    <c:if test="${authProvider == 'LOCAL'}">
        <div class="profile-card">
            <h3>Сменить почту</h3>

            <form action="${pageContext.request.contextPath}/profile/edit/email" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <label>Новая почта:</label>
                    <input type="email" name="newEmail" required class="profile-input"/>
                </div>

                <button class="profile-button" type="submit">Обновить почту</button>
            </form>
        </div>

        <div class="profile-card">
            <h3>Сменить пароль</h3>

            <form action="${pageContext.request.contextPath}/profile/edit/password" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <label>Старый пароль:</label>
                    <input type="password" name="oldPassword" required class="profile-input"/>
                </div>

                <div class="form-group">
                    <label>Новый пароль:</label>
                    <input type="password" name="newPassword" required class="profile-input"/>
                </div>

                <div class="form-group">
                    <label>Подтвердите новый пароль:</label>
                    <input type="password" name="confirmPassword" required class="profile-input"/>
                </div>

                <button class="profile-button" type="submit">Сменить пароль</button>
            </form>
        </div>
    </c:if>

    <div class="profile-card">
        <h3>Сменить группу крови</h3>

        <form action="${pageContext.request.contextPath}/profile/edit/bloodtype" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label>Группа крови:</label>
                <select name="bloodType" class="profile-input">
                    <option value="">-- Не выбрана --</option>
                    <c:forEach items="${bloodTypes}" var="type">
                        <option value="${type}" ${type == currentBloodType ? 'selected' : ''}>
                                ${type}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button class="profile-button" type="submit">Обновить группу крови</button>
        </form>
    </div>

    <div class="profile-card">
        <h3>Сменить город</h3>

        <div class="form-group">
            <label>Введите город:</label>
            <input type="text" id="cityInput" autocomplete="off" class="profile-input"/>
            <div id="suggestions" class="suggestions-box"></div>
        </div>

        <form action="${pageContext.request.contextPath}/profile/edit/city" method="post" id="cityForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="cityHidden" name="city"/>
            <button class="profile-button" type="submit">Сохранить</button>
        </form>
    </div>
</div>

<div class="back-link">
    <a href="/profile">Обратно в профиль</a>
</div>

<script src="/assets/js/edit-profile.js"></script>

</body>
</html>