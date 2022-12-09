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

@WebServlet(urlPatterns = "/admin/upgrade")
public class UpgradeUserServlet extends HttpServlet {

    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Users updradedUser = usersDAO.findById(Long.parseLong(req.getParameter("idUser"))).get();

        System.out.println(Long.parseLong(req.getParameter("idUser")));

        Role admin = roleDAO.findById(2L).get();

        if (!updradedUser.getRoles().contains(admin)){
            updradedUser.addRole(admin);
            usersDAO.update(updradedUser);
        }

        resp.sendRedirect(UserListServlet.URL);

    }

}
