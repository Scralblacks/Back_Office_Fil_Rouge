package servlet;

import Entity.Users;
import dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/deactivate")
public class DeactivateUserServlet extends HttpServlet {

    UsersDAO usersDAO = new UsersDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Users deactivitedUser = usersDAO.findById(Long.parseLong(req.getParameter("idUser"))).get();

        deactivitedUser.setActivated(false);

        usersDAO.update(deactivitedUser);

        resp.sendRedirect(UserListServlet.URL);

    }
}
