package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TeamController implements Controller<Integer, Team>{
    TeamRepository repository;
    @Override
    public String get(Integer integer) {
        //retrieve (get) team from db
        //serialize team in json format

        return "" ;//json
    }

    @Override
    public String get(){
        var teams = repository.getAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(teams);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void post(Team value) {
        repository.save(value);
    }

    @Override
    public void put(Integer key, Team value) {

    }

    @Override
    public void delete(Integer key) {

    }
}
