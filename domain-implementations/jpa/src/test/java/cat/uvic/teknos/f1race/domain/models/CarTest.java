package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.domain.repositories.JpaCarRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private static EntityManagerFactory entityManagerFactory;
    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("formula1jpa");
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void insertTaste() {
        // EntityManager
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            var team = entityManager.find(Team.class, 1);

            Team team1 = new Team();
            team1.setId(2);

            Car car = new Car();
            car.setModel("AP19");
            car.setEngine("Honda");
            car.setChassis("AP19");
            car.setTeam(team1);



            entityManager.persist(car);

            assertTrue(car.getId() > 0);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }



}