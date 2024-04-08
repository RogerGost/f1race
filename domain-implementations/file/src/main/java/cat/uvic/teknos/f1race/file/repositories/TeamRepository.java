package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamRepository implements cat.uvic.teknos.f1race.repositories.TeamRepository{

    private static Map<Integer, Team>team = new HashMap<>();

    public static void load(){

        try(var inputStream = new ObjectInputStream(new FileInputStream(""))) {
            team = (Map<Integer, Team>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    public static void write(){
        try(var outputStream = new ObjectOutputStream(new FileOutputStream(""))) {
            outputStream.writeObject(team);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Team model) {
        if(model.getId() <=0) {
            //get new id
            var newId = team.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            team.put(newId, model);

        }else{
            team.put(model.getId(),model);
        }

    }

    @Override
    public void delete(Team model) {

        team.remove(model.getId());

    }

    @Override
    public Team get(Integer id) {
        return null;
    }

    @Override
    public Set<Team> getAll() {
        return null;
    }
}
