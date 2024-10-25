package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.RaceResultRepository;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.utils.Mappers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RaceResultController implements Controller{

    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public RaceResultController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }
    @Override
    public String get(int id) {
        RaceResult raceResult = repositoryFactory.getRaceResultRepository().get(id);

        if (raceResult == null) {
            throw new ResourceNotFoundExeption("RaceResult not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(raceResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing raceResult: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() throws JsonProcessingException {
        var racesresults = repositoryFactory.getRaceResultRepository().getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(racesresults);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing race result: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        RaceResultRepository repository = repositoryFactory.getRaceResultRepository();

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("Received JSON: " + json);
        try {
            RaceResult raceResult = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult.class);
            repository.save(raceResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing race result: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        RaceResultRepository repository = repositoryFactory.getRaceResultRepository();
        RaceResult existingRaceResult = repository.get(id);

        if (existingRaceResult == null) {
            throw new ResourceNotFoundExeption("Cannot update. RaceResult not found with id: " + id);
        }

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            RaceResult updatedRaceResult = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult.class);

            if (updatedRaceResult.getFastestLap() != null) {
                existingRaceResult.setFastestLap(updatedRaceResult.getFastestLap());
            }
            if (updatedRaceResult.getPoints() != 0) {
                existingRaceResult.setPoints(updatedRaceResult.getPoints());
            }
            if (updatedRaceResult.getPosition() != 0) {
                existingRaceResult.setPosition(updatedRaceResult.getPosition());
            }
            if (updatedRaceResult.getRaceId() != 0) {
                existingRaceResult.setRaceId(updatedRaceResult.getRaceId());

            }if (updatedRaceResult.getDriver() != null) {
                existingRaceResult.setDriver(updatedRaceResult.getDriver());
            }

            repository.save(existingRaceResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing race result: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        RaceResultRepository repository = repositoryFactory.getRaceResultRepository();
        RaceResult raceResult = repository.get(id);

        if (raceResult == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Race not found with id: " + id);
        }

        repository.delete(raceResult);
    }
}
