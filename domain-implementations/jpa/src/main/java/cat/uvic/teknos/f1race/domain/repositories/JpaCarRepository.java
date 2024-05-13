package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaCarRepository implements CarRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaCarRepository(EntityManagerFactory entityManagerFactory){
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
    public void delete(Car model) {

    }

    @Override
    public Car get(Integer id) {
        return null;
    }

    @Override
    public Set<Car> getAll() {
        return null;
    }
}
