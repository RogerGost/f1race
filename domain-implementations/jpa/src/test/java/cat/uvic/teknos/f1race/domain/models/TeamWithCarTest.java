package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamWithCarTest {
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

            var team = new Team();
            team.setTeamName("Williams");
            team.setPrincipalName("Albon");
            team.setHeadquarters("London");
            team.setSponsor("Duracell");

            var car = new Car();
            car.setEngine("Honda");
            car.setTeamId(2);

            entityManager.persist(team);
            entityManager.persist(car);

            assertTrue(team.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }
}