package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JbdcRaceResultRepository implements RaceResultRepository {

    private final Connection connection;

    public JbdcRaceResultRepository(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(RaceResult model) {
        if (model.getId() <= 0){
            insert(model);
        } else {
            update(model);
        }

    }

    private void insert(RaceResult model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO RACE_RESULT ( RACE_ID, DRIVER_ID, POSITION, POINTS_EARNED) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, model.getRaceId());
            statement.setInt(2, model.getDriverId());
            statement.setInt(3, model.getPosition());
            statement.setString(4, model.getFastestLap());
            statement.setInt(4, model.getPoints());



            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(RaceResult model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE RACE_RESULT SET RACE_ID=?, DRIVER_ID=?, POSITION=?, POINTS_EARNED=?  WHERE RESULT_ID=?", Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, model.getRaceId());
            statement.setInt(2, model.getDriverId());
            statement.setInt(3, model.getPosition());
            //statement.setTime(4, model.getFastestLap());
            statement.setInt(4, model.getPoints());
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
    public void delete(RaceResult model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM RACE_RESULT WHERE RESULT_ID = ?")) {
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
    public RaceResult get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM RACE_RESULT WHERE RESULT_ID = ?")) {
            RaceResult race = null;

            statement.setInt(1, id);

            var resultSet= statement.executeQuery();
            if (resultSet.next()) {
                race = new cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult();
                race.setId(resultSet.getInt("RESULT_ID"));
                race.setRaceId(resultSet.getInt("RACE_ID"));
                race.setDriverId(resultSet.getInt("DRIVER_ID"));
                race.setPosition(resultSet.getInt("POSITION"));
                race.setFastestLap(resultSet.getString("FASTEST_LAP_TIME"));
                race.setPoints(resultSet.getInt("POINTS_EARNED"));


            }
            return race;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public Set<RaceResult> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM RACE_RESULT")) {
            var races = new HashSet<RaceResult>();


            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var race = new cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult();
                race.setId(resultSet.getInt("RESULT_ID"));
                race.setRaceId(resultSet.getInt("RACE_ID"));
                race.setDriverId(resultSet.getInt("DRIVER_ID"));
                race.setPosition(resultSet.getInt("POSITION"));
                race.setFastestLap(resultSet.getString("FASTEST_LAP_TIME"));
                race.setPoints(resultSet.getInt("POINTS_EARNED"));

                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }
}
