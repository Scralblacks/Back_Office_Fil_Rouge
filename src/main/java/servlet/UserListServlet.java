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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/dashbord")
public class UserListServlet extends HttpServlet {

    public static final String URL = "/admin/dashbord";
    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        Role superAdmin = roleDAO.findById(2L).get();
        Users user = usersDAO.findById((Long) session.getAttribute("userId")).get();

        boolean isSuperAdmin = user.getRoles().contains(superAdmin);

        List<Users> users = usersDAO.findAll();

        req.setAttribute("users", users);
        req.setAttribute("isSuperAdmin", isSuperAdmin);

        req.getRequestDispatcher(req.getContextPath() +"/WEB-INF/dashboard.jsp").forward(req, resp);

    }
}
