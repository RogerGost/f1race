package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.models.Sponsor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RaceResultRepository implements cat.uvic.teknos.f1race.repositories.RaceResultRepository{

    private static Map<Integer, RaceResult> raceResult= new HashMap<>();

    public static void load(){

        try(var inputStream = new ObjectInputStream(new FileInputStream(""))) {
            raceResult = (Map<Integer, RaceResult>) inputStream.readObject();
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
            outputStream.writeObject(raceResult);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(RaceResult model) {
        if(model.getId() <=0) {
            //get new id
            var newId = raceResult.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            raceResult.put(newId, model);

        }else{
            raceResult.put(model.getId(),model);
        }

    }

    @Override
    public void delete(RaceResult model) {

        raceResult.remove(model.getId());

    }

    @Override
    public RaceResult get(Integer id) {
        return null;
    }

    @Override
    public Set<RaceResult> getAll() {
        return null;
    }{
    }
}

