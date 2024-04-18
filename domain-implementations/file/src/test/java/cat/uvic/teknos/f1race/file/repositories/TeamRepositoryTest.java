package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.file.models.Driver;
import cat.uvic.teknos.f1race.file.models.Sponsor;
import cat.uvic.teknos.f1race.file.models.Team;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import java.util.jar.Attributes;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {

    @Test
    void saveNewTeam() {

        var repository = new TeamRepository();

        var ferrari = new Team();
        ferrari.setId(1);
        ferrari.setTeamName("Ferrari");
        ferrari.setHeadquarters("Maranello");
        ferrari.setPrincipalName("Binotto");
        ferrari.setSponsor("Santander");



        repository.save(ferrari);
        assertTrue(ferrari.getId() > 0);
        assertNotNull(repository.get(ferrari.getId()));

        TeamRepository.load();
        assertNotNull(repository.get(ferrari.getId()));



    }

   void updateTeam(){
        var path = System.getProperty("user.dir") + "/src/main/resources/team.ser/";
        var repository = new TeamRepository();

        var ferrari = new Team();
        ferrari.setId(1);
        ferrari.setPrincipalName("Bino");
        repository.save(ferrari);

        var updateTeam = repository.get(1);

        assertEquals(1, updateTeam.getId());

    }

    void deleteTeam(){
        var repository = new TeamRepository();

        var ferrari = new Team();
        repository.delete(ferrari);
        assertNull(repository.get(ferrari.getId()));

    }
    }