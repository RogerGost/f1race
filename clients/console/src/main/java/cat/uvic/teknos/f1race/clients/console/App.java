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
import java.util.stream.Collectors;

public class App {
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintStream out = new PrintStream(System.out);
    private static RestClientImpl restClient= new RestClientImpl("localhost", 8080);

    public static void main(String[] args) throws JsonProcessingException, RequestException {

        var command = "";
        do {
            showBanner();
            showMainMenu();
            command = readLine(in);

            switch (command){
                case "1" -> managerTeam();
                case "2" -> managerCar();
                case "3" -> managerDriver();
                case "4" -> managerRace();
                case "5" -> managerSponsor();


            }

        }

        while (!command.equals("exit"));

       out.println("Fi del programa");

    }

    private static void showMainMenu() {
        out.println("1. Team");
        out.println("2. Car");
        out.println("3. Driver");
        out.println("4. RaceResult");
        out.println("5. Sponsor");
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



    private static void managerTeam() throws RequestException, JsonProcessingException {
        TeamManager teamManager = new TeamManager(restClient, in);
        teamManager.start();
    }

    private static void managerCar() throws RequestException, JsonProcessingException {
        CarManager carManager = new CarManager(restClient, in);
        carManager.start();
    }
    private static void managerDriver() throws RequestException, JsonProcessingException {
        DriverManager driverManager = new DriverManager(restClient, in);
        driverManager.start();
    }

    private static void managerRace() throws RequestException, JsonProcessingException {
        RaceResultManager raceResultManager = new RaceResultManager(restClient, in);
        raceResultManager.start();
    }

    private static void managerSponsor() throws RequestException, JsonProcessingException {
        SponsorManager sponsorManager = new SponsorManager(restClient, in);
        sponsorManager.start();
    }

    private static void showBanner() {
        var bannerStream = App.class.getResourceAsStream("/banner.txt");

        var banner = new BufferedReader(new InputStreamReader(bannerStream))
                .lines().collect(Collectors.joining("\n"));

        out.println(banner);
    }



}
