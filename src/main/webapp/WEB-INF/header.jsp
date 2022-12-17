<%--
  Created by IntelliJ IDEA.
  User: Fabrice
  Date: 17/12/2022
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="container">
        <span>Bienvenue <%= request.getParameter("username") %></span>
        <div class="onglet">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/dashbord?page=1">
                        <i class="fa-xl fa-solid fa-gauge"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/add">
                        <i class="fa-lg fa-solid fa-user-plus"></i>
                        <span>Nouvel Utilisateur</span>
                    </a>
                </li>
            </ul>
        </div>
        <form class="logout__form" method="post" action="${pageContext.request.contextPath}/logout">
            <button type="submit">
                <span>Logout</span>
                <img src="/asset/img/logout.png" alt="">
            </button>
        </form>
    </div>
</header>
