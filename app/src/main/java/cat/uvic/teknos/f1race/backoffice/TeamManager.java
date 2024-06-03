package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Arrays;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;

public class TeamManager {
    private final PrintStream out;
    private final BufferedReader in;
    private final TeamRepository teamRepository;
    private final ModelFactory modelFactory;

    public TeamManager(BufferedReader in, PrintStream out, TeamRepository teamRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.teamRepository = teamRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        out.println("Team");

        var command = "";
        do {
            showTeamMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> getAll();
            }

        } while (!command.equals("exit"));

        out.println("End of program");
    }

    private void getAll() {
        out.println("\n List of Teams \n");

        var teams = teamRepository.getAll();

        String table = AsciiTable.getTable(teams, Arrays.asList(
                new Column().header("Id").with(team -> String.valueOf(team.getId())),
                new Column().header("Name").with(team -> team.getTeamName()),
                new Column().header("Team Principal").with(team -> team.getPrincipalName()),
                new Column().header("Headquarters").with(team -> team.getHeadquarters()),
                new Column().header("Principal Sponsor").with(team -> team.getSponsorName())
        ));

        out.println(table);
    }

    private void delete() {
        var team = modelFactory.createTeam();

        out.println("Enter the ID of the team to delete:");
        int id = Integer.parseInt(readLine(in));
        team.setId(id);

        teamRepository.delete(team);
    }

    private void update() {
        try {
            var team = modelFactory.createTeam();

            out.println("Enter the ID of the team to update:");
            int id = Integer.parseInt(readLine(in));
            team.setId(id);

            out.println("Name");
            team.setTeamName(readLine(in));

            out.println("Principal");
            team.setPrincipalName(readLine(in));

            out.println("Headquarters");
            team.setHeadquarters(readLine(in));

            out.println("Sponsor");
            team.setSponsorName(readLine(in));

            teamRepository.update(team);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid team ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the team: " + e.getMessage());
        }
    }

    private void insert() {
        var team = modelFactory.createTeam();

        out.println("Name");
        team.setTeamName(readLine(in));

        out.println("Principal");
        team.setPrincipalName(readLine(in));

        out.println("Headquarters");
        team.setHeadquarters(readLine(in));

        out.println("Sponsor");
        team.setSponsorName(readLine(in));

        teamRepository.save(team);

        out.println("Insert OK");
    }

    private void showTeamMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
