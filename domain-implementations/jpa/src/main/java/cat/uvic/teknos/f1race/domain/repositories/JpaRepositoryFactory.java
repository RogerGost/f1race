package cat.uvic.teknos.f1race.domain.repositories;

import cat.uvic.teknos.f1race.exceptions.RepositoryException;
import cat.uvic.teknos.f1race.repositories.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Properties;

public class JpaRepositoryFactory implements RepositoryFactory {
    private EntityManagerFactory entityManagerFactory;

    public JpaRepositoryFactory() {
        var properties = new Properties();
        try {
            properties.load(JpaRepositoryFactory.class.getResourceAsStream("/jpa.properties"));
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("formula1jpa", properties);
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
