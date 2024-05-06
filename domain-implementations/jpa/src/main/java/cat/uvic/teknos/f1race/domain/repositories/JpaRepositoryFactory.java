package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.repositories.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaRepositoryFactory implements RepositoryFactory {
    private EntityManagerFactory entityManagerFactory;

    public JpaRepositoryFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("formula1jpa");
    }
    @Override
    public TeamRepository getTeamRepository() {
        return null;
    }

    @Override
    public CarRepository getCarRepository() {
        return null;
    }

    @Override
    public DriverRepository getDriverRepository() {
        return null;
    }

    @Override
    public SponsorRepository getSponsorRepository() {
        return null;
    }

    @Override
    public RaceResultRepository getRaceResultRepository() {
        return null;
    }
}
