<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Чат мероприятия</title>
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

    <h2>Чат мероприятия</h2>

    <div id="chat">
        <c:forEach var="msg" items="${messages}">
            <p>
                <b>${msg.senderUsername}</b>: ${msg.message}
            </p>
        </c:forEach>
    </div>

    <hr>

    <form id="chatForm">
        <input type="text" id="messageInput" required>
        <button type="submit">Отправить</button>
    </form>

    <script>
        const eventId = "${eventId}";
    </script>
    <script src="/assets/js/event-chat.js"></script>
    </body>
</html>