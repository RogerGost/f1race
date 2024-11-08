package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.utils.RestClientImpl;
import cat.uvic.teknos.f1race.clients.console.dto.TeamDto;
import cat.uvic.teknos.f1race.clients.console.exceptions.RequestException;
import cat.uvic.teknos.f1race.clients.console.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;



public class TeamManager {
    private RestClientImpl restClient;
    private BufferedReader in;

    public TeamManager(RestClientImpl restClient, BufferedReader in) {
        this.restClient = restClient;
        this.in = in;
    }





    public void start() throws RequestException, JsonProcessingException {
        String command;
        do {
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    var teams = restClient.getAll("/teams", TeamDto[].class);
                    System.out.println("Lista d'equips':");

                    showTeamsTable(teams);

                }
                case "2" -> {
                    System.out.print("introdueix ID del team ");
                    var teamId = readLine(in);
                    var team = restClient.get("/teams/" + teamId, TeamDto.class);
                    System.out.println("Detalles de team: " + team.getTeamName());
                }
                case "3" -> {

                    var team = new TeamDto();
                    System.out.print("Introdueix el nom de team: ");
                    team.setTeamName(readLine(in));
                    System.out.print("Introdueix el pais de team: ");
                    team.setHeadquarters(readLine(in));
                    System.out.print("Introdueix el principal de team: ");
                    team.setPrincipalName(readLine(in));
                    System.out.print("Introdueix sponsor de team: ");
                    team.setSponsorName(readLine(in));
                    restClient.post("/teams", Mappers.get().writeValueAsString(team));
                    System.out.println("team agregat.");
                }
                case "4" -> {
                    System.out.println("Exit...");
                }
                default -> System.out.println("Comand no valid.");
            }

        } while (!command.equals("4"));
    }

    private String readLine(BufferedReader in) {
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e);
        }
        return command;
    }
    private void showMenu() {
        System.out.println("\n--- Menu de Gestio Equips F1 ---");
        System.out.println("1. Lista tots els equips");
        System.out.println("2. Ver detalls de un equip");
        System.out.println("3. Agregar un nou equip");
        System.out.println("4. Exit");
    }
    private void showTeamsTable(TeamDto[] teams) {
        String table = AsciiTable.getTable(Arrays.asList(teams), Arrays.asList(
                new Column().header("Id").with(team -> String.valueOf(team.getId())),
                new Column().header("Name").with(TeamDto::getTeamName),
                new Column().header("Team Principal").with(TeamDto::getPrincipalName),
                new Column().header("Headquarters").with(TeamDto::getHeadquarters),
                new Column().header("Principal Sponsor").with(TeamDto::getSponsorName)
        ));
        System.out.println(table);
    }


}
