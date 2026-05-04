<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Мои мероприятия</title>
<link rel="stylesheet" href="/assets/css/my-events.css">
</head>
    <body class="my-events-body">

    <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

    <h2 class="my-events-title">Мои созданные мероприятия</h2>

        <div class="events-container">

        <c:forEach var="event" items="${events}">

            <div class="event-card">

                <p><b>Название:</b> ${event.title}</p>
                <p><b>Описание:</b> ${event.description}</p>
                <p><b>Дата:</b> ${event.eventDate}</p>
                <p><b>Адрес:</b> ${event.address}</p>
                <p><b>Статус:</b> <span class="status ${event.status}">${event.status}</span></p>
                <p><b>Участников:</b> ${event.currentParticipants} / ${event.maxParticipants}</p>

                <div class="event-buttons">
                    <form action="${pageContext.request.contextPath}/events/chat/${event.id}" method="get">
                        <button class="btn chat-btn">Открыть чат</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/events/edit/${event.id}" method="get">
                      <button class="btn edit-btn" type="submit">Редактировать</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/events/delete/${event.id}" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                      <button class="btn delete-btn" type="submit">Удалить</button>
                    </form>


                </div>

            </div>

        </c:forEach>

        </div>

    </body>
</html>