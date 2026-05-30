<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

            <form:form modelAttribute="eventDto" action="${pageContext.request.contextPath}/events/edit/${eventDto.id}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <form:hidden path="id" />

                <div class="form-group">
                    <label class="event-label">Название:</label>
                    <form:input path="title" cssClass="event-input" required="true"/>
                    <form:errors path="title" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label class="event-label">Описание:</label>
                    <form:textarea path="description" cssClass="event-textarea"/>
                    <form:errors path="description" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label class="event-label">Дата и время:</label>
                    <form:input type="datetime-local" path="eventDate" cssClass="event-input" required="true"/>
                    <form:errors path="eventDate" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label class="event-label">Адрес:</label>
                    <form:input path="address" cssClass="event-input" required="true"/>
                    <form:errors path="address" cssClass="error"/>
                </div>

                <div class="form-group">
                    <label class="event-label">Макс. участников:</label>
                    <form:input type="number" path="maxParticipants" cssClass="event-input"/>
                    <form:errors path="maxParticipants" cssClass="error"/>
                </div>

                <button class="event-button" type="submit">Сохранить</button>
            </form:form>

        </div>

    </body>
</html>