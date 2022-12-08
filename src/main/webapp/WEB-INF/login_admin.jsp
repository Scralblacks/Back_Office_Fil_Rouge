<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin</title>
    <%--  <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">--%>
</head>
<body>
<c:if test="${loginFail}">
    <h1>Log in failed</h1>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/admin/login">
    <label for="email">Email : </label>
    <input id="email" name="email" type="text" placeholder="email@email.com">

    <label for="password">Password : </label>
    <input id="password" name="password" type="password" placeholder="Password">

    <button>Log in</button>
</form>

</body>
</html>