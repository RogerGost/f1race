package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TeamController implements Controller {
    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public TeamController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        Team team = repositoryFactory.getTeamRepository().get(id);

        if (team == null) {
            throw new ResourceNotFoundExeption("Team not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(team);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing team: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var teams = repositoryFactory.getTeamRepository().getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(teams);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing teams: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        TeamRepository repository = repositoryFactory.getTeamRepository();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("Received JSON: " + json);
        try {
            Team team = mapper.readValue(json, Team.class);
            repository.save(team);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing team: " + e.getMessage(), e);
        }
    }


    @Override
    public void put(int id, String json) {
        TeamRepository repository = repositoryFactory.getTeamRepository();
        Team existingTeam = repository.get(id);

        if (existingTeam == null) {
            throw new ResourceNotFoundExeption("Cannot update. Team not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            Team updatedTeam = mapper.readValue(json, Team.class);

            if (updatedTeam.getTeamName() != null) {
                existingTeam.setTeamName(updatedTeam.getTeamName());
            }
            if (updatedTeam.getHeadquarters() != null) {
                existingTeam.setHeadquarters(updatedTeam.getHeadquarters());
            }
            if (updatedTeam.getSponsorName() != null) {
                existingTeam.setSponsorName(updatedTeam.getSponsorName());
            }
            if (updatedTeam.getPrincipalName() != null) {
                existingTeam.setPrincipalName(updatedTeam.getPrincipalName());
            }

            repository.save(existingTeam);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing team: " + e.getMessage(), e);
        }
    }


    @Override
    public void delete(int id) {
        TeamRepository repository = repositoryFactory.getTeamRepository();
        Team team = repository.get(id);

        if (team == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Team not found with id: " + id);
        }

        repository.delete(team);
    }
}