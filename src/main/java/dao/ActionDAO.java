package dao;

import Entity.Action;
import Entity.Planning;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import utils.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionDAO implements CrudDAO<Action> {
    @Override
    public List<Action> findAll() {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<Action> actions = new ArrayList<>();
        try {
            TypedQuery<Action> query = em.createQuery("select a from Action a", Action.class);
            actions = query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return actions;
    }

    @Override
    public Optional<Action> findById(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Action action = null;
        try {
            action = Optional.of(em.find(Action.class, id)).get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.of(action);
    }


    // No need
    @Override
    public boolean delete(Long id) {
        return false;
    }


    // No need
    @Override
    public Action update(Action element) {
        return null;
    }

    @Override
    public Action create(Action element) {
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
    public Optional<Action> checkIfExists(Action element) {
        return Optional.empty();
    }
}
