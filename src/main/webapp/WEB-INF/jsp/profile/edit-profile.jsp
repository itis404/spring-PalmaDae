<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="/assets/css/profile-edit.css">
</head>

<body class="profile-edit-body">

<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<h1 class="profile-edit-title">Edit Profile</h1>

<c:if test="${not empty successMessage}">
    <div class="success">${successMessage}</div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="error">${errorMessage}</div>
</c:if>

<div class="profile-edit-container">
    <div class="profile-card">
        <h3>Change email</h3>

        <form action="/profile/edit/email" method="post">

            <div class="form-group">
                <label>New Email:</label>
                <input type="email" name="newEmail" required class="profile-input"/>
            </div>

            <button class="profile-button" type="submit">Update Email</button>

        </form>
    </div>

    <div class="profile-card">
        <h3>Change Password</h3>

        <form action="/profile/edit/password" method="post">

            <div class="form-group">
                <label>Old Password:</label>
                <input type="password" name="oldPassword" required class="profile-input"/>
            </div>

            <div class="form-group">
                <label>New Password:</label>
                <input type="password" name="newPassword" required class="profile-input"/>
            </div>

            <div class="form-group">
                <label>Confirm New Password:</label>
                <input type="password" name="confirmPassword" required class="profile-input"/>
            </div>

            <button class="profile-button" type="submit">Change Password</button>

        </form>
    </div>

    <div class="profile-card">
        <h3>Change Blood Type</h3>

        <form action="/profile/edit/bloodtype" method="post">

            <div class="form-group">
                <label>Blood Type:</label>

                <select name="bloodType" class="profile-input">
                    <option value="">-- Not specified --</option>
                    <c:forEach items="${bloodTypes}" var="type">
                        <option value="${type}" ${type == currentBloodType ? 'selected' : ''}>
                                ${type}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button class="profile-button" type="submit">Update Blood Type</button>

        </form>
    </div>

    <div class="profile-card">
        <h3>Change City</h3>

        <div class="form-group">
            <label>Enter city:</label>
            <input type="text" id="cityInput" autocomplete="off" class="profile-input"/>
            <div id="suggestions" class="suggestions-box"></div>
        </div>

        <form action="/profile/edit/city" method="post" id="cityForm">
            <input type="hidden" id="cityHidden" name="city"/>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="profile-button" type="submit">Save</button>
        </form>

    </div>

</div>

<div class="back-link">
    <a href="/profile">Back to Profile</a>
</div>

<script src="/assets/js/edit-profile.js"></script>

</body>
</html>