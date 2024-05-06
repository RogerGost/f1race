package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.swing.text.html.parser.Entity;
import java.util.Set;

public class JpaTeamRepository implements TeamRepository {
    private final EntityManagerFactory entitymanagerFactory;

    public JpaTeamRepository(EntityManagerFactory entityManagerFactory){
        this.entitymanagerFactory = entityManagerFactory;
    }
    @Override
    public void save(Team model) {
        var entityManager= entitymanagerFactory.createEntityManager();
    }

    @Override
    public void delete(Team model) {

    }

    @Override
    public Team get(Integer id) {
        return null;
    }

    @Override
    public Set<Team> getAll() {
        return null;
    }
}
