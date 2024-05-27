package cat.uvic.teknos.f1race.domain.models;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllTest {
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
    public void testGetAllTeams() {
        var entityManager = entityManagerFactory.createEntityManager();



        try {
            // Iniciar la transacción
            entityManager.getTransaction().begin();

            // Obtener todos los equipos
            Query query = entityManager.createQuery("SELECT t FROM Team t");
            List<Team> teams = query.getResultList();

            // Comprobar que se obtienen resultados
            assertNotNull(teams);
            assertTrue(teams.size() > 0);

            // Imprimir los equipos (solo como ejemplo)
            for (Team team : teams) {
                System.out.println("Team Name: " + team.getTeamName());
            }

            // Confirmar la transacción
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // En caso de excepción, hacer rollback
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            fail("Error occurred: " + e.getMessage());
        } finally {
            // Cerrar el EntityManager y el EntityManagerFactory
            entityManager.close();
            entityManager.close();
        }
    }
}
