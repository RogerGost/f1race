package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaSponsorRepository implements SponsorRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaSponsorRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Sponsor model) {
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();

    }

    @Override
    public void delete(Sponsor model) {

    }

    @Override
    public Sponsor get(Integer id) {
        return null;
    }

    @Override
    public Set<Sponsor> getAll() {
        return null;
    }
}
