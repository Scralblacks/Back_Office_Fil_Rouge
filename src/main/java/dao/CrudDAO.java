package dao;

import Entity.Action;
import Entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import utils.ConnectionManager;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<E> {
    List<E> findAll();
    Optional<E> findById(Long id);
    boolean delete(Long id);
    E update(E element);

    E create(E element);

    Optional<E> checkIfExists(E element);

}
