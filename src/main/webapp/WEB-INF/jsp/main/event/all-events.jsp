<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Все мероприятия</title>
    <link rel="stylesheet" href="/assets/css/all-events.css">
</head>
    <body class="events-body">

        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

        <h2 class="events-title">Доступные мероприятия</h2>

        <div class="flash-container">

            <c:if test="${not empty success}">
                <div class="flash flash-success">
                        ${success}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="flash flash-error">
                        ${error}
                </div>
            </c:if>

        </div>

        <div class="events-container">

            <c:forEach var="event" items="${eventsApproved}">

                <div class="event-card">

                    <p><b>Название:</b> ${event.title}</p>
                    <p><b>Описание:</b> ${event.description}</p>
                    <p><b>Дата:</b> ${event.eventDate}</p>
                    <p><b>Адрес:</b> ${event.address}</p>
                    <p><b>Участников:</b> ${event.currentParticipants} / ${event.maxParticipants}</p>
                    <p><b>Организатор:</b> ${event.organizer.username}</p>

                    <div class="event-buttons">

                        <form action="${pageContext.request.contextPath}/events/join/${event.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="event-btn" type="submit">Присоединиться</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/events/leave/${event.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="event-btn leave-btn" type="submit">Покинуть</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/events/chat/${event.id}" method="get">
                            <button class="event-btn chat-btn" type="submit">Открыть чат</button>
                        </form>

                    </div>

                </div>

            </c:forEach>

            <c:forEach var="event" items="${eventsUpdated}">

                <div class="event-card">

                    <p><b>Название:</b> ${event.title}</p>
                    <p><b>Описание:</b> ${event.description}</p>
                    <p><b>Дата:</b> ${event.eventDate}</p>
                    <p><b>Адрес:</b> ${event.address}</p>
                    <p><b>Участников:</b> ${event.currentParticipants} / ${event.maxParticipants}</p>
                    <p><b>Организатор:</b> ${event.organizer.username}</p>

                    <div class="event-buttons">

                        <form action="${pageContext.request.contextPath}/events/join/${event.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="event-btn" type="submit">Присоединиться</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/events/leave/${event.id}" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="event-btn leave-btn" type="submit">Покинуть</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/events/chat/${event.id}" method="get">
                            <button class="event-btn chat-btn" type="submit">Открыть чат</button>
                        </form>

                    </div>

                </div>

            </c:forEach>

        </div>

    </body>
</html>