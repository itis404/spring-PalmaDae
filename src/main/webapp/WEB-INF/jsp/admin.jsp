<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin Panel</title>
        <style>
            .block { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; border-radius: 8px; }
            .error { color: red; font-size: 12px; }
            .success { color: green; }
            label { display: block; margin-top: 10px; font-weight: bold; }
            input, select { width: 100%; max-width: 300px; padding: 5px; }
            button { margin-top: 10px; }
        </style>
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
    </body>
</html>
