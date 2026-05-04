<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Создание мероприятия</title>
    <link rel="stylesheet" href="/assets/css/create-event.css">
</head>
    <body class="event-body">

        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

        <div class="event-div">

            <h2 class="event-h2">Создать мероприятие</h2>

            <form action="${pageContext.request.contextPath}/events/create" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <label class="event-label">Название:</label>
                    <input class="event-input" type="text" name="title" required>
                </div>

                <div class="form-group">
                    <label class="event-label">Описание:</label>
                    <textarea class="event-textarea" name="description"></textarea>
                </div>

                <div class="form-group">
                    <label class="event-label">Дата и время:</label>
                    <input class="event-input" type="datetime-local" name="eventDate" required>
                </div>

                <div class="form-group">
                    <label class="event-label">Адрес:</label>
                    <input class="event-input" type="text" name="address" required>
                </div>

                <div class="form-group">
                    <label class="event-label">Макс. участников:</label>
                    <input class="event-input" type="number" name="maxParticipants">
                </div>

                <button class="event-button" type="submit">Создать</button>

            </form>

        </div>

    </body>
</html>