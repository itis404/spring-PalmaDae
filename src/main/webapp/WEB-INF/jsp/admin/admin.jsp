<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Panel</title>

    <link rel="stylesheet" href="/assets/css/shared.css">
    <link rel="stylesheet" href="/assets/css/admin.css">
</head>

<body>

<div class="block">
    <h3>Просмотр справок</h3>

    <c:forEach var="d" items="${inProgressDonations}">
        <div class="donation-card">

            <p><b>ID:</b> ${d.id}</p>
            <p><b>Status:</b> ${d.donationStatus}</p>
            <p><b>Date:</b> ${d.date}</p>

            <img src="${pageContext.request.contextPath}/certificate/${d.certificate}">

            <form action="${pageContext.request.contextPath}/admin/update-status" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${d.id}"/>

                <select name="status">
                    <option value="IN_PROGRESS" ${d.donationStatus == 'IN_PROGRESS' ? 'selected' : ''}>IN_PROGRESS</option>
                    <option value="CONFIRMED" ${d.donationStatus == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                    <option value="DECLINED" ${d.donationStatus == 'DECLINED' ? 'selected' : ''}>DECLINED</option>
                    <option value="WITHOUT" ${d.donationStatus == 'WITHOUT' ? 'selected' : ''}>WITHOUT</option>
                </select>

                <button type="submit">Update</button>
            </form>

        </div>
    </c:forEach>
</div>


<div class="block">
    <h3>Просмотр по дате</h3>

    <form:form method="post"
               modelAttribute="dateDto"
               action="${pageContext.request.contextPath}/admin/date">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <label>Дата донации</label>
        <form:input path="date" type="date" required="true"/>

        <button type="submit">Search</button>
    </form:form>

    <c:if test="${not empty donationsByDate}">
        <c:forEach var="d" items="${donationsByDate}">
            <div class="donation-card">

                <p><b>ID:</b> ${d.id}</p>
                <p><b>Status:</b> ${d.donationStatus}</p>
                <p><b>Date:</b> ${d.date}</p>

                <img src="${pageContext.request.contextPath}/certificate/${d.certificate}">
            </div>
        </c:forEach>
    </c:if>
</div>


<div class="block">
    <h3>Мероприятия на модерации</h3>

    <c:if test="${empty pendingEvents}">
        <p class="empty">Нет мероприятий на проверке</p>
    </c:if>

    <c:forEach var="event" items="${pendingEvents}">
        <div class="event-card">

            <p><b>Название:</b> ${event.title}</p>
            <p><b>Описание:</b> ${event.description}</p>
            <p><b>Дата:</b> ${event.eventDate}</p>
            <p><b>Адрес:</b> ${event.address}</p>
            <p><b>Организатор:</b> ${event.organizer.username}</p>

            <form action="${pageContext.request.contextPath}/admin/approve-event" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="eventId" value="${event.id}">
                <button class="approve" type="submit">Одобрить</button>
            </form>

            <form action="${pageContext.request.contextPath}/admin/reject-event" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="eventId" value="${event.id}">
                <button class="reject" type="submit">Отклонить</button>
            </form>

        </div>
    </c:forEach>
</div>

<div class="block">
    <h3>Обновлённые мероприятия</h3>

    <c:forEach var="event" items="${updatedEvents}">
        <div class="event-card">

            <p><b>Название:</b> ${event.title}</p>
            <p><b>Описание:</b> ${event.description}</p>
            <p><b>Дата:</b> ${event.eventDate}</p>
            <p><b>Адрес:</b> ${event.address}</p>
            <p><b>Организатор:</b> ${event.organizer.username}</p>

            <form action="${pageContext.request.contextPath}/admin/approve-event" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="eventId" value="${event.id}">
                <button class="approve" type="submit">Одобрить</button>
            </form>

            <form action="${pageContext.request.contextPath}/admin/reject-event" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="eventId" value="${event.id}">
                <button class="reject" type="submit">Отклонить</button>
            </form>

        </div>
    </c:forEach>
</div>

</body>
</html>