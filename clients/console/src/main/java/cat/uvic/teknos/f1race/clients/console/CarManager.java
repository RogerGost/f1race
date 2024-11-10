package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.CarDto;
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

public class CarManager {
    private RestClientImpl restClient;
    private BufferedReader in;

    public CarManager(RestClientImpl restClient, BufferedReader in) {
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
                    var cars = restClient.getAll("/cars", CarDto[].class);
                    System.out.println("Lista cars':");

                    showCarsTable(cars);

                }
                case "2" -> {
                    System.out.print("introdueix ID del car");
                    var carId = readLine(in);
                    var car = restClient.get("/cars/" + carId, CarDto.class);

                    showCarTable(car);
                }
                case "3" -> {

                    var car = new CarDto();
                    System.out.print("Introdueix el chassis: ");
                    car.setChassis(readLine(in));
                    System.out.print("Introdueix el engine: ");
                    car.setEngine(readLine(in));
                    System.out.print("Introdueix el model: ");
                    car.setModel(readLine(in));
                    System.out.print("Introdueix el teamID: ");
                    int teamId = Integer.parseInt(readLine(in));
                    var team = restClient.get("/teams/" + teamId, Team.class);
                    car.setTeam(team);
                    restClient.post("/cars", Mappers.get().writeValueAsString(car));
                    System.out.println("car agregat.");
                }
                case "4" -> {
                    System.out.println("Selecciona car per eliminar");
                    var carId = readLine(in);
                    restClient.delete("/cars/" + carId, null);
                    System.out.println("Car eliminat.");
                }
                case "5" -> {
                    System.out.println("Selecciona un car per editar");
                    var carId = readLine(in);
                    var car = new CarDto();
                    System.out.print("Introdueix el chassis: ");
                    car.setChassis(readLine(in));
                    System.out.print("Introdueix el engine ");
                    car.setEngine(readLine(in));
                    System.out.print("Introdueix el mdoel: ");
                    car.setModel(readLine(in));
                    System.out.print("Introdueix el teamID: ");
                    int teamId = Integer.parseInt(readLine(in));
                    var team = restClient.get("/teams/" + teamId, Team.class);
                    car.setTeam(team);

                    restClient.put("/cars/"+carId, Mappers.get().writeValueAsString(car));
                    System.out.println("Car editat correcte");
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
        System.out.println("1. Lista tots els cars");
        System.out.println("2. Ver detalls de un car");
        System.out.println("3. Agregar un nou car");
        System.out.println("4. Eliminar car");
        System.out.println("5. Editar car");
    }
    private void showCarsTable(CarDto[] cars) {
        String table = AsciiTable.getTable(Arrays.asList(cars), Arrays.asList(
                new Column().header("Id").with(car -> String.valueOf(car.getId())),
                new Column().header("Chassis").with(CarDto::getChassis),
                new Column().header("Engine").with(CarDto::getEngine),
                new Column().header("Model").with(CarDto::getModel),
                new Column().header("Team ID").with(c -> String.valueOf(c.getTeam() != null ? c.getTeam().getId() : "N/A"))
        ));
        System.out.println(table);
    }

    private void showCarTable(CarDto car) {
        String table = AsciiTable.getTable(Arrays.asList(car), Arrays.asList(
                new Column().header("Id").with(c -> String.valueOf(c.getId())),
                new Column().header("Chassis").with(CarDto::getChassis),
                new Column().header("Engine").with(CarDto::getEngine),
                new Column().header("Model").with(CarDto::getModel),
                new Column().header("Team ID").with(c -> String.valueOf(c.getTeam() != null ? c.getTeam().getId() : "N/A"))
        ));
        System.out.println(table);
    }


}
