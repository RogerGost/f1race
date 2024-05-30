package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.repositories.DriverRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

public class JpaDriverRepository implements DriverRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaDriverRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Driver model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            var modelAttached = entityManager.merge(model);
            model.setId(modelAttached.getId());
            //entityManager.persist(model);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Driver model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Driver driver = entityManager.find(Driver.class, model.getId());
            if (driver != null) {
                entityManager.remove(driver);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Driver get(Integer id) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return entityManager.find(Driver.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Driver> getAll() {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT d FROM Driver d", Driver.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
