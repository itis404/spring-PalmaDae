<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Чат мероприятия</title>
    <link rel="stylesheet" href="/assets/css/chat.css">
</head>

    <body class="chat-body">

        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

        <div class="chat-container">

            <h2 class="chat-title">Чат мероприятия</h2>

            <div id="chat" class="chat-box">

                <c:forEach var="msg" items="${messages}">
                    <div class="chat-message">
                        <span class="chat-user">${msg.senderUsername}</span>
                        <span class="chat-text">${msg.message}</span>
                    </div>
                </c:forEach>

            </div>

            <form id="chatForm" class="chat-form">
                <input type="text" id="messageInput" class="chat-input" placeholder="Введите сообщение..." required>
                <button type="submit" class="chat-button">Отправить</button>
            </form>

        </div>

        <script>
            const eventId = "${eventId}";
        </script>
        <script src="/assets/js/event-chat.js"></script>

    </body>
</html>