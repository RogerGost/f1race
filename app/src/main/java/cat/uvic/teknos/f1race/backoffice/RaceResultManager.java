package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;

public class RaceResultManager {

    private final PrintStream out;
    private final BufferedReader in;
    private final RaceResultRepository raceResultRepository;
    private final ModelFactory modelFactory;


    public RaceResultManager(BufferedReader in, PrintStream out, RaceResultRepository raceResultRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.raceResultRepository = raceResultRepository;
        this.modelFactory = modelFactory;

    }

    public void start(){
        out.println("Team");


        var command = "";
        do {
            showRaceResultMenu();
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
        out.println("\n List of Results \n");

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("ID", "RACE_ID", "DRIVER_ID", "POSITION","FAST_LAP", "POINTS");
        asciiTable.addRule();

        for (var race : raceResultRepository.getAll()) {
            asciiTable.addRow(race.getId(), race.getRaceId(), race.getDriverId(), race.getPosition(), race.getFastestLap(), race.getPoints());
            asciiTable.addRule();
        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);

        String render = asciiTable.render();
        out.println(render);
    }

    private void delete() {
        var race = modelFactory.createRaceResult();

        out.println("Enter the ID of the race to delete:");
        int id = Integer.parseInt(readLine(in));
        race.setId(id);

        raceResultRepository.delete(race);
    }

    private void update() {
        try {
            var race = modelFactory.createRaceResult();

            out.println("Enter the ID of the RaceResult to update:");
            int id = Integer.parseInt(readLine(in));
            race.setId(id);

            out.println("RaceID");
            race.setRaceId(Integer.parseInt(readLine(in)));

            out.println("DriverID");
            int driverId = Integer.parseInt(readLine(in));
            Driver driver = new cat.uvic.teknos.f1race.domain.jbdc.models.Driver();
            driver.setId(driverId);
            race.setDriver(driver);

            out.println("Position");
            race.setPosition(Integer.parseInt(readLine(in)));

            out.println("Points");
            race.setPoints(Integer.parseInt(readLine(in)));

            raceResultRepository.save(race);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid team ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the team: " + e.getMessage());
        }
    }

    private void insert() {
        var raceResult = modelFactory.createRaceResult();

        out.println("RaceID");
        raceResult.setRaceId(Integer.parseInt(readLine(in)));

        out.println("DriverID");
        int driverId = Integer.parseInt(readLine(in));
        Driver driver = new cat.uvic.teknos.f1race.domain.jbdc.models.Driver();
        driver.setId(driverId);
        raceResult.setDriver(driver);

        out.println("Position");
        raceResult.setPosition(Integer.parseInt(readLine(in)));

        out.println("FastLap");
        raceResult.setFastestLap(readLine(in));

        out.println("Points");
        raceResult.setPoints(Integer.parseInt(readLine(in)));

        raceResultRepository.save(raceResult);

        out.println("Insert OK");
    }

    private void showRaceResultMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
