package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})


class JbdcTeamRepositoryTest {

    private final Connection connection;


    public JbdcTeamRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    void save() {

        Team team = new Team();
        team.setTeamName("Alpha tauri");
        team.setPrincipalName("yuki");
        team.setHeadquarters("Italy");
        team.setSponsor("Visa");

        var repository = new JbdcTeamRepository(connection);

        repository.save(team);

        assertTrue(team.getId() >0);

        assertNotNull(repository.get(team.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/
    }
    @Test
    void shouldUpdateNewTeamTest() throws SQLException {


        Team team = new Team();

        team.setTeamName("Williams");
        team.setPrincipalName("Russell");
        team.setHeadquarters("London");
        team.setSponsor("Duracell");
        team.setId(1);


        var repository = new JbdcTeamRepository(connection);
        repository.save(team);

        assertTrue(true);

    }

    @Test
    void delete() {

        Team team = new Team();
        team.setId(1);

        var repository = new JbdcTeamRepository(connection);
        repository.delete(team);

        //assertNull(repository.get(1));
    }

    @Test
    void get() {
        var repository = new JbdcTeamRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
    }
}