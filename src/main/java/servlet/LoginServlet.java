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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(urlPatterns = "/admin/login")
public class LoginServlet extends HttpServlet {

    public static final String URL = "/admin/login";
    UsersDAO usersDAO = new UsersDAO();
    RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session != null && session.getAttribute("userId") != null){
//            resp.sendRedirect(UserListServlet.URL);
        } else {
            req.getRequestDispatcher("/WEB-INF/login_admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        Users checkUser = new Users(req.getParameter("email"));
        Optional<Users> user = usersDAO.checkIfExists(checkUser);
        String password = req.getParameter("password");
        boolean isAdmin = false;

        // For security purpose, we use a java security lib to hash the user password
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = new byte[0];
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Role admin = roleDAO.findById(2L).get();
        Role superAdmin = roleDAO.findById(3L).get();

        if(Arrays.equals(user.get().getPassword(), hash)
                && user.isPresent()
                && (user.get().getRoleList().contains(admin) || user.get().getRoleList().contains(superAdmin))
        ){

            HttpSession sessionStart = req.getSession();
            System.out.println(sessionStart);
            sessionStart.setAttribute("userId", user.get().getIdUser());
            // Expiration of session after 30min
            sessionStart.setMaxInactiveInterval(30*60);
//            resp.sendRedirect(UserListServlet.URL);

        } else {
            req.setAttribute("loginFail", true);
            doGet(req, resp);
        }
    }
}
