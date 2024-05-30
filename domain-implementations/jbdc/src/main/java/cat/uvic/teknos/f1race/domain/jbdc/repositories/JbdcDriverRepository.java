package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.repositories.DriverRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JbdcDriverRepository implements DriverRepository {

    private final Connection connection;

    public JbdcDriverRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Driver model) {
        if (model.getId() <= 0){
            insert(model);
        } else {
            update(model);
        }

    }

    private void insert(Driver model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO DRIVER ( NAME, NATIONALITY, NUMBER, TEAM_ID) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getName());
            statement.setString(2, model.getNationality());
            statement.setInt(3, model.getNumber());
            statement.setInt(4, model.getTeam().getId());



            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private void update(Driver model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE DRIVER SET NAME=?, NATIONALITY=?, NUMBER=?, TEAM_ID=? WHERE DRIVER_ID=?", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getName());
            statement.setString(2, model.getNationality());
            statement.setInt(3, model.getNumber());
            statement.setInt(4, model.getTeam().getId());
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
    public void delete(Driver model) {

        try (PreparedStatement deleteRaceResultsStatement = connection.prepareStatement("DELETE FROM RACE_RESULT WHERE DRIVER_ID = ?");
             PreparedStatement deleteDriverStatement = connection.prepareStatement("DELETE FROM DRIVER WHERE DRIVER_ID = ?")) {


            deleteRaceResultsStatement.setInt(1, model.getId());
            deleteRaceResultsStatement.executeUpdate();

            deleteDriverStatement.setInt(1, model.getId());
            int rowsAffected = deleteDriverStatement.executeUpdate();
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
    public Driver get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM DRIVER WHERE DRIVER_ID = ?")) {
            Driver driver = null;

            statement.setInt(1, id);

            var resultSet= statement.executeQuery();
            if (resultSet.next()) {
                driver = new cat.uvic.teknos.f1race.domain.jbdc.models.Driver();
                driver.setId(resultSet.getInt("DRIVER_ID"));
                driver.setName(resultSet.getString("NAME"));
                driver.setNationality(resultSet.getString("NATIONALITY"));
                driver.setNumber(resultSet.getInt("NUMBER"));
                int teamId = (resultSet.getInt("TEAM_ID"));
                cat.uvic.teknos.f1race.models.Team team = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
                team.setId(teamId);
                driver.setTeam(team);

            }
            return driver;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public Set<Driver> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM DRIVER")) {
            var drivers = new HashSet<cat.uvic.teknos.f1race.models.Driver>();


            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var driver = new cat.uvic.teknos.f1race.domain.jbdc.models.Driver();
                driver.setId(resultSet.getInt("DRIVER_ID"));
                driver.setName(resultSet.getString("NAME"));
                driver.setNationality(resultSet.getString("NATIONALITY"));
                driver.setDate(resultSet.getInt("DATE_OF_BIRTH"));
                driver.setNumber(resultSet.getInt("NUMBER"));
                int teamId = (resultSet.getInt("TEAM_ID"));
                cat.uvic.teknos.f1race.models.Team team = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
                team.setId(teamId);
                driver.setTeam(team);

                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }
}
