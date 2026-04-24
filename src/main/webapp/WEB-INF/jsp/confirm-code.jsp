
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Confirm Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
        }
        .container {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #ffeeee;
            border: 1px solid red;
            border-radius: 4px;
        }
        .info {
            color: #666;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Confirm Registration</h2>
    <p class="info">We've sent a verification code to your email address. Please enter it below.</p>

    <c:if test="${not empty error}">
        <div class="error">
                ${error}
        </div>
    </c:if>

    <form action="/auth/registration/confirm" method="post">
        <input type="hidden" name="email" value="${pendingUser.email}">

        <div class="form-group">
            <label for="code">Verification Code:</label>
            <input type="text" id="code" name="code" placeholder="Enter 6-digit code" required autofocus>
        </div>

        <button type="submit">Verify Code</button>
    </form>
</div>
</body>
</html>