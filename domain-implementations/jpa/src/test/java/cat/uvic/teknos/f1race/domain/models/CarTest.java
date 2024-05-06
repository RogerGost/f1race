package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void insertTaste(){
        // EntityManager
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            var team = entityManager.find(Team.class,1);

            Car car = new Car();
            car.setEngine("Honda");
            car.setChassis("Mrc1");


            entityManager.persist(car);

            assertTrue(car.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }
}