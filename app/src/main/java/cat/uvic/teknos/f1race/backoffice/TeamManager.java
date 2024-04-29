package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import static cat.uvic.teknos.f1race.backoffice.IOUtilis.*;

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

    public void start(){
        out.println("Team");


        var command = "";
        do {
            showTeamMenu();
            command = readLine(in);

            switch (command){
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> getAll();
            }

        }
        while (!command.equals("exit"));

        out.println("Fi del programa");
    }

    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("ID","NAME", "PRINCIPAL", "HEADQUARTERS","SPONSOR");
        asciiTable.addRule();

        for (var team : teamRepository.getAll()){
            asciiTable.addRow(team.getId(), team.getTeamName(), team.getPrincipalName(), team.getHeadquarters(), team.getSponsor());
            asciiTable.addRule();

        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);

    }

    private void delete() {
    }

    private void update() {
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
        team.setSponsor(readLine(in));

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
