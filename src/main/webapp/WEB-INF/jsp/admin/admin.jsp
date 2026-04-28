<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin Panel</title>
        <link rel="stylesheet" href="/assets/css/shared.css">
    </head>
    <body>
        <div class="block">
            <h3>Просмотр справок</h3>
            <form action="admin/certificate">

            </form>
            <c:forEach var="d" items="${donations}">
                <div style="border:1px solid #ddd; padding:10px; margin-top:10px">

                    <p>ID: ${d.id}</p>
                    <p>Status: ${d.donationStatus}</p>
                    <p>Date: ${d.date}</p>

                    <form action="/admin/update-status" method="post">

                        <input type="hidden" name="id" value="${d.id}"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <select name="status">
                            <option value="IN_PROGRESS" ${d.donationStatus == 'IN_PROGRESS' ? 'selected' : ''}>IN_PROGRESS</option>
                            <option value="CONFIRMED" ${d.donationStatus == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                            <option value="DECLINED" ${d.donationStatus == 'DECLINED' ? 'selected' : ''}>DECLINED</option>
                            <option value="WITHOUT" ${d.donationStatus == 'WITHOUT' ? 'selected' : ''}>WITHOUT</option>
                        </select>

                        <img src="">

                        <button type="submit">Update</button>
                    </form>

                </div>
            </c:forEach>
        </div>
        <div class="block">
            <h3>
                Просмотр по дате
            </h3>
            <form action="admin/date">
                <label>
                    Date of donation
                </label>
                <input type="date" name="date" required>
                <button type="submit"></button>
            </form>
        </div>

        <h2>Мероприятия на модерации</h2>

        <c:if test="${empty pendingEvents}">
            <p>Нет мероприятий на проверке</p>
        </c:if>

        <c:forEach var="event" items="${pendingEvents}">
            <div>
                <p><b>Название:</b> ${event.title}</p>
                <p><b>Описание:</b> ${event.description}</p>
                <p><b>Дата:</b> ${event.eventDate}</p>
                <p><b>Адрес:</b> ${event.address}</p>
                <p><b>Организатор:</b> ${event.organizer.username}</p>

                <form action="${pageContext.request.contextPath}/admin/approve-event" method="post">
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Одобрить</button>
                </form>

                <form action="${pageContext.request.contextPath}/admin/reject-event" method="post">
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Отклонить</button>
                </form>

                <hr>
            </div>
        </c:forEach>
    </body>
</html>
