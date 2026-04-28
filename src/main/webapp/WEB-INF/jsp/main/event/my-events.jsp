<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Мои мероприятия</title>
</head>
<body>

<h2>Мои созданные мероприятия</h2>

<c:forEach var="event" items="${events}">
  <hr>

  <p><b>Название:</b> ${event.title}</p>
  <p><b>Описание:</b> ${event.description}</p>
  <p><b>Дата:</b> ${event.eventDate}</p>
  <p><b>Адрес:</b> ${event.address}</p>
  <p><b>Статус:</b> ${event.status}</p>
  <p><b>Участников:</b> ${event.currentParticipants} / ${event.maxParticipants}</p>

  <form action="/events/edit/${event.id}" method="get">
    <button type="submit">Редактировать</button>
  </form>

</c:forEach>

</body>
</html>