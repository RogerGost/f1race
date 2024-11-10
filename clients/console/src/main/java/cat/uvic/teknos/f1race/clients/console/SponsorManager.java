package cat.uvic.teknos.f1race.clients.console;

import cat.uvic.teknos.f1race.clients.console.dto.CarDto;
import cat.uvic.teknos.f1race.clients.console.dto.SponsorDto;
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

public class SponsorManager {
    private RestClientImpl restClient;
    private BufferedReader in;

    public SponsorManager(RestClientImpl restClient, BufferedReader in) {
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
                    var sponsors = restClient.getAll("/sponsors", SponsorDto[].class);
                    System.out.println("Lista sponsors':");

                    showSponsorsTable(sponsors);

                }
                case "2" -> {
                    System.out.print("introdueix ID del car");
                    var sponsorId = readLine(in);
                    var sponsor = restClient.get("/sponsors/" + sponsorId, SponsorDto.class);

                    showSponsorTable(sponsor);
                }
                case "3" -> {

                    var sponsor = new SponsorDto();
                    System.out.print("Introdueix el nom: ");
                    sponsor.setName(readLine(in));
                    System.out.print("Introdueix el pais: ");
                    sponsor.setCountry(readLine(in));
                    System.out.print("Introdueix el phone: ");
                    sponsor.setPhone(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el sponsorType: ");
                    sponsor.setSponsorType(readLine(in));

                    restClient.post("/sponsors", Mappers.get().writeValueAsString(sponsor));
                    System.out.println("sponsor agregat.");
                }
                case "4" -> {
                    System.out.println("Selecciona car per eliminar");
                    var carId = readLine(in);
                    restClient.delete("/cars/" + carId, null);
                    System.out.println("Car eliminat.");
                }
                case "5" -> {
                    System.out.println("Selecciona un car per editar");
                    var sponsorId = readLine(in);
                    var sponsor = new SponsorDto();
                    System.out.print("Introdueix el nom: ");
                    sponsor.setName(readLine(in));
                    System.out.print("Introdueix el pais: ");
                    sponsor.setCountry(readLine(in));
                    System.out.print("Introdueix el phone: ");
                    sponsor.setPhone(Integer.parseInt(readLine(in)));
                    System.out.print("Introdueix el sponsorType: ");
                    sponsor.setSponsorType(readLine(in));

                    restClient.put("/sponsors/"+sponsorId, Mappers.get().writeValueAsString(sponsor));
                    System.out.println("Sponsor editat correcte");
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
        System.out.println("1. Lista tots els sponsors");
        System.out.println("2. Ver detalls de un sponsor");
        System.out.println("3. Agregar un nou sponsor");
        System.out.println("4. Eliminar sponsor");
        System.out.println("5. Editar sponsor");
    }
    private void showSponsorsTable(SponsorDto[] sponsors) {
        String table = AsciiTable.getTable(Arrays.asList(sponsors), Arrays.asList(
                new Column().header("Id").with(sponsor -> String.valueOf(sponsor.getId())),
                new Column().header("Name").with(SponsorDto::getName),
                new Column().header("Country").with(SponsorDto::getCountry),
                new Column().header("Phone").with(sponsor -> String.valueOf(sponsor.getPhone())),
                new Column().header("Sponsor Type").with(SponsorDto::getSponsorType)
        ));
        System.out.println(table);
    }

    private void showSponsorTable(SponsorDto sponsor) {
        String table = AsciiTable.getTable(Arrays.asList(sponsor), Arrays.asList(
                new Column().header("Id").with(s -> String.valueOf(s.getId())),
                new Column().header("Name").with(SponsorDto::getName),
                new Column().header("Country").with(SponsorDto::getCountry),
                new Column().header("Phone").with(s -> String.valueOf(s.getPhone())),
                new Column().header("Sponsor Type").with(SponsorDto::getSponsorType)
        ));
        System.out.println(table);
    }



}
