package dao;

import Entity.Planning;
import Entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import utils.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanningDAO implements CrudDAO<Planning>{
    @Override
    public List<Planning> findAll() {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<Planning> plannings = new ArrayList<>();
        try {
            TypedQuery<Planning> query = em.createQuery("select p from Planning p", Planning.class);
            plannings = query.getResultList();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            em.close();
        }

        return plannings;
    }

    @Override
    public Optional<Planning> findById(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Planning planning = null;
        // Probably needed to add shared people in the query later
        try {
            TypedQuery<Planning> query = em.createQuery("select p from Planning p " +
                    "left join fetch p.tasks " +
                    "left join fetch p.events", Planning.class);
            query.setParameter("id",id);
            planning = query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return Optional.of(planning);
    }

    @Override
    public boolean delete(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean success = false;
        try {
            et.begin();
            Optional<Planning> planning = Optional.of(em.find(Planning.class, id));
            em.remove(planning.get());
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
    public Planning update(Planning element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Planning planningToUpdate = null;
        try {
            et.begin();
            planningToUpdate = Optional.of(em.find(Planning.class, element.getIdPlanning())).get();
            planningToUpdate.setNamePlanning(element.getNamePlanning());
            em.persist(planningToUpdate);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
        return planningToUpdate;
    }

    @Override
    public Planning create(Planning element) {
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

    // No need for now
    @Override
    public Optional<Planning> checkIfExists(Planning element) {
        return Optional.empty();
    }
}
