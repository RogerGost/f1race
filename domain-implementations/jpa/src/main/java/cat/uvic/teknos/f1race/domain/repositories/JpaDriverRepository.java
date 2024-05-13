package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.repositories.DriverRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaDriverRepository implements DriverRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaDriverRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Car model) {
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();

    }

    @Override
    public void save(Driver model) {

    }

    @Override
    public void delete(Driver model) {

    }

    @Override
    public Driver get(Integer id) {
        return null;
    }

    @Override
    public Set<Driver> getAll() {
        return null;
    }
}
