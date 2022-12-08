<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>List od users</title>
</head>
<body>

<form>
    <table>
        <thread>
            <tr>
                <th>Id</th>
                <th>Pseudo</th>
                <th>Email</th>
                <th>Date of last Login</th>
                <th>Role</th>
                <c:choose>
                    <c:when test="${isSuperAdmin}">
                        <th colspan="3">Action</th>
                    </c:when>
                    <c:otherwise>
                        <th colspan="2">Action</th>
                    </c:otherwise>
                </c:choose>
            </tr>
        </thread>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <input type="text" value="${user.idUser}">
                </tr>
                <tr>
                    <input type="text" value="${user.pseudo}">
                </tr>
                <tr>
                    <input type="text" value="${user.email}">
                </tr>
                <tr>
                    <input type="date" value="${user.dateLastLogin}">
                </tr>
                <tr>
                    <c:forEach var="role" items="${user.roles}">
                        ${role.name}
                    </c:forEach>
                </tr>
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/admin/dashbord">
                        <input type="hidden" value="${user.id}" name="idUser">
                        <button class="btn btn-update">Update user</button>
                    </form>
                </tr>
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/admin/dashbord">
                        <input type="hidden" value="${user.id}" name="idUser">
                        <button class="btn btn-delete">Delete user</button>
                    </form>
                </tr>
                <c:if test="${isSuperAdmin}">
                    <tr>
                        <form method="post" action="${pageContext.request.contextPath}/admin/dashbord">
                            <input type="hidden" value="${user.id}" name="idUser">
                            <button class="btn btn-delete">Upgrade to admin</button>
                        </form>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>

    <button>Update all</button>

</form>

</body>
</html>