<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add donation</title>
    <link rel="stylesheet" href="/assets/css/donation.css">
</head>

    <body class="donation-body">

        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />

        <div class="donation-div">

            <h2 class="donation-title">Добавить донацию</h2>

            <c:if test="${not empty successMessage}">
                <div class="success">${successMessage}</div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>

            <form:form
                    modelAttribute="donationDto"
                    action="${pageContext.request.contextPath}/profile/add-donation" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <label class="donation-label">Дата донации</label>
                    <input class="donation-input" type="date" name="date" required>
                    <form:errors path="date" cssClass="error" />
                </div>

                <div class="form-group">
                    <label class="donation-label">Тип донации</label>
                    <select class="donation-input" name="donationType">
                        <option value="">Не выбрано</option>
                        <c:forEach items="${donationTypes}" var="type">
                            <option value="${type}" ${type == currentDonationType ? 'selected' : ''}>
                                    ${type}
                            </option>
                        </c:forEach>
                    </select>
                    <form:errors path="donationType" cssClass="error" />
                </div>

                <div class="form-group">
                    <label class="donation-label">Загрузить справку? (необязательно)</label>
                    <input class="donation-input" type="file" name="certificateFile">
                </div>

                <button class="donation-button" type="submit">Добавить справку</button>

            </form:form>



        </div>

        <div class="back-link">
            <a href="/profile">Обратно в профиль</a>
        </div>

    </body>
</html>