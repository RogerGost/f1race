package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.CarDto;
import cat.uvic.teknos.f1race.clients.console.dto.DriverDto;
import cat.uvic.teknos.f1race.clients.console.dto.RaceResultDto;
import cat.uvic.teknos.f1race.clients.console.dto.TeamDto;
import cat.uvic.teknos.f1race.clients.console.exceptions.RequestException;
import cat.uvic.teknos.f1race.clients.console.utils.Mappers;
import cat.uvic.teknos.f1race.clients.console.utils.RestClientImpl;
import cat.uvic.teknos.f1race.models.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class RaceResultManager {
    private RestClientImpl restClient;
    private BufferedReader in;

    public RaceResultManager(RestClientImpl restClient, BufferedReader in) {
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
                    var races = restClient.getAll("/racesResults", RaceResultDto[].class);
                    System.out.println("Llista races':");

                    showRacesTable(races);

                }
                case "2" -> {
                    System.out.print("introdueix ID del car");
                    var raceId = readLine(in);
                    var race = restClient.get("/racesResults/" + raceId, RaceResultDto.class);

                    showRaceTable(race);
                }
                case "3" -> {
                    var race = new RaceResultDto();
                    System.out.print("Introdueix el raceId: ");
                    race.setRaceId(Integer.parseInt(readLine(in)));

                    System.out.print("Introdueix el driverId: ");
                    int driverId = Integer.parseInt(readLine(in));
                    var driver = restClient.get("/drivers/" + driverId, DriverDto.class);
                    race.setDriver(driver);

                    System.out.print("Introdueix la posicio: ");
                    race.setPosition(Integer.parseInt(readLine(in)));

                    System.out.print("Introdueix el fastestLap (en segundos): ");
                    race.setFastestLap(readLine(in));

                    System.out.print("Introdueix els punts: ");
                    race.setPoints(Integer.parseInt(readLine(in)));

                    restClient.post("/racesResults", Mappers.get().writeValueAsString(race));
                    System.out.println("race agregat.");
                }

                case "4" -> {
                    System.out.println("Selecciona race per eliminar");
                    var raceID = readLine(in);
                    restClient.delete("/racesResults/" + raceID, null);
                    System.out.println("Race eliminat.");
                }
                case "5" -> {
                    System.out.println("Selecciona un driver per editar");
                    var raceId = readLine(in);
                    var race = new RaceResultDto();
                    System.out.print("Introdueix el raceId: ");
                    race.setRaceId(Integer.parseInt(readLine(in)));

                    System.out.print("Introdueix el driverId: ");
                    int driverId = Integer.parseInt(readLine(in));
                    var driver = restClient.get("/drivers/" + driverId, DriverDto.class);
                    race.setDriver(driver);

                    System.out.print("Introdueix la posicio: ");
                    race.setPosition(Integer.parseInt(readLine(in)));

                    System.out.print("Introdueix el fastestLap (en segundos): ");
                    race.setFastestLap(readLine(in));

                    System.out.print("Introdueix els punts: ");
                    race.setPoints(Integer.parseInt(readLine(in)));

                    restClient.put("/racesResults/"+raceId, Mappers.get().writeValueAsString(driver));
                    System.out.println("race editat correcte");
                }

                default -> System.out.println("Comand no valid.");
            }

        } while (!command.equals("exit"));
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
        System.out.println("1. Lista totes les races");
        System.out.println("2. Ver detalls de una race");
        System.out.println("3. Agregar una nova race");
        System.out.println("4. Eliminar race");
        System.out.println("5. Editar race");
    }
    private void showRacesTable(RaceResultDto[] races) {
        String table = AsciiTable.getTable(Arrays.asList(races), Arrays.asList(
                new Column().header("Result ID").with(race -> String.valueOf(race.getId())),
                new Column().header("Race ID").with(race -> String.valueOf(race.getRaceId())),
                new Column().header("Position").with(race -> String.valueOf(race.getPosition())),
                new Column().header("Fastest Lap").with(RaceResultDto::getFastestLap),
                new Column().header("Points").with(race -> String.valueOf(race.getPoints())),
                new Column().header("Driver ID").with(race -> String.valueOf(race.getDriver() != null ? race.getDriver().getId() : "N/A"))
        ));
        System.out.println(table);
    }

    private void showRaceTable(RaceResultDto race) {
        String table = AsciiTable.getTable(Arrays.asList(race), Arrays.asList(
                new Column().header("Race ID").with(r -> String.valueOf(r.getRaceId())),
                new Column().header("Position").with(r -> String.valueOf(r.getPosition())),
                new Column().header("Fastest Lap").with(RaceResultDto::getFastestLap),
                new Column().header("Points").with(r -> String.valueOf(r.getPoints())),
                new Column().header("Driver ID").with(r -> String.valueOf(r.getDriver() != null ? r.getDriver().getId() : "N/A"))
        ));
        System.out.println(table);
    }



}
