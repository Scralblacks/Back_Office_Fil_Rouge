package servlet;

import Entity.Users;
import dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/admin/update")
public class UpdateUserServlet extends HttpServlet {

    UsersDAO userDao = new UsersDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long updatedId = Long.parseLong(req.getParameter("idUser"));
        String updatedUsername = req.getParameter("username");
        String updatedEmail = req.getParameter("email");

        Users updatedUser = userDao.findById(updatedId).get();

        if (!updatedUsername.isEmpty() && !updatedEmail.isEmpty()) {
            updatedUser.setUsername(updatedUsername);
            updatedUser.setEmail(updatedEmail);
            userDao.update(updatedUser);
        }


        resp.sendRedirect(UserListServlet.URL + "?page=1");

    }
}
