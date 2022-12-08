import Entity.*;
import dao.CrudDAO;
import dao.DaoFactory;
import jakarta.persistence.EntityManagerFactory;
import utils.ConnectionManager;
import utils.Password_Hasher;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Peristence Manager
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();

        // Instantiate all DAOs
        CrudDAO<Users> userDao = DaoFactory.getUsersDao();
        CrudDAO<Planning> planningDao = DaoFactory.getPlanningDao();
        CrudDAO<Task> taskDao = DaoFactory.getTaskDao();
        CrudDAO<Role> roleDao = DaoFactory.getRoleDao();
        CrudDAO<Address> addressDao = DaoFactory.getAddressDao();
        CrudDAO<Action> actionDao = DaoFactory.getActionDao();

        // Setting all existing actions
        Action createAction = actionDao.create(new Action("CREATE"));
        Action updateAction = actionDao.create(new Action("UPDATE"));
        Action deleteAction = actionDao.create(new Action("DELETE"));

        // Setting all existing roles
        Role basicRole = roleDao.create(new Role("BASIC"));
        Role adminRole = roleDao.create(new Role("ADMIN"));
        Role superAdminRole = roleDao.create(new Role("SUPERADMIN"));

        // Creating an instance of the Address entity
        Address userAddress = new Address("Paris","75000");

        // Creating an instance of the User entity.
        // Then we verify if user already exists based on his email
        Users user = new Users("fabrice@fabrice.fr");
        Optional<Users> verifyUser = userDao.checkIfExists(user);

        // If he doesn't
        if(!verifyUser.isPresent()) {

            // Creating him a planning with default name
            Planning planning = planningDao.create(new Planning("Fabrice's planning", LocalDate.now()));

            // Getting the basic role (Optional here because we've instanced it above
            Role role = roleDao.findById(1L).get();

            // Checking if user address is already registered
            Optional<Address> address = addressDao.checkIfExists(userAddress);

            // If it doesn't exist, creates it
            if (!address.isPresent()) {
                userAddress = addressDao.create(userAddress);
            } else {
                userAddress = address.get();
            }

            // For security purpose, we use a java security lib to hash the user password
            byte[] hash = new byte[16];
            try {
                hash = new Password_Hasher().h_password("azerty");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            // Completing the user entity
            user.setPseudo("Fabrice");
            user.setPassword(hash);
            user.setAddress(userAddress);
            user.setPlanning(planning);
            user.setPhoto(null);
            user.addRole(basicRole);
            user.addRole(adminRole);
            user.addRole(superAdminRole);
            user.setActivated(true);

            // Adding user to the database
            Users newUser = userDao.create(user);

            System.out.println("User created successfully !");

            // Verifying if user has been correctly added
            System.out.println("GETTING THE USER : ");
            Optional<Users> gettingThisOne = userDao.findById(newUser.getIdUser());
            System.out.println(gettingThisOne);

        } else {
            System.out.println("User already exists !");
        }

       /* while (true) {
            System.out.println("1 - Voir le planning et ses tâches");
            System.out.println("2 - Voir une tâche en particulier");
            System.out.println("3 - Voir les utilisateurs");
            System.out.println("4 - Créer un utilisateur");
            System.out.println("5 - Créer une tâche pour un planning");
            System.out.println("6 - Supprimer une tâche");
            System.out.println("7 - Modifier une tâche");

            int choix = recupererEntier("");
            switch (choix) {
                case 1 :
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    List<Users> users = userDao.findAll();
                    if(!users.isEmpty()) {
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println((i + 1) + " - " + users.get(i).getPseudo());
                        }
                    } else {
                        System.out.println("Empty !");
                    }
                    break;


            }

        }*/


    }

    public static int recupererEntier(String message){
        Scanner sc = new Scanner(System.in);
        if(message.length() > 0) System.out.println(message);
        return sc.nextInt();
    }
    public static String recupererString(String message){
        Scanner sc = new Scanner(System.in);
        if(message.length() > 0) System.out.println(message);
        return sc.nextLine();
    }
}
