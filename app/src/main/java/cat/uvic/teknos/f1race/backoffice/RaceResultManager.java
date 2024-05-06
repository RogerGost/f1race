package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import cat.uvic.teknos.f1race.repositories.TeamRepository;

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
            race.setDriverId(Integer.parseInt(readLine(in)));

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
        raceResult.setDriverId(Integer.parseInt(readLine(in)));

        out.println("Position");
        raceResult.setPosition(Integer.parseInt(readLine(in)));

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
