package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult;
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

        RaceResult race = new RaceResult();
        race.setRaceId(12);
        race.setDriverId(1);
        race.setPosition(14);
        Time fastestLapTime = Time.valueOf("01:23:59");
        race.setFastestLap(fastestLapTime);
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
    void shouldUpdateNewRaceTest() throws SQLException {


        RaceResult race = new RaceResult();

        race.setRaceId(12);
        race.setDriverId(1);
        race.setPosition(4);
        race.setFastestLap(Time.valueOf(LocalTime.of(01,23,59)));
        race.setPoints(24);
        race.setId(1);


        var repository = new JbdcRaceResultRepository(connection);
        repository.save(race);

        assertTrue(true);

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
    void get() {

        var repository = new JbdcRaceResultRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
    }
}