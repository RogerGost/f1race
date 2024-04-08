package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CarRepository implements cat.uvic.teknos.f1race.repositories.CarRepository {

    private static Map<Integer, Car> car = new HashMap<>();

    public static void load() {

        try (var inputStream = new ObjectInputStream(new FileInputStream(""))) {
            car = (Map<Integer, Car>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void write() {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(""))) {
            outputStream.writeObject(car);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Car model) {
        if (model.getId() <= 0) {
            //get new id
            var newId = car.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            car.put(newId, model);

        } else {
            car.put(model.getId(), model);
        }

    }

    @Override
    public void delete(Car model) {

        car.remove(model.getId());

    }

    @Override
    public Car get(Integer id) {
        return null;
    }

    @Override
    public Set<Car> getAll() {
        return null;
    }

}
