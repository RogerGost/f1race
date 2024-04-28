package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JbdcSponsorRepository implements SponsorRepository {


    private static final String  INSERT_SPONSOR = "INSERT INTO SPONSOR (SPONSOR_NAME, COUNTRY, NUMBER, SPONSOR_TYPE) VALUES (?,?,?,?)";
    private static final String  UPDATE_SPONSOR = "UPDATE SPONSOR SET SPONSOR_NAME = ?, COUNTRY = ?,NUMBER = ?, SPONSOR_TYPE = ? WHERE ID = ?";
    private final Connection connection;

    public JbdcSponsorRepository(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(Sponsor model) {
        try(

                var preparedStatement = connection.prepareStatement(INSERT_SPONSOR, Statement.RETURN_GENERATED_KEYS);
                var teamSponsorStatement = connection.prepareStatement(UPDATE_SPONSOR, Statement.RETURN_GENERATED_KEYS))
        {
            connection.setAutoCommit(false);
            /*preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getCountry());
            preparedStatement.setInt(3, model.getPhone());
            preparedStatement.setString(4, model.getSponsorType());
            preparedStatement.executeUpdate();*/
            var keys = preparedStatement.getGeneratedKeys();
            if (keys.next()){
                model.setId(keys.getInt(1));
            }


            for (var team : model.getTeams()){
                teamSponsorStatement.setInt(1, model.getId());
                teamSponsorStatement.setInt(2, team.getId());

                teamSponsorStatement.executeUpdate();
            }
            connection.commit();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


    public void update(Sponsor model) {
        try (var preparedStatement = connection.prepareStatement(UPDATE_SPONSOR)) {
            preparedStatement.setString(1, model.getName());
            preparedStatement.setInt(5, model.getId());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el patrocinador en la base de datos", e);
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
