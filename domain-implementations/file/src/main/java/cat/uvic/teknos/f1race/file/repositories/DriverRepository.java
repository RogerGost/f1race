package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DriverRepository implements cat.uvic.teknos.f1race.repositories.DriverRepository {

    private static Map<Integer, Driver> driver = new HashMap<>();

    public static void load() {

        var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        try (var inputStream = new ObjectInputStream(new FileInputStream(currentDirectory+ "driver.ser"))) {
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
        var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(currentDirectory + "driver.ser"))) {
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
        write();
    }

    public static void update(){
        var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(currentDirectory + "driver.ser"))) {
            outputStream.writeObject(driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Driver model) {

        var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(currentDirectory + "driver.ser"))) {
            for (Iterator<Map.Entry<Integer, Driver>> iterator = driver.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, Driver> entry = iterator.next();
                if (entry.getValue().equals(model)) {
                    iterator.remove();
                    break;
                }
            }

            outputStream.writeObject(driver);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Driver get(Integer id) {
        return driver.get(id);
    }

    @Override
    public Set<Driver> getAll() {
        return Set.copyOf(driver.values());
    }
}

