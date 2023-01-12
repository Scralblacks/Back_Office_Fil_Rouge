<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Agendo - Login Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/asset/css/global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/login.css" />" rel="stylesheet" type="text/css">
</head>
<body>
<main>
    <div class="container">
        <div class="section">
            <div class="introduction">
                <h2>Welcome Agendo Admin</h2>
                <p>From your dashboard, access your rights and manage users.</p>
                <c:if test="${loginFail}">
                    <h1>Log in failed</h1>
                </c:if>
            </div>


            <div class="container_form">
                <form method="post" action="${pageContext.request.contextPath}/admin_login">
                    <div class="form__group">
                        <input class="form__field" id="email" name="email" type="text" placeholder="Email"
                               value="fabrice@fabrice.fr" required>
                        <label class="form__label" for="email">Email</label>
                    </div>
                    <div class="form__group">
                        <input class="form__field" id="password" name="password" type="password" placeholder="Password"
                               value="azerty" required>
                        <label class="form__label" for="password">Password</label>
                    </div>

                    <button>Log in</button>

                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
