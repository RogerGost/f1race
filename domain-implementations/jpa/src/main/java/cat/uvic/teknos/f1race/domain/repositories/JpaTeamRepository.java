package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.swing.text.html.parser.Entity;
import java.util.HashSet;
import java.util.Set;

public class JpaTeamRepository implements TeamRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaTeamRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Team model) {
        var entityManager= entitymanagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Team model) {
        var entityManager = entitymanagerFactory.createEntityManager();
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

    public void update(Team model){
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
    public Team get(Integer id) {
        var entityManager = entitymanagerFactory.createEntityManager();
        try {
            return entityManager.find(Team.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Team> getAll() {
        var entityManager = entitymanagerFactory.createEntityManager();
        try {
            var query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
            return new HashSet<>(query.getResultList());
        } finally {
            entityManager.close();
        }
    }
}
