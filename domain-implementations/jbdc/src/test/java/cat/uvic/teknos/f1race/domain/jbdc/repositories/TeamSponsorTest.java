package cat.uvic.teknos.f1race.domain.jbdc.repositories;
import cat.uvic.teknos.f1race.domain.jbdc.models.Team;
import cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor;

import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})


class TeamSponsorTest {

    private final Connection connection;


    public TeamSponsorTest(Connection connection) {
        this.connection = connection;
    }
    @Test
    @DisplayName("Given a new Sponsor (id = 0), when save, then a new record is added to the SPONSOR table")
    void shouldInsertNewSponsorTest() throws SQLException {

        Team team1 = createTeamWithId(1);
        Team team2 = createTeamWithId(2);
        Team team3 = createTeamWithId(3);


        Sponsor sponsor = new Sponsor();
        sponsor.setName("Test Sponsor");
        sponsor.setCountry("Test Country");
        sponsor.setPhone(123456);
        sponsor.setSponsorType("Primary");

        var teams = new HashSet<cat.uvic.teknos.f1race.models.Team>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        sponsor.setTeam(teams);

        var repository = new JbdcSponsorRepository(connection);
        repository.save(sponsor);

        assertTrue(sponsor.getId() > 0);
        assertNotNull(repository.get(sponsor.getId()));

        DbAssertions.assertThat(connection)
                .table("SPONSOR")
                .where("SPONSOR_ID", sponsor.getId())
                .hasOneLine();
    }

    private Team createTeamWithId (int id){
        Team team = new Team();
        team.setId(id);
        return team;
    }

//    @Test
//    void shouldUpdateSponsorTest() throws SQLException {
//
//        Team team1 = new Team();
//        team1.setId(1);
//
//        Sponsor sponsor = new Sponsor();
//        sponsor.setName("Updated Sponsor");
//        sponsor.setCountry("Updated Country");
//        sponsor.setPhone(654321);
//        sponsor.setSponsorType("Secondary");
//        sponsor.setId(1); // Assuming there's already a sponsor with ID 1
//
//        var repository = new JbdcSponsorRepository(connection);
//        repository.update(sponsor);
//
//        assertTrue(true);
//    }
//
//    @Test
//    void delete() throws SQLException {
//        Sponsor sponsor = new Sponsor();
//        sponsor.setId(1); // Assuming there's already a sponsor with ID 1
//
//        var repository = new JbdcSponsorRepository(connection);
//        repository.delete(sponsor);
//
//        assertNull(repository.get(sponsor.getId()));
//    }

//    @Test
//    void get() {
//        var repository = new JbdcSponsorRepository(connection);
//        assertNotNull(repository.get(1)); // Assuming there's already a sponsor with ID 1
//    }
//
//    @Test
//    void getAll() {
//        var repository = new JbdcSponsorRepository(connection);
//        var sponsors = repository.getAll();
//
//        assertNotNull(sponsors);
//        assertFalse(sponsors.isEmpty());
//    }

}