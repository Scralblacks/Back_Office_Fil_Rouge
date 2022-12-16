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
    <link href="<c:url value="/asset/css/dashboard.css" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/asset/js/main.js" />"></script>
    <script src="https://kit.fontawesome.com/21e53ce249.js" crossorigin="anonymous"></script>
</head>
<body>

    <form method="post" action="${pageContext.request.contextPath}/admin/add">

        <div>
            <label for="pseudo">Pseudo : </label>
            <input id="pseudo" type="text" name="pseudo" placeholder="Pseudo">

            <label for="email">Email : </label>
            <input id="email" type="text" name="email" placeholder="email@email.com">

            <label for="password">Password : </label>
            <input id="password" type="password" name="password" placeholder="Password">

            <label for="setadmin">Set as admin</label>
            <input id="setadmin" type="checkbox" name="isAdmin" value="true">
        </div>

        <div>
            <label for="city">City's name</label>
            <input id="city" type="text" name="city" placeholder="Name of City">

            <label for="zipcode">City's zip code</label>
            <input id="zipcode" type="text" name="zip_code" placeholder="Zip code">
        </div>

        <button>Add new user</button>

    </form>

</body>
</html>