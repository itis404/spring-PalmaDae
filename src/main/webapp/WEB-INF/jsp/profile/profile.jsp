<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>

        <h1>${username}</h1>
        <h1>${email}</h1>
        <h1>${bloodType}</h1>
        <h1>${city}</h1>

        <p><a href="/profile/edit">Edit Profile</a></p>

        <p><a href="/profile/add-donation">Add Donation</a></p>
        <p><a href="/home">Go Home</a></p>
        <p><a href="/events/create">Create event</a></p>
    </body>
</html>
