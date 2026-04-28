<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Чат мероприятия</title>
</head>
<body>

<h2>Чат мероприятия</h2>

<c:forEach var="msg" items="${messages}">
    <p>
        <b>${msg.senderUsername}</b>:
            ${msg.message}
        (${msg.sentAt})
    </p>
</c:forEach>

<hr>

<form action="/events/chat/${eventId}/send" method="post">
    <input type="text" name="message" required>
    <button type="submit">Отправить</button>
</form>

</body>
</html>