package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.domain.repositories.JpaCarRepository;
import cat.uvic.teknos.f1race.domain.repositories.JpaTeamRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamWithCarTest {
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

        var teamRepository = new JpaTeamRepository(entityManagerFactory);
        var carRepository = new JpaCarRepository(entityManagerFactory);


        var entityManager = entityManagerFactory.createEntityManager();

        try {

            entityManager.getTransaction().begin();


            var team = new Team();
            team.setTeamName("Williams");
            team.setPrincipalName("Albon");
            team.setHeadquarters("London");
            team.setSponsorName("Duracell");


            teamRepository.save(team);

            assertTrue(team.getId() > 0);

            Team team1 = new Team();
            team1.setId(2);

            var car = new Car();
            car.setModel("AP19");
            car.setEngine("Honda");
            car.setChassis("AP19");
            car.setTeam(team1);


            carRepository.save(car);


            entityManager.getTransaction().commit();
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {

            entityManager.close();
        }
    }
}