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
    }

    private void update() {
    }

    private void insert() {
        var car = modelFactory.createCar();

        carRepository.save(null);
    }

    private void showCarMenu() {
        out.println("1. Insert");
        out.println("2. Update");
        out.println("3. Delete");
        out.println("4. GetAll");
    }
}
