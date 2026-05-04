<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Profile</title>
        <link rel="stylesheet" href="/assets/css/profile.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/shared/header.jsp" />
        <div id="user-info" class="profile-div">
            <h1>${profile.username}</h1>
            <h1>${profile.bloodType}</h1>
            <h1>${profile.city}</h1>

        </div>
        <div class="profile-div">
            <a class="profile-button" href="/profile/edit">Редактировать профиль</a>
            <a class="profile-button" href="/profile/add-donation">Добавить донацию</a>
            <a class="profile-button" href="/events/create">Создать мероприятие</a>
        </div>

        <div class="donations-container">
            <c:forEach var="donation" items="${profile.donations}">
                <div class="donation-card">
                    <div class="donation-header">
                        <span class="donation-type">${donation.donationType}</span>
                        <span class="donation-status status-${donation.donationStatus}">
                                ${donation.donationStatus}
                        </span>
                    </div>

                    <div class="donation-body">
                        <div class="donation-date">
                            Дата: ${donation.date}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </body>
</html>
