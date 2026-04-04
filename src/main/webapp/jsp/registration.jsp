<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>

</head>

<body>
    <form:form method="post" modelAttribute="userForm" action="${pageContext.request.contextPath}/registration">
        <h2>
            Reg
        </h2>
        <form:input path="username"/>
        <form:input path="password"/>
        <button type="submit">
            Reg
        </button>
    </form:form>
</body>