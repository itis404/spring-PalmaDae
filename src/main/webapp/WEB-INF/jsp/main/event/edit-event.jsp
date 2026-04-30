<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Редактирование мероприятия</title>
    <link rel="stylesheet" href="/assets/css/create-event.css">
</head>

<body class="event-body">

<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<div class="event-div">

    <h2 class="event-h2">Редактировать мероприятие</h2>

    <form action="/events/edit/${event.id}" method="post">

        <div class="form-group">
            <label class="event-label">Название:</label>
            <input class="event-input" type="text" name="title" value="${event.title}" required>
        </div>

        <div class="form-group">
            <label class="event-label">Описание:</label>
            <textarea class="event-textarea" name="description">${event.description}</textarea>
        </div>

        <div class="form-group">
            <label class="event-label">Дата и время:</label>
            <input class="event-input" type="datetime-local" name="eventDate" value="${event.eventDate}" required>
        </div>

        <div class="form-group">
            <label class="event-label">Адрес:</label>
            <input class="event-input" type="text" name="address" value="${event.address}" required>
        </div>

        <div class="form-group">
            <label class="event-label">Макс. участников:</label>
            <input class="event-input" type="number" name="maxParticipants" value="${event.maxParticipants}">
        </div>

        <button class="event-button" type="submit">Сохранить</button>

    </form>

</div>

</body>
</html>