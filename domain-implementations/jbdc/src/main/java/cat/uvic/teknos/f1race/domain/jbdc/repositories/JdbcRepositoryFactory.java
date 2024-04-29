package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.repositories.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcRepositoryFactory implements RepositoryFactory {

    private Connection connection;
    public JdbcRepositoryFactory(){
        try {
            var properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/datasource.properties"));
            connection = DriverManager.getConnection(String.format("%s:%s://%s/%s",
                    properties.getProperty("protocol"),
                    properties.getProperty("subprotocol"),
                    properties.getProperty("url"),
                    properties.getProperty("database")), properties.getProperty("user"), properties.getProperty("password"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public TeamRepository getTeamRepository() {
        return new JbdcTeamRepository(connection);
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
