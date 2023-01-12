<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Agendo - Add a user</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/asset/css/global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/add.css" />" rel="stylesheet" type="text/css">
    <script src="https://kit.fontawesome.com/21e53ce249.js" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="username" value="${username}"/>
</jsp:include>
<main>
    <div class="container">
        <div class="user-exists ${isExist ? 'display': ''}">
            <span>User already exists !</span>
            <i class="close fa-solid fa-xmark"></i>
        </div>
        <div class="title">
            <h2>
                Create Account
            </h2>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/add">
            <div>
                <div class="form__group">
                    <input class="form__field" id="username" name="username" value="${username}" type="text"
                           placeholder="Username" required>
                    <label class="form__label" for="username">Username</label>
                    <div class="invalid-input ${nameNotValid ? 'display':''}">
                        Username is not valid !
                    </div>
                </div>
                <div class="form__group">
                    <input class="form__field" id="email" name="email" value="${email}" type="text" placeholder="Email"
                           required>
                    <label class="form__label" for="email">Email</label>
                    <div class="invalid-input ${emailNotValid ? 'display':''}">
                        Email is not valid !
                    </div>
                </div>
                <div class="form__group">
                    <input class="form__field" id="password" name="password" value="${password}" type="password"
                           placeholder="Password"
                           required>
                    <label class="form__label" for="password">Password</label>
                    <div class="invalid-input ${passwordNotValid ? 'display':''}">
                        Password must not be empty and at least 5 characters !
                    </div>
                </div>

                <div class="form__group form__checkbox">
                    <span>Set as Admin</span>
                    <label class="toggler-wrapper style-1">
                        <input type="checkbox" id="setadmin" name="isAdmin" <c:if test="${isAdmin}">checked</c:if>>
                        <div class="toggler-slider">
                            <div class="toggler-knob"></div>
                        </div>
                    </label>
                </div>
            </div>

            <div>
                <div class="form__group">
                    <input class="form__field" id="city" name="city" value="${city}" type="text" placeholder="City"
                           required>
                    <label class="form__label" for="city">City</label>
                    <div class="invalid-input ${cityNotValid ? 'display':''}">
                        City is not valid !
                    </div>
                </div>
                <div class="form__group">
                    <input class="form__field" id="zipcode" name="zipCode" value="${zipCode}" type="text"
                           placeholder="Zip Code" required>
                    <label class="form__label" for="zipcode">Zip Code</label>
                    <div class="invalid-input ${postalCodeNotValid ? 'display':''}">
                        Zipcode is not valid !
                    </div>
                </div>
            </div>

            <button>Add new user</button>

        </form>
    </div>
</main>


</body>
<script type="text/javascript" src="<c:url value="/asset/js/main.js" />"></script>
</html>
