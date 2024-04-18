package cat.uvic.teknos.f1race.domain.jbdc.repositories;

import cat.uvic.teknos.f1race.domain.jbdc.models.Car;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JbdcCarRepositoryTest {

    @Test
    @DisplayName("Given a new Car (id = 0), when save, then a new record is added to the CAR table")
    void shouldInsertNewCarTest() throws SQLException {

        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/formula1", "root", "2004")) {

            Car mercedes = new Car();
            mercedes.setModel("mc2024");
            mercedes.setEngine("mercedes1");
            mercedes.setChassis("C1mercedes");

            var repository = new JbdcCarRepository(connection);
            repository.save(mercedes);

            assertTrue(mercedes.getId() >0);
        }
    }


    void shouldUpdateNewCarTest() throws SQLException {

        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/formula1", "root", "2004")) {

            Car mercedes = new Car();

            mercedes.setModel("444");
            mercedes.setEngine("mercedes2");
            mercedes.setChassis("C2mercedes");
            mercedes.setTeamId(1);
            mercedes.setId(1);


            var repository = new JbdcCarRepository(connection);
            repository.save(mercedes);

            assertTrue(true);
        }
    }

    @Test
    void delete() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/formula1", "root", "2004")) {
            Car mercedes = new Car();
            mercedes.setId(1);
            delete();
        }
        }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }
}