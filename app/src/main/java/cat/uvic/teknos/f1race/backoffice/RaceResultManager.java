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
    }

    private void update() {
    }

    private void insert() {
        var raceResult = modelFactory.createRaceResult();

        raceResultRepository.save(null);
    }

    private void showRaceResultMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
