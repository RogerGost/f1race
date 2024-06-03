package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

public class JpaCarRepository implements CarRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaCarRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Car model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            var modelAttached = entityManager.merge(model);
            model.setId(modelAttached.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Car model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Car existingCar = entityManager.find(Car.class, model.getId());
            if (existingCar == null) {
                throw new RepositoryException("El car con ID " + model.getId() + " no existe.");
            }
            entityManager.merge(model);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Car model) {
        var entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Car mergedModel = entityManager.merge(model);
            entityManager.remove(mergedModel);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Car get(Integer id) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return entityManager.find(Car.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Car> getAll() {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            var query = entityManager.createQuery("SELECT a FROM Car a", Car.class);
            return new HashSet<>(query.getResultList());
        } finally {
            entityManager.close();
        }
    }
}
