<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Все мероприятия</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<h2>Доступные мероприятия</h2>

<c:forEach var="event" items="${events}">
    <hr>

    <p><b>Название:</b> ${event.title}</p>
    <p><b>Описание:</b> ${event.description}</p>
    <p><b>Дата:</b> ${event.eventDate}</p>
    <p><b>Адрес:</b> ${event.address}</p>
    <p><b>Участников:</b> ${event.currentParticipants} / ${event.maxParticipants}</p>
    <p><b>Организатор:</b> ${event.organizer.username}</p>

    <form action="/events/join/${event.id}" method="post">
        <button type="submit">Присоединиться</button>
    </form>

    <form action="/events/leave/${event.id}" method="post">
        <button type="submit">Покинуть</button>
    </form>

    <form action="/events/chat/${event.id}" method="get">
        <button type="submit">Открыть чат</button>
    </form>

</c:forEach>

</body>
</html>