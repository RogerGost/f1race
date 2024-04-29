package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import cat.uvic.teknos.f1race.repositories.DriverRepository;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;

public class DriverManager {
    private final PrintStream out;
    private final BufferedReader in;
    private final DriverRepository driverRepository;
    private final ModelFactory modelFactory;


    public DriverManager(BufferedReader in, PrintStream out, DriverRepository driverRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.driverRepository = driverRepository;
        this.modelFactory = modelFactory;

    }

    public void start(){
        out.println("Driver");


        var command = "";
        do {
            showDriverMenu();
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
        var driver = modelFactory.createDriver();

        driverRepository.save((Car) null);
    }

    private void showDriverMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
