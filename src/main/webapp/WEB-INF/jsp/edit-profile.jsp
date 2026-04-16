<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <style>
            .block { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; border-radius: 8px; }
            .error { color: red; font-size: 12px; }
            .success { color: green; }
            label { display: block; margin-top: 10px; font-weight: bold; }
            input, select { width: 100%; max-width: 300px; padding: 5px; }
            button { margin-top: 10px; }
        </style>
    </head>
    <body>
        <h1>Edit Profile</h1>

        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>


        <div class="block">
            <h3>Change email</h3>
            <form action="/profile/edit/email" method="post">
                <label>New Email:</label>
                <input type="email" name="newEmail" required />
                <button type="submit">Update Email</button>
            </form>
        </div>

        <div class="block">
            <h3>Change Password</h3>
            <form action="/profile/edit/password" method="post">
                <label>Old Password:</label>
                <input type="password" name="oldPassword" required />
                <label>New Password:</label>
                <input type="password" name="newPassword" required />
                <label>Confirm New Password:</label>
                <input type="password" name="confirmPassword" required />
                <button type="submit">Change Password</button>
            </form>
        </div>

        <div class="block">
            <h3>Change Blood Type</h3>
            <form action="/profile/edit/bloodtype" method="post">
                <label>Blood Type:</label>
                <select name="bloodType">
                    <option value="">-- Not specified --</option>
                    <c:forEach items="${bloodTypes}" var="type">
                        <option value="${type}" ${type == currentBloodType ? 'selected' : ''}>${type}</option>
                    </c:forEach>
                </select>
                <button type="submit">Update Blood Type</button>
            </form>
        </div>

        <p><a href="/profile">Back to Profile</a></p>

    </body>
</html>
