package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.CarDto;
import cat.uvic.teknos.f1race.clients.console.dto.DriverDto;
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

public class DriverManager {
    private RestClientImpl restClient;
    private BufferedReader in;

    public DriverManager(RestClientImpl restClient, BufferedReader in) {
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
                    var drivers = restClient.getAll("/drivers", DriverDto[].class);
                    System.out.println("Lista cars':");

                    showDriversTable(drivers);

                }
                case "2" -> {
                    System.out.print("introdueix ID del driver");
                    var driverId = readLine(in);
                    var driver = restClient.get("/drivers/" + driverId, DriverDto.class);

                    showDriverTable(driver);
                }
                case "3" -> {

                    var driver = new DriverDto();
                    System.out.print("Introdueix el nom: ");
                    driver.setName(readLine(in));
                    System.out.print("Introdueix la nacionalitat: ");
                    driver.setNationality(readLine(in));
                    System.out.print("Introdueix l'any de neixament: ");
                    driver.setDate(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el numero: ");
                    driver.setNumber(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el teamID: ");
                    int teamId = Integer.parseInt(readLine(in));
                    var team = restClient.get("/teams/" + teamId, Team.class);
                    driver.setTeam(team);
                    restClient.post("/drivers", Mappers.get().writeValueAsString(driver));
                    System.out.println("driver agregat.");
                }
                case "4" -> {
                    System.out.println("Selecciona driver per eliminar");
                    var driverId = readLine(in);
                    restClient.delete("/drivers/" + driverId, null);
                    System.out.println("Driver eliminat.");
                }
                case "5" -> {
                    System.out.println("Selecciona un car per editar");
                    var driverId = readLine(in);
                    var driver = new DriverDto();
                    System.out.print("Introdueix el nom: ");
                    driver.setName(readLine(in));
                    System.out.print("Introdueix la nacionalitat: ");
                    driver.setNationality(readLine(in));
                    System.out.print("Introdueix l'any de neixament: ");
                    driver.setDate(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el numero: ");
                    driver.setNumber(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el teamID: ");
                    int teamId = Integer.parseInt(readLine(in));
                    var team = restClient.get("/teams/" + teamId, Team.class);
                    driver.setTeam(team);

                    restClient.put("/drivers/"+driverId, Mappers.get().writeValueAsString(driver));
                    System.out.println("Driver editat correcte");
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
        System.out.println("1. Lista tots els drivers");
        System.out.println("2. Detalls de un driver");
        System.out.println("3. Insert un nou driver");
        System.out.println("4. Eliminar driver");
        System.out.println("5. Editar driver");
    }
    private void showDriversTable(DriverDto[] drivers) {
        String table = AsciiTable.getTable(Arrays.asList(drivers), Arrays.asList(
                new Column().header("Id").with(driver -> String.valueOf(driver.getId())),
                new Column().header("Name").with(DriverDto::getName),
                new Column().header("Nationality").with(DriverDto::getNationality),
                new Column().header("Birth Year").with(driver -> String.valueOf(driver.getDate())),
                new Column().header("Number").with(driver -> String.valueOf(driver.getNumber())),
                new Column().header("Team ID").with(driver -> driver.getTeam() != null ? String.valueOf(driver.getTeam().getId()) : "N/A")
        ));
        System.out.println(table);
    }

    private void showDriverTable(DriverDto driver) {
        String table = AsciiTable.getTable(Arrays.asList(driver), Arrays.asList(
                new Column().header("Id").with(d -> String.valueOf(d.getId())),
                new Column().header("Name").with(DriverDto::getName),
                new Column().header("Nationality").with(DriverDto::getNationality),
                new Column().header("Birth Year").with(d -> String.valueOf(d.getDate())),
                new Column().header("Number").with(d -> String.valueOf(d.getNumber())),
                new Column().header("Team ID").with(d -> d.getTeam() != null ? String.valueOf(d.getTeam().getId()) : "N/A")
        ));
        System.out.println(table);
    }



}
