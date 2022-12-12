<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>List of users</title>
    <link href="<c:url value="/asset/css/global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/dashboard.css" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/asset/js/main.js" />"></script>

</head>

<body>
<header>
    <div class="container">
        <span>Bienvenue <c:out value="${username}"/></span>
        <form class="logout__form" method="post" action="${pageContext.request.contextPath}/logout">
            <button type="submit"><img src="/asset/img/logout.png" alt=""></button>
        </form>
    </div>
</header>

<main>
    <div class="container">
        <div class="dashboard_introduction">
            <div class="dash"></div>
            <h2>Gérez les utilisateurs de l'application depuis cette interface.</h2>
            <div class="dash"></div>
        </div>
        <table class="styled-table">
            <thead>
            <tr>
                <th class="priority-2">Id</th>
                <th class="priority-1">Pseudo</th>
                <th class="priority-1">Email</th>
                <th class="priority-2">Date of last Login</th>
                <th class="priority-1">Activated</th>
                <th class="priority-1">Role</th>
                <c:choose>
                    <c:when test="${isSuperAdmin}">
                        <th class="priority-1" colspan="2">Action</th>
                    </c:when>
                    <c:otherwise>
                        <th class="priority-1" colspan="2">Action</th>
                    </c:otherwise>
                </c:choose>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/admin/update">
                        <td class="priority-2">
                            <span>${user.idUser}</span>
                        </td>
                        <td class="priority-1">
                            <input class="form__field" type="text" name="pseudo" value="${user.pseudo}">
                        </td>
                        <td class="priority-1">
                            <input class="form__field" type="text" name="email" value="${user.email}">
                        </td>
                        <td class="priority-1">
                            <c:choose>
                                <c:when test="${empty user.dateLastLogin}">
                                    <span>Never</span>
                                </c:when>
                                <c:otherwise>
                                    <span>${user.dateLastLogin}</span>
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td class="priority-1">
                            <label class="toggler-wrapper style-1">
                                <input type="checkbox" onclick='triggerActivateUser(${user.idUser})' <c:if test="${user.activated}">checked</c:if>   >
                                <div class="toggler-slider">
                                    <div class="toggler-knob"></div>
                                </div>
                            </label>

<%--                        <span>--%>
<%--                            <c:choose>--%>
<%--                                <c:when test="${user.activated}">--%>
<%--                                    Activated--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    Deactivated--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
<%--                        </span>--%>
                        </td>
                        <td class="priority-1">
                            <span><c:forEach var="role" items="${user.roles}">
                                ${role.name}
                            </c:forEach></span>
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
                        <td class="priority-1">
                            <button type="submit" class="update">Update user</button>
                        </td>
                    </form>
                    <td class="action_forms">
<%--                            <form method="post" action="${pageContext.request.contextPath}/admin/deactivate">--%>
<%--                                <input type="hidden" value="${user.idUser}" name="idUser">--%>
<%--                                <button class="deactivate">Deactivate user</button>--%>
<%--                            </form>--%>
                            <c:if test="${isSuperAdmin}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/delete">
                                    <input type="hidden" value="${user.idUser}" name="idUser">
                                    <button class="delete">Delete user</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/admin/upgrade">
                                    <input type="hidden" value="${user.idUser}" name="idUser">
                                    <button class="upgrade">Upgrade to admin</button>
                                </form>

                                <form method="post" action="${pageContext.request.contextPath}/admin/downgrade">
                                    <input type="hidden" value="${user.idUser}" name="idUser">
                                    <button class="downgrade">Downgrade from admin</button>
                                </form>
                            </c:if>
                        </div>
                    </td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<footer>
    <c:forEach begin="1" end="${numberPage}" var="page">
        <c:choose>
            <c:when test="${page eq currentPage}">
                <span>${page}</span
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/admin/dashbord?page=${page}">${page}</a>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</footer>
</body>
</html>