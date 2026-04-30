<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add donation</title>
    <link rel="stylesheet" href="/assets/css/donation.css">
</head>

<body class="donation-body">

<jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

<div class="donation-div">

    <h2 class="donation-title">Add donation</h2>

    <c:if test="${not empty successMessage}">
        <div class="success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <form action="/profile/add-donation" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label class="donation-label">Date of donation</label>
            <input class="donation-input" type="date" name="date" required>
        </div>

        <div class="form-group">
            <label class="donation-label">Donation type</label>
            <select class="donation-input" name="donationType">
                <option value="">Не выбрано</option>
                <c:forEach items="${donationTypes}" var="type">
                    <option value="${type}" ${type == currentDonationType ? 'selected' : ''}>
                            ${type}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label class="donation-label">Certificate (optional)</label>
            <input class="donation-input" type="file" name="certificateFile">
        </div>

        <button class="donation-button" type="submit">Add donation</button>

    </form>

</div>

</body>
</html>