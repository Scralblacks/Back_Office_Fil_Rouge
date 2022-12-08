package dao;

import Entity.Address;
import Entity.Users;
import jakarta.persistence.*;
import utils.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class AddressDAO implements CrudDAO<Address> {

    // No need
    @Override
    public List<Address> findAll() {
        return null;
    }

    // No need for now
    @Override
    public Optional<Address> findById(Long id) {
        return Optional.empty();
    }

    // No need for now
    @Override
    public boolean delete(Long id) {
        return false;
    }

    // No need
    @Override
    public Address update(Address element) {
        return null;
    }

    @Override
    public Address create(Address element) {
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
    public Optional<Address> checkIfExists(Address element) {
        EntityManagerFactory emf = ConnectionManager.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        List<Address> addressList = null;
        try {
            TypedQuery<Address> query = em.createQuery("select a from Address a WHERE a.city=:c AND a.postalCode=:pc", Address.class);
            query.setParameter("c", element.getCity());
            query.setParameter("pc", element.getPostalCode());
            addressList = query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Address doesn't exist. Need to create it first...");
        } finally {
            em.close();
        }

        return addressList.size() > 0 ? Optional.ofNullable(addressList.get(0)) : Optional.<Address>empty();
    }
}
