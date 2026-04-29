<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <link rel="stylesheet" href="/assets/css/auth.css">
</head>

<body class="auth-body">
<div class="auth-div">

    <h2 class="auth-h1">Registration Form</h2>

    <form:errors path="*" cssClass="error-block"/>

    <form:form method="post"
               modelAttribute="userForm"
               action="${pageContext.request.contextPath}/auth/registration">

        <div class="form-group">

            <label class="auth-label">Login:</label>
            <form:input path="username" cssClass="auth-input"/>
            <form:errors path="username" cssClass="error"/>

        </div>

        <div class="form-group">

            <label class="auth-label">Password:</label>
            <form:input path="password" type="password" cssClass="auth-input"/>
            <form:errors path="password" cssClass="error"/>

        </div>

        <div class="form-group">

            <label class="auth-label">Confirm Password:</label>
            <form:input path="passCorrect" type="password" cssClass="auth-input"/>
            <form:errors path="passCorrect" cssClass="error"/>

        </div>

        <div class="form-group">

            <label class="auth-label">Email:</label>
            <form:input path="email" type="email" cssClass="auth-input"/>
            <form:errors path="email" cssClass="error"/>

        </div>

        <button type="submit" class="auth-button">
            Registration
        </button>

    </form:form>

</div>

</body>