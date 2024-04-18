package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.file.models.Driver;
import cat.uvic.teknos.f1race.file.models.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DriverRepositoryTest {

    @Test
    /*void saveNewDriver() {

        var repository = new DriverRepository();

        var leclerc = new Driver();
        leclerc.setId(2);
        leclerc.setName("Leclerc");
        leclerc.setNationality("Monaco");
        leclerc.setTeamId(1);
        leclerc.setDate(LocalDate.of(1997, 10, 16));
        leclerc.setNumber(16);

        repository.save(leclerc);
        assertTrue(leclerc.getId() > 0);
        //posa que el id esta null pero no ho esta
        assertNotNull(repository.get(leclerc.getId()));

        DriverRepository.load();
        assertNotNull(repository.get(leclerc.getId()));



    }*/

    /*void updateDriver(){
        var path = System.getProperty("user.dir") + "/src/main/resources/driver.ser/";
        var repository = new DriverRepository();

        var leclerc = new Driver();
        leclerc.setId(1);
        leclerc.setName("Charles Leclerc");
        repository.save(leclerc);

        var updateDrive = repository.get(1);

        assertEquals(1, updateDrive.getId());

    }*/

    void deleteDriver(){
        var repository = new DriverRepository();

        // Crear un equipo y guardarlo en el repositorio
        var leclerc = new Driver();
        leclerc.setId(1);
        leclerc.setName("Leclerc");
        leclerc.setNationality("Monaco");
        leclerc.setTeamId(1);
        leclerc.setDate(LocalDate.of(1997, 10, 16));
        leclerc.setNumber(16);
        repository.save(leclerc);

        // Verificar que el equipo se haya guardado correctamente
        assertTrue(leclerc.getId() > 0);
        assertNotNull(repository.get(leclerc.getId()));

        // Eliminar el equipo del repositorio
        repository.delete(leclerc);

        // Verificar que el equipo se haya eliminado correctamente
        assertNull(repository.get(leclerc.getId()));

    }
}