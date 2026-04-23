<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Edit Profile</title>

        <style>
            .block { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; border-radius: 8px; }
            .error { color: red; font-size: 12px; }
            .success { color: green; }

            label { display: block; margin-top: 10px; font-weight: bold; }
            input, select { width: 100%; max-width: 300px; padding: 5px; }
            button { margin-top: 10px; }

            .suggestions-box {
                border: 1px solid #ccc;
                max-width: 300px;
                background: white;
                position: absolute;
                z-index: 1000;
            }

            .suggestions-box div {
                padding: 8px;
                cursor: pointer;
            }

            .suggestions-box div:hover {
                background: #f0f0f0;
            }

            .city-wrapper {
                position: relative;
            }
        </style>
    </head>

    <body>

    <h1>Edit Profile</h1>

    <c:if test="${not empty successMessage}">
        <div class="success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>


    <div class="block">
        <h3>Change email</h3>
        <form action="/profile/edit/email" method="post">
            <label>New Email:</label>
            <input type="email" name="newEmail" required />
            <button type="submit">Update Email</button>
        </form>
    </div>


    <div class="block">
        <h3>Change Password</h3>
        <form action="/profile/edit/password" method="post">
            <label>Old Password:</label>
            <input type="password" name="oldPassword" required />

            <label>New Password:</label>
            <input type="password" name="newPassword" required />

            <label>Confirm New Password:</label>
            <input type="password" name="confirmPassword" required />

            <button type="submit">Change Password</button>
        </form>
    </div>


    <div class="block">
        <h3>Change Blood Type</h3>
        <form action="/profile/edit/bloodtype" method="post">
            <label>Blood Type:</label>
            <select name="bloodType">
                <option value="">-- Not specified --</option>
                <c:forEach items="${bloodTypes}" var="type">
                    <option value="${type}" ${type == currentBloodType ? 'selected' : ''}>
                            ${type}
                    </option>
                </c:forEach>
            </select>
            <button type="submit">Update Blood Type</button>
        </form>
    </div>


    <div class="block">
        <h3>Change City</h3>

        <div class="city-wrapper">
            <label>Enter city:</label>
            <input type="text" id="cityInput" autocomplete="off" />

            <div id="suggestions" class="suggestions-box"></div>
        </div>

        <form action="/profile/edit/city" method="post" id="cityForm">
            <input type="hidden" id="cityHidden" name="city" />

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button type="submit">Save</button>
        </form>
    </div>

    <p><a href="/profile">Back to Profile</a></p>

    <script>
        const input = document.getElementById("cityInput");
        const hidden = document.getElementById("cityHidden");
        const box = document.getElementById("suggestions");

        let timeout;

        input.addEventListener("input", () => {
            clearTimeout(timeout);

            timeout = setTimeout(async () => {
                const q = input.value;

                if (q.length < 2) {
                    box.innerHTML = "";
                    return;
                }

                const res = await fetch(
                    `/profile/edit/city/search?query=` + encodeURIComponent(q)
                );

                const data = await res.json();

                box.innerHTML = "";

                data.forEach(city => {
                    const div = document.createElement("div");
                    div.textContent = city;

                    div.onclick = () => {
                        input.value = city;
                        hidden.value = city;
                        box.innerHTML = "";
                    };

                    box.appendChild(div);
                });
            }, 300);
        });
    </script>

    </body>
</html>