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

    public static final String URL = "/admin/dashbord?page=1";
    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        String spageid=req.getParameter("page");
        int pageid=Integer.parseInt(spageid);
        int total = 5;
        if(pageid==1){}
        else{
            pageid=pageid-1;
            pageid=pageid*total+1;
        }

        Long countUsers = usersDAO.count();
        int numberPages = (int) Math.ceil((double)countUsers / total);

        Role superAdmin = roleDAO.findById(3L).get();
        Users user = usersDAO.findById((Long) session.getAttribute("userId")).get();

        boolean isSuperAdmin = user.getRoles().contains(superAdmin);

        List<Users> users = usersDAO.getChunk(pageid,total);

        req.setAttribute("users", users);
        req.setAttribute("admin", user);
        req.setAttribute("isSuperAdmin", isSuperAdmin);
        req.setAttribute("username", user.getUsername());
        req.setAttribute("numberPage", numberPages);
        req.setAttribute("currentPage", Integer.parseInt(spageid));

        req.getRequestDispatcher(req.getContextPath() +"/WEB-INF/dashboard.jsp").forward(req, resp);

    }
}
