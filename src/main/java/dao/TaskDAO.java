package dao;

import Entity.Planning;
import Entity.Task;
import Entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import utils.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class TaskDAO implements CrudDAO<Task> {

    // No need
    @Override
    public List<Task> findAll() {
        return null;
    }


    // No need for now
    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean success = false;
        try {
            et.begin();
            Optional<Task> task = Optional.of(em.find(Task.class, id));
            em.remove(task.get());
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
    public Task update(Task element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Task taskToUpdate = null;
        try {
            et.begin();
            taskToUpdate = Optional.of(em.find(Task.class, element.getIdTask())).get();
            taskToUpdate.setNameTask(element.getNameTask());
            taskToUpdate.setDateTaskStart(element.getDateTaskStart());
            taskToUpdate.setDateTaskEnd(element.getDateTaskEnd());
            taskToUpdate.setDescription(element.getDescription());
            em.persist(taskToUpdate);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
        return taskToUpdate;
    }

    @Override
    public Task create(Task element) {
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
    public Optional<Task> checkIfExists(Task element) {
        return Optional.empty();
    }
}
