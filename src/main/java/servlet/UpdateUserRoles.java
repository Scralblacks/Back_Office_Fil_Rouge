package servlet;

import Entity.Role;
import Entity.Users;
import dao.RoleDAO;
import dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/updateUserRoles")
public class UpdateUserRoles extends HttpServlet {

    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDao = new RoleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long userId = Long.parseLong(req.getParameter("idUser"));
        Long roleId = Long.parseLong(req.getParameter("idRole"));

        Users user = usersDAO.findById(userId).get();

        Role role = roleDao.findById(roleId).get();

        if(user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        } else {
            user.addRole(role);
        }

        usersDAO.update(user);

        resp.sendRedirect(UserListServlet.URL);

    }
}
