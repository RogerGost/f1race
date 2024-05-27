package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.repositories.Repository;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class JpaSponsorShipRepository implements Repository<SponsorShipId, SponsorShip> {
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
