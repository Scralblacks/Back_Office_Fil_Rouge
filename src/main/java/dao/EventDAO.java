package dao;

import Entity.Action;
import Entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import utils.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class EventDAO implements CrudDAO<Event> {

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Event update(Event element) {
        return null;
    }

    @Override
    public Event create(Event element) {
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

    @Override
    public Optional<Event> checkIfExists(Event element) {
        return Optional.empty();
    }

}
