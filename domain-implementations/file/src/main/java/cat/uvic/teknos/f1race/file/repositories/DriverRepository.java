package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DriverRepository implements cat.uvic.teknos.f1race.repositories.DriverRepository {

    private static Map<Integer, Driver> driver = new HashMap<>();

    public static void load() {

        try (var inputStream = new ObjectInputStream(new FileInputStream(""))) {
            driver = (Map<Integer, Driver>) inputStream.readObject();
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
            outputStream.writeObject(driver);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Driver model) {
        if (model.getId() <= 0) {
            //get new id
            var newId = driver.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            driver.put(newId, model);

        } else {
            driver.put(model.getId(), model);
        }

    }

    @Override
    public void delete(Driver model) {

        driver.remove(model.getId());

    }

    @Override
    public Driver get(Integer id) {
        return null;
    }

    @Override
    public Set<Driver> getAll() {
        return null;
    }
}

