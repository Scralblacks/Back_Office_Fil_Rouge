package servlet;

import Entity.Address;
import Entity.Planning;
import Entity.Role;
import Entity.Users;
import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Password_Hasher;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/admin/add")
public class AddUserServlet extends HttpServlet {

    UsersDAO usersDAO = new UsersDAO();
    PlanningDAO planningDAO = new PlanningDAO();
    RoleDAO roleDAO = new RoleDAO();
    AddressDAO addressDAO = new AddressDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(req.getContextPath() +"/WEB-INF/add-admin.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAdmin;
        String name = req.getParameter("pseudo");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String city = req.getParameter("city");
        String postalCode = req.getParameter("zipCode");
        if (req.getParameter("isAdmin") == null){
            isAdmin = false;
        } else {
            isAdmin = true;
        }

        Users user = new Users(email);
        Address userAddress = new Address(city, postalCode);
        Role basicRole = roleDAO.findById(1L).get();
        Role adminRole = roleDAO.findById(2L).get();
        Optional<Users> verifyUser = usersDAO.checkIfExists(user);

        if(verifyUser.isEmpty()) {

            // Creating him a planning with default name
            Planning planning = planningDAO.create(new Planning(name + "'s planning", LocalDate.now()));

            // Checking if user address is already registered
            Optional<Address> address = addressDAO.checkIfExists(userAddress);

            // If it doesn't exist, creates it
            if (!address.isPresent()) {
                userAddress = addressDAO.create(userAddress);
            } else {
                userAddress = address.get();
            }

            // For security purpose, we use a java security lib to hash the user password
            byte[] hash = new byte[16];
            try {
                hash = new Password_Hasher().h_password(password);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            // Completing the user entity
            user.setPseudo(name);
            user.setPassword(hash);
            user.setAddress(userAddress);
            user.setPlanning(planning);
            user.setPhoto(null);
            user.addRole(basicRole);
            if (isAdmin){
                user.addRole(adminRole);
            }
            user.setActivated(true);

            // Adding user to the database
            Users newUser1 = usersDAO.create(user);

            System.out.println("User created successfully !");

            // Verifying if user has been correctly added
            System.out.println("GETTING THE USER : ");
            Optional<Users> gettingThisOne = usersDAO.findById(newUser1.getIdUser());
            System.out.println(gettingThisOne);

        } else {
            System.out.println("User already exists !");
        }

        resp.sendRedirect(UserListServlet.URL + "?page=1");
    }
}