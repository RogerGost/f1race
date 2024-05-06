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
        var driver = modelFactory.createDriver();

        out.println("Enter the ID of the driver to delete:");
        int id = Integer.parseInt(readLine(in));
        driver.setId(id);

        driverRepository.delete(driver);
    }

    private void update() {
        try {
            var driver = modelFactory.createDriver();

            out.println("Enter the ID of the driver to update:");
            int id = Integer.parseInt(readLine(in));
            driver.setId(id);

            out.println("Name");
            driver.setName(readLine(in));

            out.println("Nationality");
            driver.setNationality(readLine(in));

            out.println("Number");
            driver.setNumber(Integer.parseInt(readLine(in)));

            out.println("Team ID");
            driver.setTeamId(Integer.parseInt(readLine(in)));

            driverRepository.save(driver);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid team ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the team: " + e.getMessage());
        }
    }

    private void insert() {
        var driver = modelFactory.createDriver();

        out.println("Name");
        driver.setName(readLine(in));

        out.println("Nationality");
        driver.setNationality(readLine(in));

        out.println("Number");
        driver.setNumber(Integer.parseInt(readLine(in)));

        out.println("TeamID");
        driver.setTeamId(Integer.parseInt(readLine(in)));


        driverRepository.save(driver);

        out.println("Insert OK");
    }

    private void showDriverMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
