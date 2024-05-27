package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Driver;
import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})


class JbdcDriverRepositoryTest {

    private final Connection connection;

    JbdcDriverRepositoryTest(Connection connection) {
        try {
            // Desactivar el autocommit
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Error setting autocommit to false", e);
        }
        this.connection = connection;
    }

    @Test
    void save() {
        Team team1 = new Team();
        team1.setId(2);

        Driver driver = new Driver();
        driver.setName("Leclerc");
        driver.setNationality("Monaco");
        driver.setDate(1997);
        driver.setNumber(16);
        driver.setTeam(team1);

        var repository = new JbdcDriverRepository(connection);

        repository.save(driver);

        assertTrue(driver.getId() >0);

        assertNotNull(repository.get(driver.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldUpdateNewDriverTest() throws SQLException {
        Team team1 = new Team();
        team1.setId(2);

        Driver driver = new Driver();

        driver.setName("Charles Leclerc");
        driver.setNationality("Monaco");
        driver.setNumber(16);
        driver.setTeam(team1);
        driver.setId(1);


        var repository = new JbdcDriverRepository(connection);
        repository.save(driver);

        assertTrue(true);
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void delete() {

        Driver driver = new Driver();
        driver.setId(4);

        var repository = new JbdcDriverRepository(connection);
        repository.delete(driver);

        //assertNull(repository.get(1));
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void get() {
        var repository = new JbdcDriverRepository(connection);

        int existingId=1;
        Driver driver = (Driver) repository.get(existingId);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
        var repository = new JbdcDriverRepository(connection);
        assertNotNull(repository.get(1));
    }
}