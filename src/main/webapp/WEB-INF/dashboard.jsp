<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>List of users</title>
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
</head>

<header>
    <h1>Hello ${admin.pseudo}</h1>
    <form method="post" action="${pageContext.request.contextPath}/logout">
        <button type="submit">Log out</button>
    </form>
</header>

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
                <th colspan="5">Action</th>
            </c:when>
            <c:otherwise>
                <th colspan="2">Action</th>
            </c:otherwise>
        </c:choose>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <form method="post" action="${pageContext.request.contextPath}/admin/update">
                <td>
                    <span>${user.idUser}</span>
                </td>
                <td>
                    <input type="text" name="pseudo" value="${user.pseudo}">
                </td>
                <td>
                    <input type="text" name="email" value="${user.email}">
                </td>
                <td>
                    <span>${user.dateLastLogin}</span>
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
                    <c:forEach var="role" items="${user.roles}">
                        ${role.name}
                    </c:forEach>
<%--                    <c:forEach var="role" items="${allRoles}">--%>
<%--                        <c:set var="testString" value="${role.name}"/>--%>
<%--                        <c:set var="testString" value="${user.roles.name}"/>--%>
<%--                        <label for="${role.name}"></label>--%>
<%--                        <input id="${role.name}" type="checkbox" value="${role.name}"--%>
<%--                            <c:if test="${fn:contains(${user.roles.name, role.name})}">--%>
<%--                                checked--%>
<%--                            </c:if>--%>
<%--                        >--%>
<%--                    </c:forEach>--%>
                </td>
                <td>
                    <button type="submit" class="btn btn-update">Update user</button>
                </td>
            </form>
            <c:if test="${isSuperAdmin}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/delete">
                        <input type="hidden" value="${user.idUser}" name="idUser">
                        <button class="btn btn-delete">Delete user</button>
                    </form>
                </td>
            </c:if>
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
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/admin/downgrade">
                        <input type="hidden" value="${user.idUser}" name="idUser">
                        <button class="btn btn-upgrade">Downgrade from admin</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>