package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JbdcSponsorRepository implements SponsorRepository {

    private static final String INSERT_TEAM_SPONSOR = "INSERT INTO TEAM_SPONSOR (TEAM_ID,SPONSOR_ID) VALUES(?,?)";

    private final Connection connection;

    public JbdcSponsorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Sponsor model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Sponsor model) {
        try {
            connection.setAutoCommit(false);

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

    private void insertIntoSponsor(Sponsor model) throws SQLException {
        String insertSql = "INSERT INTO SPONSOR (SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getCountry());
            statement.setInt(3, model.getPhone());
            statement.setString(4, model.getSponsorType());

            statement.executeUpdate();



            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    model.setId(generatedKeys.getInt(1));
                }
            }
            if (model.getTeams() != null){
                for (Team team : model.getTeams()) {
                    InsertTeamSponsor(team, model.getId());
                }
            }
        }
    }

    private void InsertTeamSponsor(Team model,int sponsor_id){
        try (
                var preparedStatement = connection.prepareStatement(INSERT_TEAM_SPONSOR)
        ) {
            preparedStatement.setInt(1, model.getId());
            preparedStatement.setInt(2, sponsor_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Sponsor model) {
        try {
            connection.setAutoCommit(false);

            updateSponsor(model);

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

    private void updateSponsor(Sponsor model) throws SQLException {
        String updateSql = "UPDATE SPONSOR SET SPONSOR_NAME=?, COUNTRY=?, NUMBER=?, SPONSOR_TYPE=? WHERE SPONSOR_ID=?";
        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getCountry());
            statement.setInt(3, model.getPhone());
            statement.setString(4, model.getSponsorType());
            statement.setInt(5, model.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No items to update");
            }
        }
    }

    @Override
    public void delete(Sponsor model) {
        try {
            connection.setAutoCommit(false);

            deleteSponsor(model);

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

    private void deleteSponsor(Sponsor model) throws SQLException {
        String deleteSql = "DELETE FROM SPONSOR WHERE SPONSOR_ID=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(1, model.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Sponsor get(Integer id) {
        String query = "SELECT * FROM SPONSOR WHERE SPONSOR_ID=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor sponsor=new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor() {
                    };
                    sponsor.setId(resultSet.getInt("SPONSOR_ID"));
                    sponsor.setName(resultSet.getString("SPONSOR_NAME"));
                    sponsor.setCountry(resultSet.getString("COUNTRY"));
                    sponsor.setPhone(resultSet.getInt("NUMBER"));
                    sponsor.setSponsorType(resultSet.getString("SPONSOR_TYPE"));
                    return sponsor;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Set<Sponsor> getAll() {
        String query = "SELECT * FROM SPONSOR";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            Set<Sponsor> sponsors = new HashSet<>();
            while (resultSet.next()) {
                cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor sponsor=new cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor() {
                };
                sponsor.setId(resultSet.getInt("SPONSOR_ID"));
                sponsor.setName(resultSet.getString("SPONSOR_NAME"));
                sponsor.setCountry(resultSet.getString("COUNTRY"));
                sponsor.setPhone(resultSet.getInt("NUMBER"));
                sponsor.setSponsorType(resultSet.getString("SPONSOR_TYPE"));
                sponsors.add(sponsor);
            }
            return sponsors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}