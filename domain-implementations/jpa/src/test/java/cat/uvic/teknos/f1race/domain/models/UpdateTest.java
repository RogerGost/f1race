package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.domain.repositories.JpaCarRepository;
import cat.uvic.teknos.f1race.domain.repositories.JpaTeamRepository;
import cat.uvic.teknos.f1race.models.Team;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateTest {
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
    void updateTest() {
        // Crear instancia del repositorio
        var teamRepository = new JpaTeamRepository(entityManagerFactory);

        // Crear un EntityManager para el test
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            // Obtener el equipo de la base de datos (suponiendo que ya está almacenado)
            int teamIdToUpdate = 1; // ID del equipo a actualizar
            Team teamToUpdate = teamRepository.get(teamIdToUpdate);

            // Verificar que el equipo se haya obtenido correctamente
            assertEquals("Christian Horner", teamToUpdate.getTeamName());

            // Modificar los atributos del equipo
            teamToUpdate.setSponsorName("AMG");

            // Actualizar el equipo en la base de datos
            teamRepository.save(teamToUpdate);

            // Verificar que la modificación se haya guardado correctamente
            assertEquals("AMG", teamRepository.get(teamIdToUpdate).getSponsor());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager
            entityManager.close();
        }
    }

    @Test
    void updateDriverTest() {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Encuentra el conductor existente con ID 1
            Driver driver = entityManager.find(Driver.class, 1);
            assertTrue(driver != null, "Driver not found!");

            // Modificar los atributos del conductor
            driver.setName("Charles Leclerc");
            driver.setNationality("Monaco");
            driver.setDate(1999);
            driver.setNumber(16);

            entityManager.getTransaction().commit();

            // Verificar que los cambios se han realizado
            Driver updatedDriver = entityManager.find(Driver.class, 1);
            assertEquals("Charles Leclerc", updatedDriver.getName());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }



    @Test
    void deleteDriverTest() {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Encuentra el conductor existente con ID 1
            Driver driver = entityManager.find(Driver.class, 1);
            assertTrue(driver != null, "Driver not found!");

            // Eliminar el conductor
            entityManager.remove(driver);
            entityManager.getTransaction().commit();

            // Verificar que el conductor se ha eliminado
            Driver deletedDriver = entityManager.find(Driver.class, 1);
            assertNull(deletedDriver, "Driver should be deleted!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
