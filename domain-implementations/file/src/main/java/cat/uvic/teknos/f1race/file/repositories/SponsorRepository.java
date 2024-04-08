package cat.uvic.teknos.f1race.file.repositories;

import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.models.Team;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SponsorRepository implements cat.uvic.teknos.f1race.repositories.SponsorRepository{

    private static Map<Integer, Sponsor> sponsor = new HashMap<>();

    public static void load(){

        try(var inputStream = new ObjectInputStream(new FileInputStream(""))) {
            sponsor = (Map<Integer, Sponsor>) inputStream.readObject();
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
            outputStream.writeObject(sponsor);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Sponsor model) {
        if(model.getId() <=0) {
            //get new id
            var newId = sponsor.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            sponsor.put(newId, model);

        }else{
            sponsor.put(model.getId(),model);
        }

    }

    @Override
    public void delete(Sponsor model) {

        sponsor.remove(model.getId());

    }

    @Override
    public Sponsor get(Integer id) {
        return null;
    }

    @Override
    public Set<Sponsor> getAll() {
        return null;
    }{
}
}
