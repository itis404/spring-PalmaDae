<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/assets/css/home.css">
</head>
<body class="home-body">

<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<h2 class="home-title">Blood donation stations in your city</h2>

<div class="stations-container">

    <c:forEach items="${stations}" var="s">

        <div class="station-card">

            <h3 class="station-title">${s.title}</h3>
            <p class="station-address">${s.address}</p>

            <div class="blood-groups">

                <c:forEach items="${s.bloodGroup}" var="bg">
                    <span class="blood-badge">${bg}</span>
                </c:forEach>

            </div>

        </div>

    </c:forEach>

</div>

<div class="logout-block">
    <a class="logout-link" href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

</body>
</html>