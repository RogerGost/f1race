package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Driver;
import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JbdcTeamRepository implements TeamRepository {

    private final Connection connection;

    public JbdcTeamRepository(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(Team model) {
        if (model.getId() <= 0){
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Team model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO TEAM ( TEAM_NAME, PRINCIPAL_NAME, HEADQUARTERS, SPONSOR_NAME) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getTeamName());
            statement.setString(2, model.getPrincipalName());
            statement.setString(3, model.getHeadquarters());
            statement.setString(4, model.getSponsorName());



            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void update(Team model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE TEAM SET TEAM_NAME=?, PRINCIPAL_NAME=?, HEADQUARTERS=?, SPONSOR_NAME=? WHERE TEAM_ID=?", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getTeamName());
            statement.setString(2, model.getPrincipalName());
            statement.setString(3, model.getHeadquarters());
            statement.setString(4, model.getSponsorName());
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
    public void delete(Team model) {
        try (
                PreparedStatement deleteTeamSponsorStatement = connection.prepareStatement("DELETE FROM TEAM_SPONSOR WHERE TEAM_ID = ?");
                PreparedStatement deleteCarsStatement = connection.prepareStatement("DELETE FROM CAR WHERE TEAM_ID = ?");
                PreparedStatement deleteRaceResultsStatement = connection.prepareStatement("DELETE FROM RACE_RESULT WHERE DRIVER_ID IN (SELECT DRIVER_ID FROM DRIVER WHERE TEAM_ID = ?)");
                PreparedStatement deleteDriversStatement = connection.prepareStatement("DELETE FROM DRIVER WHERE TEAM_ID = ?");
                PreparedStatement deleteTeamStatement = connection.prepareStatement("DELETE FROM TEAM WHERE TEAM_ID = ?")
        ) {
            deleteTeamSponsorStatement.setInt(1, model.getId());
            deleteTeamSponsorStatement.executeUpdate();

            deleteCarsStatement.setInt(1, model.getId());
            deleteCarsStatement.executeUpdate();

            deleteRaceResultsStatement.setInt(1, model.getId());
            deleteRaceResultsStatement.executeUpdate();

            deleteDriversStatement.setInt(1, model.getId());
            deleteDriversStatement.executeUpdate();

            deleteTeamStatement.setInt(1, model.getId());
            int rowsAffected = deleteTeamStatement.executeUpdate();
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
    public Team get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEAM WHERE TEAM_ID = ?")) {
            Team merceders = null;

            statement.setInt(1, id);

            var resultSet= statement.executeQuery();
            if (resultSet.next()) {
                merceders = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
                merceders.setId(resultSet.getInt("TEAM_ID"));
                merceders.setTeamName(resultSet.getString("TEAM_NAME"));
                merceders.setPrincipalName(resultSet.getString("PRINCIPAL_NAME"));
                merceders.setHeadquarters(resultSet.getString("HEADQUARTERS"));
                merceders.setSponsorName(resultSet.getString("SPONSOR_NAME"));

            }
            return merceders;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public Set<Team> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEAM")) {
            var teams = new HashSet<Team>();


            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var mercedes = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
                mercedes.setId(resultSet.getInt("TEAM_ID"));
                mercedes.setTeamName(resultSet.getString("TEAM_NAME"));
                mercedes.setPrincipalName(resultSet.getString("PRINCIPAL_NAME"));
                mercedes.setHeadquarters(resultSet.getString("HEADQUARTERS"));
                mercedes.setSponsorName(resultSet.getString("SPONSOR_NAME"));



                teams.add(mercedes);
            }
            return teams;
        } catch (SQLException e) {
            throw new RuntimeException("Error", e);
        }
    }
}
