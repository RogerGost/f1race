package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

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
    void insertTest() {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Encuentra el equipo existente con ID 1
            var team = entityManager.find(Team.class, 1);
            if (team == null) {
                // Crear y persistir un equipo si no existe
                team = new Team();
                team.setTeamName("Ferrari");
                team.setPrincipalName("Binotto");
                team.setHeadquarters("Maranello");
                team.setSponsor("Shell");

                entityManager.persist(team);
                entityManager.flush();  // Garantiza que el equipo obtenga su ID
            }

            // Crear un nuevo conductor
            Driver driver1 = new Driver();
            driver1.setName("Leclerc");
            driver1.setNationality("Monaco");
            driver1.setDate(1997);
            driver1.setNumber(16);
            driver1.setTeamId(team.getId());  // Configura el ID del equipo

            entityManager.persist(driver1);

            assertTrue(driver1.getId() > 0);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}