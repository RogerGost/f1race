package cat.uvic.teknos.f1race.domain.repositories;

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
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();

    }


    @Override
    public void delete(Car model) {
        var entityManager = entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(model) ? model : entityManager.merge(model));
        entityManager.getTransaction().commit();
    }

    @Override
    public Car get(Integer id) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        Car cars = entityManager.find(Car.class, id);
        entityManager.close();
        return cars;
    }

    @Override
    public Set<Car> getAll() {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Set<Car> cars = new HashSet<>(entityManager.createQuery("SELECT a FROM Car a", Car.class).getResultList());
        entityManager.getTransaction().commit();
        entityManager.close();
        return cars;
    }

}
