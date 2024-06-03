package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;

public class JpaSponsorRepository implements SponsorRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaSponsorRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Sponsor model) {
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
    public void update(Sponsor model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Sponsor existingSponsor = entityManager.find(Sponsor.class, model.getId());
            if (existingSponsor == null) {
                throw new RepositoryException("El sponsor con ID " + model.getId() + " no existe.");
            }
            entityManager.merge(model);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void delete(Sponsor model) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Sponsor sponsor = entityManager.find(Sponsor.class, model.getId());
            if (sponsor != null) {
                entityManager.remove(sponsor);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Sponsor get(Integer id) {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return entityManager.find(Sponsor.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Sponsor> getAll() {
        EntityManager entityManager = entitymanagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT s FROM Sponsor s", Sponsor.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
