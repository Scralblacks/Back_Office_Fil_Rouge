package servlet;

import Entity.Users;
import dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/delete")
public class DeleteUserServlet extends HttpServlet {

    UsersDAO userDao = new UsersDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        userDao.delete(Long.parseLong(req.getParameter("idUser")));

        resp.sendRedirect(UserListServlet.URL + "?page=1");
    }
}
