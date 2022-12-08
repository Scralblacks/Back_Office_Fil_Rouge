package dao;

import Entity.Action;
import Entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import utils.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDAO implements CrudDAO<Role> {


    @Override
    public List<Role> findAll() {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<Role> roles = new ArrayList<>();
        try {
            TypedQuery<Role> query = em.createQuery("select r from Role r", Role.class);
            roles = query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return roles;
    }

    @Override
    public Optional<Role> findById(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Role role = null;
        try {
            role = Optional.of(em.find(Role.class, id)).get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.of(role);
    }

    // No need
    @Override
    public boolean delete(Long id) {
        return false;
    }


    // No need
    @Override
    public Role update(Role element) {
        return null;
    }

    @Override
    public Role create(Role element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
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

    // No need
    @Override
    public Optional<Role> checkIfExists(Role element) {
        return Optional.empty();
    }
}
