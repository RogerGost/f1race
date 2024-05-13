package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SponsorTest {
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

            var sponsor = entityManager.find(Team.class,1);

            Sponsor sponsor1 = new Sponsor();
            sponsor1.setName("Oracle");
            sponsor1.setCountry("USA");
            sponsor1.setPhone(44545458);
            sponsor1.setSponsorType("Software");


            entityManager.persist(sponsor1);

            assertTrue(sponsor1.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }


}