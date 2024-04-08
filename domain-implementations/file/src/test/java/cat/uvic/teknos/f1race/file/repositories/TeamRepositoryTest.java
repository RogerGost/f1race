package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.file.models.Sponsor;
import cat.uvic.teknos.f1race.file.models.Team;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {

    @Test
    void save() {

        var repository = new TeamRepository();

        var redbull = new Team();
        redbull.setId(1);
    }
}