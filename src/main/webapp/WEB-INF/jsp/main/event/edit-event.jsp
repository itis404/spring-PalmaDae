<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Редактирование мероприятия</title>
</head>
<body>

<h2>Редактировать мероприятие</h2>

<form action="/events/edit/${event.id}" method="post">
    <div>
        <label>Название:</label>
        <input type="text" name="title" value="${event.title}" required>
    </div>

    <div>
        <label>Описание:</label>
        <textarea name="description">${event.description}</textarea>
    </div>

    <div>
        <label>Дата и время:</label>
        <input type="datetime-local" name="eventDate" value="${event.eventDate}" required>
    </div>

    <div>
        <label>Адрес:</label>
        <input type="text" name="address" value="${event.address}" required>
    </div>

    <div>
        <label>Макс. участников:</label>
        <input type="number" name="maxParticipants" value="${event.maxParticipants}">
    </div>

    <button type="submit">Сохранить</button>
</form>

</body>
</html>