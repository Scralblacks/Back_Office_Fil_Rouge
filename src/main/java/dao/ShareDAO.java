package dao;

import Entity.Planning;
import Entity.Share;
import Entity.ShareId;
import Entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import utils.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class ShareDAO implements CrudDAO<Share> {

    // No need
    @Override
    public List<Share> findAll() {
        return null;
    }


    // No need
    @Override
    public Optional<Share> findById(Long id) {
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
            Optional<Share> share = Optional.of(em.find(Share.class, id));
            em.remove(share.get());
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
    public Share update(Share element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Share shareToUpdate = null;
        try {
            et.begin();
            shareToUpdate = Optional.of(em.find(Share.class, element.getId())).get();
            shareToUpdate.setIsReadOnly(false);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        }
        finally {
            em.close();
        }

        return shareToUpdate;
    }

    @Override
    public Share create(Share element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        Share s = new Share();
        try {
            et.begin();
            Users u = em.find(Users.class, element.getUsers().getIdUser());
            Planning p = em.find(Planning.class, element.getPlanning().getIdPlanning());
            s.setId(new ShareId(u.getIdUser(), p.getIdPlanning()));
            s.setUsers(u);
            s.setPlanning(p);
            s.setIsReadOnly(false);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(et.isActive()) {
                et.rollback();
            }
        }
        finally {
            em.close();
        }

        return s;

    }

    // No need
    @Override
    public Optional<Share> checkIfExists(Share element) {
        return Optional.empty();
    }
}
