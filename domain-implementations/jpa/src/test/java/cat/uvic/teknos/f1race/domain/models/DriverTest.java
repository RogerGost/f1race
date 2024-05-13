package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

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

            var driver = entityManager.find(Team.class,1);

            Driver driver1 = new Driver();
            driver1.setName("Leclerc");
            driver1.setNationality("Monaco");
            driver1.setDate(1997);
            driver1.setNumber(16);
            driver1.setTeamId(1);


            entityManager.persist(driver1);

            assertTrue(driver1.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

}