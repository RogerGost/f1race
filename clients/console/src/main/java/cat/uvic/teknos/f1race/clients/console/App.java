package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.TeamDto;
import cat.uvic.teknos.f1race.clients.console.exceptions.RequestException;
import cat.uvic.teknos.f1race.clients.console.utils.Mappers;
import cat.uvic.teknos.f1race.clients.console.utils.RestClient;
import cat.uvic.teknos.f1race.clients.console.utils.RestClientImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class App {
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintStream out = new PrintStream(System.out);
    private static RestClientImpl restClient= new RestClientImpl("localhost", 8080);

    public static void main(String[] args) throws JsonProcessingException, RequestException {

        var command = "";
        do {
            showMainMenu();
            command = readLine(in);

            switch (command){
                case "1" -> managerTeam();


            }

        }

        while (!command.equals("exit"));

       out.println("Fi del programa");

    }

    private static void showMainMenu() {
        out.println("1. Team");
        out.println("2. Car");
        out.println("3. Driver");
        out.println("4. Sponsor");
        out.println("5. RaceResult");
    }

    static String readLine(BufferedReader in ){
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the menu option "+e);
        }
        return command;
    }

    private static void manageTeam() throws JsonProcessingException, RequestException {
        String command;
        do {
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    var teams = restClient.getAll("/teams", TeamDto[].class);
                }
                case "2" -> {
                    var teamId = readLine(in);
                    try {
                        var client = restClient.get("/teams/" + teamId, TeamDto.class);
                    } finally {

                    }
                }
                case "3" -> {
                    var teams = new TeamDto();
                    teams.setTeamName(readLine(in));

                    restClient.post("/teams", Mappers.get().writeValueAsString(teams));
                }
            }

        } while (!command.equals("exit"));
    }

    private static void managerTeam() throws RequestException, JsonProcessingException {
        TeamManager teamManager = new TeamManager(restClient, in);
        teamManager.start();
    }



}
