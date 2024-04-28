package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})

class JbdcSponsorRepositoryTest {

    private final Connection connection;



    public JbdcSponsorRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    void shouldInsertNewSponsorTest() throws SQLException {


        Sponsor santander = new Sponsor();
        santander.setName("Santader");
        santander.setCountry("España");
        santander.setPhone(1234567);
        santander.setSponsorType("Bank");



        var repository = new JbdcSponsorRepository(connection);

        repository.save(santander);

        assertTrue(santander.getId() >0);

        assertNotNull(repository.get(santander.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/

    }


    void shouldUpdateNewCarTest() throws SQLException {


        Sponsor santander = new Sponsor();

        santander.setName("Santader");
        santander.setCountry("España");
        santander.setPhone(1234567);
        santander.setSponsorType("Bank");


        var repository = new JbdcSponsorRepository(connection);
        repository.save(santander);

        assertTrue(true);

    }

    @Test
    void delete() throws SQLException {
        Sponsor santander = new Sponsor();
        santander.setId(4);

        var repository = new JbdcSponsorRepository(connection);
        repository.delete(santander);

        //assertNull(repository.get(1));

    }

    @Test
    void get() {
        var repository = new JbdcSponsorRepository(connection);
        assertNotNull(repository.get(1));

    }

    @Test
    void getAll() {
    }
}