<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Создание мероприятия</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<h2>Создать мероприятие</h2>

<form action="/events/create" method="post">
    <div>
        <label>Название:</label>
        <input type="text" name="title" required>
    </div>

    <div>
        <label>Описание:</label>
        <textarea name="description"></textarea>
    </div>

    <div>
        <label>Дата и время:</label>
        <input type="datetime-local" name="eventDate" required>
    </div>

    <div>
        <label>Адрес:</label>
        <input type="text" name="address" required>
    </div>

    <div>
        <label>Макс. участников:</label>
        <input type="number" name="maxParticipants">
    </div>

    <button type="submit">Создать</button>
</form>

</body>
</html>