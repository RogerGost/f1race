package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;

import java.io.*;
import java.util.stream.Collectors;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;


    public BackOffice(InputStream inputStream, OutputStream outputStream, RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    public void start(){
        showBanner();
        showWelcomeMessage();

        var command = "";
        do {
            showMainMenu();
            command = readLine(in);

            switch (command){
                case "1" -> manageTeam();
                case "2" -> managerCar();
                case "3" -> managerDriver();
                case "4" -> managerSponsor();
                case "5" -> managerRaceResult();

            }

        }

        while (!command.equals("exit"));

        out.println("Fi del programa");

    }



    private void managerRaceResult() {
        new RaceResultManager(in, out,repositoryFactory.getRaceResultRepository(),modelFactory).start();

    }

    private void managerSponsor() {
        new SponsorManager(in, out,repositoryFactory.getSponsorRepository(),modelFactory).start();

    }

    private void managerDriver() {
        new DriverManager(in, out, repositoryFactory.getDriverRepository(),modelFactory).start();

    }

    private void managerCar() {
        new CarManager(in, out, repositoryFactory.getCarRepository(),modelFactory).start();
    }

    private void manageTeam() {
        new TeamManager(in, out, repositoryFactory.getTeamRepository(), modelFactory).start();
    }



    private void showWelcomeMessage() {
        out.println("Welcome to the F1Race Black Office");
        out.println("Select a menu option");
        out.println(" ");
    }

    private void showMainMenu() {
        out.println("1. Team");
        out.println("2. Car");
        out.println("3. Driver");
        out.println("4. Sponsor");
        out.println("5. RaceResult");
    }

    private void showBanner() {
        var bannerStream = BackOffice.class.getResourceAsStream("/banner.txt");

        var banner = new BufferedReader(new InputStreamReader(bannerStream))
                .lines().collect(Collectors.joining("\n"));

        out.println(banner);
    }

}
