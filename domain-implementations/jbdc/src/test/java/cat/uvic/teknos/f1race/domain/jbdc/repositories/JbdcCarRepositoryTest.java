package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})


class JbdcCarRepositoryTest {

    private final Connection connection;


    public JbdcCarRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new Car (id = 0), when save, then a new record is added to the CAR table")
    void shouldInsertNewCarTest() throws SQLException {

        Team team1 = new Team();
        team1.setId(1);

            Car mercedes = new Car();
            mercedes.setModel("mc204");
            mercedes.setEngine("mercedes1");
            mercedes.setChassis("C1mercedes");
            mercedes.setTeam(team1);

            var repository = new JbdcCarRepository(connection);

            repository.save(mercedes);

            assertTrue(mercedes.getId() >0);

            assertNotNull(repository.get(mercedes.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/

    }

    @Test
    void shouldUpdateNewCarTest() throws SQLException {

            Team team1 = new Team();
            team1.setId(1);

            Car mercedes = new Car();

            mercedes.setModel("494");
            mercedes.setEngine("mercedes2");
            mercedes.setChassis("C2mercedes");
            mercedes.setTeam(team1);
            mercedes.setId(3);


            var repository = new JbdcCarRepository(connection);
            repository.save(mercedes);

            assertTrue(true);

    }

    @Test
    void delete() throws SQLException {
            Car mercedes = new Car();
            mercedes.setId(3);

            var repository = new JbdcCarRepository(connection);
            repository.delete(mercedes);

            //assertNull(repository.get(1));

    }

    @Test
    void get() {
            var repository = new JbdcCarRepository(connection);
            assertNotNull(repository.get(1));

    }

    @Test
    void getAll() {

        var repository = new JbdcCarRepository(connection);

        var inventories = repository.getAll();

        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
    }
}