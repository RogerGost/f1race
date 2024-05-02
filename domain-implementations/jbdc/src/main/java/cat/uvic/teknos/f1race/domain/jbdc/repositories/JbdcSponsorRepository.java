package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JbdcSponsorRepository implements SponsorRepository {


    private final Connection connection;

    public JbdcSponsorRepository(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(Sponsor model) {
        if (model.getId() <= 0){
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Sponsor model) {
        String sql = "INSERT INTO SPONSOR (SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getCountry());
            statement.setInt(3, model.getPhone());
            statement.setString(4, model.getSponsorType());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting sponsor failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    model.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting sponsor failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting sponsor", e);
        }
    }


    public void update(Sponsor model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE SPONSOR SET SPONSOR_NAME=?, COUNTRY=?, NUMBER=?, SPONSOR_TYPE=? WHERE SPONSOR_ID=?", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, model.getName());
            statement.setString(2, model.getCountry());
            statement.setInt(3, model.getPhone());
            statement.setString(4, model.getSponsorType());
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
    public void delete(Sponsor model) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SPONSOR WHERE SPONSOR_ID = ?");
            PreparedStatement teamStatement = connection.prepareStatement("DELETE FROM TEAM WHERE SPONSOR = ?")){
            preparedStatement.setInt(1,model.getId());
            preparedStatement.executeUpdate();

            for(var team : model.getTeams()){
                teamStatement.setString(1,team.getSponsor());
                preparedStatement.setInt(1, team.getId());

                teamStatement.executeUpdate();
                preparedStatement.executeUpdate();
            }
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e){

            throw  new RuntimeException(e);
        }
    }

    @Override
    public Sponsor get(Integer id) {
        String query = "SELECT * FROM SPONSOR WHERE SPONSOR_ID = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    Sponsor sponsor = new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor(); {
                        sponsor.setId(resultSet.getInt("ID"));
                        sponsor.setName(resultSet.getString("NAME"));
                        sponsor.setCountry(resultSet.getString("COUNTRY"));
                        sponsor.setPhone(resultSet.getInt("PHONE"));

                        return sponsor;
                    }
                }

            }
        } catch (SQLException e){

            throw  new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Set<Sponsor> getAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SPONSOR")){
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Sponsor>sponsors = new HashSet<>();
            while (resultSet.next()){
                Sponsor sponsor = new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor();
                sponsor.setId(resultSet.getInt("ID"));
                sponsor.setSponsorType(resultSet.getString("SPONSOR_TYPE"));
                sponsor.setName(resultSet.getString("NAME"));
                sponsor.setCountry(resultSet.getString("COUNTRY"));
                sponsor.setPhone(resultSet.getInt("PHONE"));
            }
            return sponsors;

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
}
