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
        return new JpaTeamRepository(entityManagerFactory);
    }

    @Override
    public CarRepository getCarRepository() {
        return new JpaCarRepository(entityManagerFactory);
    }

    @Override
    public DriverRepository getDriverRepository() {
        return new JpaDriverRepository(entityManagerFactory);
    }

    @Override
    public SponsorRepository getSponsorRepository() {
        return new JpaSponsorRepository(entityManagerFactory);
    }

    @Override
    public RaceResultRepository getRaceResultRepository() {
        return new JpaRaceResultRepository(entityManagerFactory);
    }
}
