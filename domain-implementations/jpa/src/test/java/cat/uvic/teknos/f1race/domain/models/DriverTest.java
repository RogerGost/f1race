package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.domain.models.Driver;
import cat.uvic.teknos.f1race.domain.models.Team;
import cat.uvic.teknos.f1race.domain.repositories.JpaTeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    private static EntityManagerFactory entityManagerFactory;
    private static JpaTeamRepository teamRepository;
    private static int teamId;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("formula1jpa");
        teamRepository = new JpaTeamRepository(entityManagerFactory);
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void insertTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();




            Team team1 = new Team();
            team1.setId(1);

            Driver driver = new Driver();
            driver.setName("Verstappen");
            driver.setNationality("Dutch");
            driver.setDate(1997);
            driver.setNumber(1);
            driver.setTeam(team1);

            entityManager.persist(driver);
            entityManager.flush();

            assertTrue(driver.getId() > 0);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void updateTest() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Team team1 = new Team();
            team1.setId(2);

            Driver driver = new Driver();

            driver.setName("Charles Leclerc");
            driver.setNationality("Monaco");
            driver.setNumber(16);
            driver.setTeam(team1);
            driver.setId(1);

            entityManager.persist(driver);
            entityManager.flush();

            assertTrue(driver.getId() > 0);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void delete() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("formula1jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();

            Driver driver = entityManager.find(Driver.class, 1);
            if (driver == null) {
                System.out.println("El conductor con ID 1 no existe.");
                return;
            }

            entityManager.remove(driver);

            entityManager.getTransaction().commit();

            assertNull(entityManager.find(Driver.class, 4));

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getAllTest() {

    }


}
