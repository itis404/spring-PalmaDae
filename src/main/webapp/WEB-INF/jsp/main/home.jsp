<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/assets/css/home.css">
</head>
    <body class="home-body">

        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

        <h2 class="home-title">Пункты сдачи крови в вашем городе</h2>

        <div class="stations-container">

            <c:forEach items="${stations}" var="s">

                <div class="station-card" onclick="toggleStation(${s.id})">

                    <h3 class="station-title">${s.title}</h3>
                    <p class="station-address">${s.address}</p>

                    <div class="blood-groups">
                        <span class="blood-badge ${s.o_plus}">O+</span>
                        <span class="blood-badge ${s.o_minus}">O-</span>
                        <span class="blood-badge ${s.a_plus}">A+</span>
                        <span class="blood-badge ${s.a_minus}">A-</span>
                        <span class="blood-badge ${s.b_plus}">B+</span>
                        <span class="blood-badge ${s.b_minus}">B-</span>
                        <span class="blood-badge ${s.ab_plus}">AB+</span>
                        <span class="blood-badge ${s.ab_minus}">AB-</span>
                    </div>

                    <div class="station-hint">Нажмите, чтобы увидеть подробную потребность</div>

                    <div class="station-details" id="station-${s.id}">
                        <p>Телефон: ${s.firstPhone}</p>
                        <p>Сайт: <a href="${s.site}" target="_blank">перейти</a></p>
                        <p>${s.registrationText}</p>

                        <a href="https://www.google.com/maps?q=${s.lat},${s.lng}" target="_blank">
                            Построить маршрут
                        </a>
                    </div>

                </div>

            </c:forEach>

        </div>

        <div class="logout-block">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="logout-link">Выйти</button>
            </form>
        </div>


        <script src="/assets/js/home.js"></script>
    </body>
</html>