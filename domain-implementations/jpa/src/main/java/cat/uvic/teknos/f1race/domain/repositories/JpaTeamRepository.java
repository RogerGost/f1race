package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.Set;

public class JpaTeamRepository implements TeamRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaTeamRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Team model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            var modelAttached = entityManager.merge(model);
            model.setId(modelAttached.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Team model) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Team mergedModel = entityManager.merge(model);
            entityManager.remove(mergedModel);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void update(Team model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Team existingTeam = entityManager.find(Team.class, model.getId());
            if (existingTeam == null) {
                throw new RepositoryException("El equipo con ID " + model.getId() + " no existe.");
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
    public Team get(Integer id) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Team.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Team> getAll() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
            return new HashSet<>(query.getResultList());
        } finally {
            entityManager.close();
        }
    }
}
