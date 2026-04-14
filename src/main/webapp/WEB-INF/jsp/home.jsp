<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Welcome, ${username}!</h1>
<p>Your role: ${role}</p>

<a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>