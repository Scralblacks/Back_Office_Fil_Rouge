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

@WebServlet(urlPatterns = "/admin/downgrade")
public class DowngradeUserServlet extends HttpServlet {

    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Users downgradedUser = usersDAO.findById(Long.parseLong(req.getParameter("idUser"))).get();

        System.out.println(Long.parseLong(req.getParameter("idUser")));

        Role admin = roleDAO.findById(2L).get();

        if (downgradedUser.getRoles().contains(admin)){
            downgradedUser.deleteRole(admin);
            usersDAO.update(downgradedUser);
            if (downgradedUser.getRoles().size() == 0){
                usersDAO.delete(downgradedUser.getIdUser());
            }
        }

        resp.sendRedirect(UserListServlet.URL);

    }

}
