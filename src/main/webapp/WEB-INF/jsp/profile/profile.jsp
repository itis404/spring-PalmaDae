<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Profile</title>
        <link rel="stylesheet" href="/assets/css/profile.css">
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />
    <div id="user-info" class="profile-div">
        <h1>${username}</h1>
        <h1>${email}</h1>
        <h1>${bloodType}</h1>
        <h1>${city}</h1>
    </div>

    <div class="profile-div">
        <a class="profile-button" href="/profile/edit">Edit Profile</a>
        <a class="profile-button" href="/profile/add-donation">Add donation</a>
        <a class="profile-button" href="/events/create">Create event</a>
    </div>
    </body>
</html>
