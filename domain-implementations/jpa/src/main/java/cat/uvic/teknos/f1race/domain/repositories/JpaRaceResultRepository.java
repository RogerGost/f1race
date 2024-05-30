package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

public class JpaRaceResultRepository implements RaceResultRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaRaceResultRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(RaceResult model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            var modelAttached = entityManager.merge(model);
            model.setId(modelAttached.getId());
            //entityManager.persist(model);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(RaceResult model) {
        // Implementación según necesidades específicas
    }

    @Override
    public RaceResult get(Integer id) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return entityManager.find(RaceResult.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<RaceResult> getAll() {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT rr FROM RaceResult rr", RaceResult.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
