package dao;

import Entity.Users;
import Entity.Address;
import Entity.Planning;
import Entity.Role;
import jakarta.persistence.*;
import utils.ConnectionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersDAO implements CrudDAO<Users> {
    @Override
    public List<Users> findAll() {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<Users> users = null;
        try {
            TypedQuery<Users> query = em.createQuery("select u from Users u", Users.class);
            users = query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return users;
    }

    @Override
    public Optional<Users> findById(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Users user = null;
        try {
            TypedQuery<Users> query = em.createQuery("select u from Users u " +
                    "join fetch u.planning " +
                    "join fetch u.address " +
                    "join fetch u.roles " +
                    "join fetch u.share " +
                    "WHERE u.idUser=:id",
                    Users.class);
            query.setParameter("id",id);
            user = query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.of(user);
    }

    @Override
    public boolean delete(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean success = false;
        try {
            et.begin();
            Optional<Users> user = Optional.of(em.find(Users.class, id));
            em.remove(user.get());
            et.commit();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
        return success;
    }

    @Override
    public Users update(Users element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Users userToUpdate = null;
        try {
            et.begin();
            userToUpdate = Optional.of(em.find(Users.class, element.getIdUser())).get();
            userToUpdate.setPhoto(element.getPhoto());
            userToUpdate.setPassword(element.getPassword());
            userToUpdate.setEmail(element.getEmail());
            userToUpdate.setActivated(element.isActivated());
            userToUpdate.setRoles(element.getRoleList());
            em.merge(userToUpdate);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
        return userToUpdate;
    }

    @Override
    public Users create(Users element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Address a = em.find(Address.class, element.getAddress().getIdAddress());
            Planning p = em.find(Planning.class, element.getPlanning().getIdPlanning());
            List<Long> idsRole = element.getRoleList().stream().map(role -> role.getIdRole()).collect(Collectors.toList());
            TypedQuery<Role> query = em.createQuery("select r from Role r WHERE r.idRole in :idsRole", Role.class);
            query.setParameter("idsRole", idsRole);
            Set<Role> r = new HashSet<Role>(query.getResultList());
            element.setPlanning(p);
            element.setAddress(a);
            element.setRoles(r);

            em.persist(element);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
        return element;
    }

    @Override
    public Optional<Users> checkIfExists(Users element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<Users> usersList = null;
        try {
            TypedQuery<Users> query = em.createQuery("select u from Users u Join FETCH u.roles " +
                    "WHERE u.email=:e ", Users.class);
            query.setParameter("e", element.getEmail());
            usersList = query.getResultList();
        } catch (NoResultException e) {
            System.out.println("User doesn't exist. Need to create it first...");
        } finally {
            em.close();
        }

        return usersList.size() > 0 ? Optional.ofNullable(usersList.get(0)) : Optional.<Users>empty();
    }
}
