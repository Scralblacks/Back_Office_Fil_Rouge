<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>List of users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/asset/css/global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/style.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/asset/css/dashboard.css" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/asset/js/main.js" />"></script>
    <script src="https://kit.fontawesome.com/21e53ce249.js" crossorigin="anonymous"></script>

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
                                    <c:set var="date" value="${user.dateLastLogin.toString()}" />
                                    <fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
                                    <span><fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" /></span>
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td class="priority-1">
                            <label class="toggler-wrapper style-1">
                                <input type="checkbox" onclick='triggerActivateUser(${user.idUser})'
                                       <c:if test="${user.activated}">checked</c:if>   >
                                <div class="toggler-slider">
                                    <div class="toggler-knob"></div>
                                </div>
                            </label>
                        </td>
                        <td class="flexable_td">
                            <c:forEach items="${user.roles}" var="currentRole" varStatus="stat">
                                <c:set var="sRoles" value="${stat.first ? '' : sRoles} ${currentRole.name}"/>
                            </c:forEach>
                            <div class="activation__group">
                                <div class="activation__field">
                                    <label class="toggler-wrapper style-1">
                                        <input type="checkbox" onclick='triggerActivateRole(${user.idUser},1)'
                                               <c:if test="${fn:contains(sRoles, 'BASIC')}">checked</c:if>   >
                                        <div class="toggler-slider">
                                            <div class="toggler-knob"></div>
                                        </div>
                                    </label>
                                    <span>BASIC</span>
                                </div>
                                <div class="activation__field">
                                    <label class="toggler-wrapper style-1">
                                        <input type="checkbox" onclick='triggerActivateRole(${user.idUser},2)'
                                               <c:if test="${fn:contains(sRoles, 'ADMIN')}">checked</c:if>   >
                                        <div class="toggler-slider">
                                            <div class="toggler-knob"></div>
                                        </div>
                                    </label>
                                    <span>ADMIN</span>
                                </div>
                            </div>
                            <c:if test="${fn:contains(sRoles,'SUPERADMIN')}">
                                <div class="superadmin__icon">
                                    <i class="fa-solid fa-medal fa-lg"></i>
                                </div>
                            </c:if>

                                <%--                            <span><c:forEach var="role" items="${user.roles}">--%>
                                <%--                                ${role.name}--%>
                                <%--                            </c:forEach></span>--%>
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
                            <button type="submit" class="update"><i class="fa-solid fa-upload"></i></button>
                        </td>
                    </form>
                    <c:choose>
                        <c:when test="${isSuperAdmin && !fn:contains(sRoles,'SUPERADMIN')}">
                            <td class="action_forms">
                                <div>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/delete">
                                        <input type="hidden" value="${user.idUser}" name="idUser">
                                        <button class="delete"><i class="fa-solid fa-user-minus"></i></button>
                                    </form>
                                </div>

                            </td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>

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
                <span class="active_pagination_number">${page}</span>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/admin/dashbord?page=${page}">${page}</a>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</footer>
</body>
</html>