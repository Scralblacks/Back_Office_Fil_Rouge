<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Agendo - Dashboard Admin</title>
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
                <th>Id</th>
                <th>Pseudo</th>
                <th>Email</th>
                <th>Date of last Login</th>
                <th>Activated</th>
                <th>Role</th>
                <th style="text-align: center">Save</th>
                <th style="text-align: center">Delete</th>
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
                            <input class="form__field" type="text" name="pseudo" value="${user.pseudo}">
                        </td>
                        <td>
                            <input class="form__field" type="text" name="email" value="${user.email}">
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty user.dateLastLogin}">
                                    <span>Never</span>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="date" value="${user.dateLastLogin}"/>
                                    <fmt:parseDate value="${date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                                   type="date"/>
                                    <span><fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm"/></span>
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <c:set var="sRoles" value=""/>
                        <c:forEach items="${user.roles}" var="currentRole" varStatus="stat">
                            <c:set var="sRoles" value="${stat.first ? '' : sRoles} ${currentRole.name}"/>
                        </c:forEach>
                        <td>
                            <label class="toggler-wrapper style-1">
                                <input type="checkbox" onclick='triggerActivateUser(${user.idUser})'
                                       <c:if test="${user.activated}">checked</c:if>
                                       <c:if test="${admin.idUser eq user.idUser}">disabled</c:if>   >
                                <div class="toggler-slider">
                                    <div class="toggler-knob">
                                        <c:if test="${admin.idUser eq user.idUser || fn:contains(sRoles, 'SUPERADMIN')}"><i
                                                class="fa-solid fa-xmark"></i></c:if>
                                    </div>
                                </div>
                            </label>
                        </td>
                        <td class="flexable_td">
                            <c:set var="isDisabled"
                                   value="${admin.idUser eq user.idUser || fn:contains(sRoles, 'SUPERADMIN')}"/>
                            <div class="activation__group">
                                <div class="activation__field">
                                    <label class="toggler-wrapper style-1">
                                        <input type="checkbox" onclick='triggerActivateRole(${user.idUser},1)'
                                               <c:if test="${fn:contains(sRoles, 'BASIC')}">checked</c:if>
                                               <c:if test="${isDisabled}">disabled</c:if>   >
                                        <div class="toggler-slider">
                                            <div class="toggler-knob">
                                                <c:if test="${isDisabled}"><i class="fa-solid fa-xmark"></i></c:if>
                                            </div>
                                        </div>
                                    </label>
                                    <span>BASIC</span>
                                </div>
                                <div class="activation__field">
                                    <label class="toggler-wrapper style-1">
                                        <input type="checkbox" onclick='triggerActivateRole(${user.idUser},2)'
                                               <c:if test="${fn:contains(sRoles, 'ADMIN')}">checked</c:if>
                                               <c:if test="${isDisabled}">disabled</c:if>  >
                                        <div class="toggler-slider">
                                            <div class="toggler-knob">
                                                <c:if test="${isDisabled}"><i class="fa-solid fa-xmark"></i></c:if>
                                            </div>
                                        </div>
                                    </label>
                                    <span>ADMIN</span>
                                </div>
                            </div>
                            <c:if test="${fn:contains(sRoles,'SUPERADMIN')}">
                                <div class="tooltip superadmin__icon">
                                    <i class="fa-solid fa-medal fa-lg"></i>
                                    <span class="tooltiptext">Super-Admin</span>
                                </div>
                            </c:if>
                        </td>
                        <td>
                            <input type="hidden" value="${user.idUser}" name="idUser">
                            <button type="submit" class="update"><i class="fa-solid fa-floppy-disk"></i></button>
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
        <a class="ui-button button-add" href="${pageContext.request.contextPath}/admin/add">Add user</a>
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