package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.repositories.CarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
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
            statement.setInt(4,model.getTeamId());


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
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CAR WHERE CAR_ID = ?")) {
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
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CAR WHERE CAR_ID = ?")) {
            Car merceders = null;

            statement.setInt(1, id);

            var resultSet= statement.executeQuery();
            if (resultSet.next()) {
                merceders = new cat.uvic.teknos.f1race.domain.jbdc.models.Car();
                merceders.setId(resultSet.getInt("CAR_ID"));
                merceders.setEngine(resultSet.getString("ENGINE"));
                merceders.setChassis(resultSet.getString("CHASSIS"));
                merceders.setModel(resultSet.getString("MODEL"));
            }
            return merceders;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public Set<Car> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CAR")) {
            var cars = new HashSet<Car>();


            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var mercedes = new cat.uvic.teknos.f1race.domain.jbdc.models.Car();
                mercedes.setId(resultSet.getInt("CAR_ID"));
                mercedes.setModel(resultSet.getString("MODEL"));
                mercedes.setEngine(resultSet.getString("ENGINE"));
                mercedes.setChassis(resultSet.getString("CHASSIS"));
                mercedes.setTeamId(resultSet.getInt("TEAM_ID"));

                cars.add(mercedes);
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }

    }
}
