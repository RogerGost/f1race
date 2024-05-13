package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaRaceResultRepository implements RaceResultRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaRaceResultRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(RaceResult model) {
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();

    }

    @Override
    public void delete(RaceResult model) {

    }

    @Override
    public RaceResult get(Integer id) {
        return null;
    }

    @Override
    public Set<RaceResult> getAll() {
        return null;
    }
}
