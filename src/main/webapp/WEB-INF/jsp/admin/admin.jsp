<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin Panel</title>
        <link rel="stylesheet" href="/assets/css/shared.css">
    </head>
    <body>
        <div class="block">
            <h3>Просмотр справок</h3>
            <c:forEach var="d" items="${inProgressDonations}">
                <div style="border:1px solid #ddd; padding:10px; margin-top:10px">

                    <p>ID: ${d.id}</p>
                    <p>Status: ${d.donationStatus}</p>
                    <p>Date: ${d.date}</p>
                    <img src="/certificate/${d.certificate}" style="max-width:200px;">

                    <form action="${pageContext.request.contextPath}/admin/update-status" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <input type="hidden" name="id" value="${d.id}"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

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

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <label>Date of donation</label>
                <form:input path="date" type="date" required="true"/>

                <button type="submit">Search</button>
            </form:form>

            <c:if test="${not empty donationsByDate}">
                <c:forEach var="d" items="${donationsByDate}">
                    <div style="border:1px solid #ddd; padding:10px; margin-top:10px">
                        <p>ID: ${d.id}</p>
                        <p>Status: ${d.donationStatus}</p>
                        <p>Date: ${d.date}</p>
                        <img src="/certificate/${d.certificate}" style="max-width:200px;">
                    </div>
                </c:forEach>
            </c:if>
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
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Одобрить</button>
                </form>

                <form action="${pageContext.request.contextPath}/admin/reject-event" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Отклонить</button>
                </form>

                <hr>
            </div>
        </c:forEach>

        <h2>Обновлённые мероприятия</h2>

        <c:forEach var="event" items="${updatedEvents}">
            <div>
                <p><b>Название:</b> ${event.title}</p>
                <p><b>Описание:</b> ${event.description}</p>
                <p><b>Дата:</b> ${event.eventDate}</p>
                <p><b>Адрес:</b> ${event.address}</p>
                <p><b>Организатор:</b> ${event.organizer.username}</p>

                <form action="${pageContext.request.contextPath}/admin/approve-event" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Одобрить</button>
                </form>

                <form action="${pageContext.request.contextPath}/admin/reject-event" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="eventId" value="${event.id}">
                    <button type="submit">Отклонить</button>
                </form>

                <hr>
            </div>
        </c:forEach>
    </body>
</html>
