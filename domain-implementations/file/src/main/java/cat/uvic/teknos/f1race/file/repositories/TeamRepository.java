package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TeamRepository implements cat.uvic.teknos.f1race.repositories.TeamRepository{

    private static Map<Integer, Team>team = new HashMap<>();

    private String path;

    public TeamRepository (String path){
        this.path=path;
    }

    void load(){
        //var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        if (Files.exists(Path.of(path))) {
            try (var inputStream = new ObjectInputStream(new FileInputStream(path))) {
                team = (Map<Integer, Team>) inputStream.readObject();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    void write(){
        //var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(team);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Team model) {
        if (model.getId() <= 0){
            //get new id
            var newId=team.keySet().stream().mapToInt(k -> k).max().orElse(0)+1;
            team.put(newId, model);
        }else{
            team.put(model.getId(), model);
        }
        write();

    }
    public void update(){
        //var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Team model) {

        //var currentDirectory = System.getProperty("user.dir") + "/src/main/resources/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(path))) {

            for (Iterator<Map.Entry<Integer, Team>> iterator = team.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, Team> entry = iterator.next();
                if (entry.getValue().equals(model)) {
                    iterator.remove();
                    break;
                }
            }

            outputStream.writeObject(team);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Team get(Integer id) {
        load();
        return team.get(id);
    }

    @Override
    public Set<Team> getAll() {
        return Set.copyOf(team.values());
    }
}
