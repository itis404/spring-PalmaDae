<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Welcome, ${username}!</h1>
<p>Your role: ${role}</p>

<hr/>

<h2>Blood donation stations in your city</h2>

<c:forEach items="${stations}" var="s">

    <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px; border-radius:8px;">

        <h3>${s.title}</h3>
        <p>${s.address}</p>

        <div>
            <c:forEach items="${s.bloodGroup}" var="bg">

                <span style="
                        padding:4px 8px;
                        margin:2px;
                        display:inline-block;
                        color:white;
                        border-radius:5px;
                        background:${bg == 'o_plus' || bg == 'o_minus' ? 'red' : 'green'};">
                        ${bg}
                </span>

            </c:forEach>
        </div>

    </div>

</c:forEach>

<a href="${pageContext.request.contextPath}/logout">Logout</a>
<a href="${pageContext.request.contextPath}/profile">Go to Profile</a>
</body>
</html>