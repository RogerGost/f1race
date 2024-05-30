package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RaceResultTest {

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
    void insertTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Team team1 = new Team();
            team1.setId(2);

            // Crear y persistir un nuevo Driver
            Driver driver = new Driver();
            driver.setName("Lewis Hamilton");
            driver.setNationality("British");
            driver.setDate(1989);
            driver.setNumber(44);
            driver.setTeam(team1);  // Asegúrate de que este equipo exista en la base de datos
            entityManager.persist(driver);

            // Crear un nuevo resultado de carrera
            RaceResult raceResult = new RaceResult();
            raceResult.setRaceId(1);
            raceResult.setDriver(driver);  // Asignar el objeto Driver
            raceResult.setPosition(1);
            raceResult.setFastestLap("1:30.000");
            raceResult.setPoints(25);

            // Persistir el resultado de carrera en la base de datos
            entityManager.persist(raceResult);

            // Verificar que se haya generado el ID del resultado de carrera
            assertTrue(raceResult.getId() > 0);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
