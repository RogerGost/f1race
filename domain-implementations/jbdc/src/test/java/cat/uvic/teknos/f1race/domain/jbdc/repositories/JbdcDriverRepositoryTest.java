package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Driver;
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
        this.connection = connection;
    }

    @Test
    void save() {
        Driver driver = new Driver();
        driver.setName("Leclerc");
        driver.setNationality("Monaco");
        driver.setNumber(16);
        driver.setTeamId(2);

        var repository = new JbdcDriverRepository(connection);

        repository.save(driver);

        assertTrue(driver.getId() >0);

        assertNotNull(repository.get(driver.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/
    }

    void shouldUpdateNewDriverTest() throws SQLException {


        Driver driver = new Driver();

        driver.setName("Charles Leclerc");
        driver.setNationality("Monaco");
        driver.setNumber(16);
        driver.setTeamId(1);
        driver.setId(1);


        var repository = new JbdcDriverRepository(connection);
        repository.save(driver);

        assertTrue(true);

    }

    @Test
    void delete() {

        Driver driver = new Driver();
        driver.setId(1);

        var repository = new JbdcDriverRepository(connection);
        repository.delete(driver);

        //assertNull(repository.get(1));
    }

    @Test
    void get() {
        var repository = new JbdcDriverRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
    }
}