package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.f1race.backoffice.IOUtilis.readLine;

public class CarManager {

    private final PrintStream out;
    private final BufferedReader in;
    private final CarRepository carRepository;
    private final ModelFactory modelFactory;

    public CarManager(BufferedReader in, PrintStream out, CarRepository carRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.carRepository = carRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        out.println("Car");

        String command;
        do {
            showCarMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> getAll();
            }

        } while (!command.equals("exit"));

        out.println("End of the program");
    }

    private void getAll() {
        out.println("\nList of Cars\n");

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("ID", "MODEL", "ENGINE", "CHASSIS", "TEAM_ID");
        asciiTable.addRule();

        for (var car : carRepository.getAll()) {
            asciiTable.addRow(car.getId(), car.getModel(), car.getEngine(), car.getChassis(), car.getTeam().getId());
            asciiTable.addRule();
        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);

        String render = asciiTable.render();
        out.println(render);
    }

    private void delete() {
        out.println("Enter the ID of the car to delete:");
        int id = Integer.parseInt(readLine(in));
        Car car = carRepository.get(id);
        if (car != null) {
            carRepository.delete(car);
            out.println("Car deleted successfully");
        } else {
            out.println("Car not found");
        }
    }

    private void update() {
        try {
            out.println("Enter the ID of the car to update:");
            int id = Integer.parseInt(readLine(in));
            Car car = carRepository.get(id);
            if (car == null) {
                out.println("Car not found");
                return;
            }

            out.println("Model");
            car.setModel(readLine(in));

            out.println("Engine");
            car.setEngine(readLine(in));

            out.println("Chassis");
            car.setChassis(readLine(in));

            out.println("Team ID");
            int teamId = Integer.parseInt(readLine(in));
            Team team = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
            team.setId(teamId);
            car.setTeam(team);

            carRepository.save(car);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid team ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the car: " + e.getMessage());
        }
    }

    private void insert() {
        try {
            Car car = modelFactory.createCar();

            out.println("Model");
            car.setModel(readLine(in));

            out.println("Engine");
            car.setEngine(readLine(in));

            out.println("Chassis");
            car.setChassis(readLine(in));

            out.println("Team ID");
            int teamId = Integer.parseInt(readLine(in));
            Team team = new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
            team.setId(teamId);
            car.setTeam(team);

            carRepository.save(car);

            out.println("Insert successful");
        } catch (NumberFormatException e) {
            out.println("Invalid input. Please enter valid data.");
        } catch (Exception e) {
            out.println("An error occurred while inserting the car: " + e.getMessage());
        }
    }

    private void showCarMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
        out.println("Type 'exit' to quit");
    }
}
