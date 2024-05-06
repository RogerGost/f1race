package cat.uvic.teknos.f1race.backoffice;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;

import java.io.BufferedReader;
import java.io.PrintStream;
import static cat.uvic.teknos.f1race.backoffice.IOUtilis.*;


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

    public void start(){
        out.println("Car");


        var command = "";
        do {
            showCarMenu();
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
        var car = modelFactory.createCar();

        out.println("Enter the ID of the car to delete:");
        int id = Integer.parseInt(readLine(in));
        car.setId(id);

        carRepository.delete(car);
    }

    private void update() {
        try {
            var car = modelFactory.createCar();

            out.println("Enter the ID of the car to update:");
            int id = Integer.parseInt(readLine(in));
            car.setId(id);

            out.println("Model");
            car.setModel(readLine(in));

            out.println("Engine");
            car.setEngine(readLine(in));

            out.println("Chassis");
            car.setChassis(readLine(in));

            out.println("Team ID");
            car.setTeamId(Integer.parseInt(readLine(in)));

            carRepository.save(car);

            out.println("Update successful");
        } catch (NumberFormatException e) {
            out.println("Invalid team ID. Please enter a valid integer ID.");
        } catch (Exception e) {
            out.println("An error occurred while updating the team: " + e.getMessage());
        }
    }

    private void insert() {
        var car = modelFactory.createCar();

        out.println("TeamID");
        car.setTeamId(Integer.parseInt(readLine(in)));

        out.println("Model");
        car.setModel(readLine(in));

        out.println("Engine");
        car.setEngine(readLine(in));

        out.println("Chassis");
        car.setChassis(readLine(in));

        carRepository.save(car);

        out.println("Insert OK");
    }

    private void showCarMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
