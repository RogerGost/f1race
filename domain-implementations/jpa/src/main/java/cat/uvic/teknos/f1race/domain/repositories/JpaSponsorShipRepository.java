package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.SponsorShip;
import cat.uvic.teknos.f1race.models.SponsorShipId;
import cat.uvic.teknos.f1race.repositories.SponsorShipRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaSponsorShipRepository implements SponsorShipRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaSponsorShipRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(SponsorShip model) {
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();

    }

    @Override
    public void delete(SponsorShip model) {

    }

    @Override
    public SponsorShip get(SponsorShipId id) {
        return null;
    }

    @Override
    public Set<SponsorShip> getAll() {
        return null;
    }
}
