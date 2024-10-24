package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TeamController implements Controller {
    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public TeamController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        Team team = repositoryFactory.getTeamRepository().get(id);  // Obtén el equipo por ID

        if (team == null) {
            throw new ResourceNotFoundExeption("Team not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Omitir campos nulos
        try {
            return mapper.writeValueAsString(team);  // Serializa el equipo a JSON
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing team: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var teams = repositoryFactory.getTeamRepository().getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Omitir campos nulos
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
        try {
            Team team = mapper.readValue(json, Team.class);
            repository.save(team);
            System.out.println("Team added: " + team.getTeamName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing team: " + e.getMessage(), e);
        }
    }


    public void post(HttpExchange exchange) throws IOException {
        // Leer el cuerpo de la solicitud directamente
        try (InputStream inputStream = exchange.getRequestBody();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String json = reader.lines().collect(Collectors.joining("\n"));

            // Imprimir el JSON recibido en la consola
            System.out.println("Received JSON: " + json);

            // Deserializar el JSON y guardar el equipo en el repositorio
            TeamRepository repository = repositoryFactory.getTeamRepository();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Omitir campos nulos

            try {
                Team team = mapper.readValue(json, Team.class);  // Deserializar JSON a objeto Team
                repository.save(team);  // Guardar el equipo en el repositorio

                // Devolver respuesta de éxito
                String response = "Team saved successfully!";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
            } catch (JsonProcessingException e) {
                // Manejo de error si falla la deserialización
                String errorResponse = "Error deserializing team: " + e.getMessage();
                exchange.sendResponseHeaders(400, errorResponse.length());
                exchange.getResponseBody().write(errorResponse.getBytes());
            }
        } catch (IOException e) {
            // Manejo de error si falla la lectura del cuerpo de la solicitud
            String errorResponse = "Error reading request body: " + e.getMessage();
            exchange.sendResponseHeaders(500, errorResponse.length());
            exchange.getResponseBody().write(errorResponse.getBytes());
        } finally {
            exchange.getResponseBody().close();  // Cerrar el cuerpo de la respuesta
        }
    }




    @Override
    public void put(int id, String json) {
        TeamRepository repository = repositoryFactory.getTeamRepository();
        Team existingTeam = repository.get(id);  // Busca el equipo existente

        if (existingTeam == null) {
            throw new ResourceNotFoundExeption("Cannot update. Team not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Omitir campos nulos
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
