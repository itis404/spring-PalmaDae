<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <style>
        .form-group {
            margin-bottom: 15px;
        }
        .error {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input {
            display: block;
            width: 100%;
            max-width: 300px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            margin-top: 10px;
            padding: 10px 20px;
        }
    </style>
</head>

<body>

<h2>Login Form</h2>

<c:if test="${param.error != null}">
    <div class="error">
        Invalid username or password
    </div>
</c:if>

<form:errors path="*" cssClass="error-block"/>
<form action="<c:url value='/perform_login'/>" method="post">

    <div class="form-group">
        <label>Login:</label>
        <input type="text" name="username"/>
    </div>

    <div class="form-group">
        <label>Password:</label>
        <input type="password" name="password"/>
    </div>

    <button type="submit">
        Login
    </button>

</form>

</body>