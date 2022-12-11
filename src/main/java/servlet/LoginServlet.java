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
import utils.Password_Hasher;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(urlPatterns = "/admin_login")
public class LoginServlet extends HttpServlet {

    public static final String URL = "/admin_login";
    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session != null && session.getAttribute("userId") != null){
            resp.sendRedirect(UserListServlet.URL);
        } else {
            req.getRequestDispatcher("/WEB-INF/login_admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Users checkUser = new Users(req.getParameter("email"));
        Optional<Users> user = usersDAO.checkIfExists(checkUser);
        String password = req.getParameter("password");
        boolean isAdmin = false;

        // For security purpose, we use a java security lib to hash the user password
        byte[] hash = new byte[16];
        try {
            hash = new Password_Hasher().h_password(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Role admin = roleDAO.findById(2L).get();
        System.out.println(admin);
        Role superAdmin = roleDAO.findById(3L).get();
        System.out.println(superAdmin);

        if(Arrays.equals(user.get().getPassword(), hash)
                && user.isPresent()
                && (user.get().getRoles().contains(admin) || user.get().getRoles().contains(superAdmin))
        ){
            user.get().setDateLastLogin(LocalDate.now());
            usersDAO.update(user.get());
            HttpSession sessionStart = req.getSession();
            System.out.println(sessionStart);
            sessionStart.setAttribute("userId", user.get().getIdUser());
            // Expiration of session after 30min
            sessionStart.setMaxInactiveInterval(30*60);
            resp.sendRedirect(UserListServlet.URL + "?page=1");

        } else {
            req.setAttribute("loginFail", true);
            doGet(req, resp);
        }
    }
}
