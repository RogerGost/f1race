package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.Driver;
import cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult;
import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})


class JbdcRaceResultRepositoryTest {

    private final Connection connection;


    public JbdcRaceResultRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    void save() {

        Driver driver1 = new Driver();
        driver1.setId(2);


        RaceResult race = new RaceResult();
        race.setRaceId(12);
        race.setDriver(driver1);
        race.setPosition(14);
        race.setFastestLap("1:23:21");
        race.setPoints(24);

        var repository = new JbdcRaceResultRepository(connection);

        repository.save(race);

        assertTrue(race.getId() >0);

        assertNotNull(repository.get(race.getId()));

            /*DbAssertions.assertThat(connection)
                    .table("CAR")
                    .where("CAR_ID = ?", mercedes.getId())
                    .hasOneLine();*/
    }



    @Test
    void delete() {
        RaceResult race = new RaceResult();
        race.setId(1);

        var repository = new JbdcRaceResultRepository(connection);
        repository.delete(race);

        //assertNull(repository.get(1));

    }


    @Test
    void getAll() {
    }
}