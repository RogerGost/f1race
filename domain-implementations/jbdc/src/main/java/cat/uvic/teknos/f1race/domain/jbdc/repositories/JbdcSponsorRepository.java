package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;
import cat.uvic.teknos.f1race.repositories.SponsorShipRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JbdcSponsorRepository implements SponsorRepository{

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
        try {
            connection.setAutoCommit(false);

            //insertIntoSponsorship(model);

            insertIntoSponsor(model);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    /*public void insertIntoSponsorship(Sponsor model) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO SPONSORSHIP (SPONSOR_NAME, CONTRACT_START_DATE, CONTRACT_END_DATE, TEAM_ID) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            statement.setDate(2, model.get());
            statement.setDate(3, model.getEndDate());
            statement.setInt(4, model.get());

            statement.executeUpdate();
        }
    }*/

    private void insertIntoSponsor(Sponsor model) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO SPONSOR (SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE) VALUES (?,?,?,?)")) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getCountry());
            statement.setInt(3, model.getPhone());
            statement.setString(4, model.getSponsorType());

            statement.executeUpdate();
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SPONSOR WHERE SPONSOR_ID = ?")) {
            preparedStatement.setInt(1,model.getId());
            preparedStatement.executeUpdate();
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
                    Sponsor sponsor = new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor();
                    sponsor.setId(resultSet.getInt("ID"));
                    sponsor.setName(resultSet.getString("NAME"));
                    sponsor.setCountry(resultSet.getString("COUNTRY"));
                    sponsor.setPhone(resultSet.getInt("NUMBER"));
                    sponsor.setSponsorType(resultSet.getString("SPONSOR_TYPE"));
                    return sponsor;
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
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Sponsor> sponsors = new HashSet<>();
            while (resultSet.next()){
                Sponsor sponsor = new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor();
                sponsor.setId(resultSet.getInt("SPONSOR_ID"));
                sponsor.setName(resultSet.getString("SPONSOR_NAME"));
                sponsor.setCountry(resultSet.getString("COUNTRY"));
                sponsor.setPhone(resultSet.getInt("NUMBER"));
                sponsor.setSponsorType(resultSet.getString("SPONSOR_TYPE"));
                sponsors.add(sponsor);
            }
            return sponsors;
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
}
