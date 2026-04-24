<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add donation</title>
        <link rel="stylesheet" href="/assets/css/shared.css">
    </head>
    <body>
        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <div class="block">
            <h3>Add donation</h3>
            <form action="/profile/add-donation" method="post" enctype="multipart/form-data">
                <label>
                    Date of donation
                </label>
                <input type="date" name="date" required>
                <label>
                    Donation type
                </label>
                <select name="donationType">
                    <option value="">Не выбрано</option>
                    <c:forEach items="${donationTypes}" var="type">
                        <option value="${type}" ${type == currentDonationType ? 'selected' : ''}>${type}</option>
                    </c:forEach>
                </select>

                <label>Certificate (optional)</label>
                <input type="file" name="certificateFile">


                <button type="submit">Add donation</button>
            </form>
        </div>

    </body>
</html>
