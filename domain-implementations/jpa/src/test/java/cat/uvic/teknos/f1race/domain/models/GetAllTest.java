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
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT t FROM Team t");
            List<Team> teams = query.getResultList();

            assertNotNull(teams);
            assertTrue(teams.size() > 0);

            for (Team team : teams) {
                System.out.println("Team Name: " + team.getTeamName());
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            fail("Error occurred: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManager.close();
        }
    }
}
