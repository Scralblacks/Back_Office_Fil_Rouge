<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>List of users</title>
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
</head>
<body>


    <table class="styled-table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Pseudo</th>
                <th>Email</th>
                <th>Date of last Login</th>
                <th>Activated</th>
                <th>Role</th>
                <c:choose>
                    <c:when test="${isSuperAdmin}">
                        <th colspan="4">Action</th>
                    </c:when>
                    <c:otherwise>
                        <th colspan="3">Action</th>
                    </c:otherwise>
                </c:choose>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <form method="post" action="${pageContext.request.contextPath}/admin/update">
                <tr>
                <td>
                    <input type="text" name="idUser" value="${user.idUser}" readonly>
                </td>
                <td>
                    <input type="text" name="pseudo" value="${user.pseudo}">
                </td>
                <td>
                    <input type="text" name="email" value="${user.email}">
                </td>
                <td>
                    <input type="date" name="dateLastLogin" value="${user.dateLastLogin}" readonly>
                </td>
                    <td>
                        <span>
                            <c:choose>
                                <c:when test="${user.activated}">
                                    Activated
                                </c:when>
                                <c:otherwise>
                                    Deactivated
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                <td>
                    <c:forEach var="role" items="${user.roles}" >
                        ${role.name}
                    </c:forEach>
                </td>
                <td>
                    <button type="submit" class="btn btn-update">Update user</button>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/delete">
                        <input type="hidden" value="${user.idUser}" name="idUser">
                        <button class="btn btn-delete">Delete user</button>
                    </form>
                </td>
                 <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/deactivate">
                        <input type="hidden" value="${user.idUser}" name="idUser">
                        <button class="btn btn-deactive">Deactivate user</button>
                    </form>
                </td>
                <c:if test="${isSuperAdmin}">
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/upgrade">
                            <input type="hidden" value="${user.idUser}" name="idUser">
                            <button class="btn btn-upgrade">Upgrade to admin</button>
                        </form>
                    </td>
                </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>


</body>
</html>