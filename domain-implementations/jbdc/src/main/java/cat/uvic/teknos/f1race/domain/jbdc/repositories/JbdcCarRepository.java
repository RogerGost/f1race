package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.repositories.CarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class JbdcCarRepository implements CarRepository {

    private final Connection connection;

    public JbdcCarRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Car model) {
        if (model.getId() <= 0){
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Car model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CAR ( MODEL, ENGINE, CHASSIS, TEAM_ID) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getModel());
            statement.setString(2, model.getEngine());
            statement.setString(3, model.getChassis());
            statement.setInt(4, model.getTeamId());

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(Car model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE CAR SET MODEL=?, ENGINE=?, CHASSIS=?, TEAM_ID=? WHERE CAR_ID=?", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getModel());
            statement.setString(2, model.getEngine());
            statement.setString(3, model.getChassis());
            statement.setInt(4, model.getTeamId());
            statement.setInt(5, model.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No items to update");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Car model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CAR WHERE ID = ?")) {
            statement.setInt(1, model.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No item to delete");
            } else {
                System.out.println("Correct delete");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public Car get(Integer id) {
        return null;
    }

    @Override
    public Set<Car> getAll() {
        return Set.of();
    }
}
