package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.DriverRepository;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.utils.Mappers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DriverController implements Controller {
    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public DriverController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        Driver driver = repositoryFactory.getDriverRepository().get(id);

        if (driver == null) {
            throw new ResourceNotFoundExeption("Driver not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(driver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing driver: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var drivers = repositoryFactory.getDriverRepository().getAll();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(drivers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing drivers: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            Driver driver = mapper.readValue(json,cat.uvic.teknos.f1race.domain.jbdc.models.Driver.class);
            repository.save(driver);
            System.out.println("Driver added: " + driver.getName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing driver: " + e.getMessage(), e);
        }
    }


    @Override
    public void put(int id, String json) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        Driver existingDriver = repository.get(id);

        if (existingDriver == null) {
            throw new ResourceNotFoundExeption("Cannot update. Driver not found with id: " + id);
        }

        ObjectMapper mapper = Mappers.get();
        try {
            Driver updatedDriver = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.Driver.class);

            if (updatedDriver.getName() != null) {
                existingDriver.setName(updatedDriver.getName());
            }
            if (updatedDriver.getNumber() != 0) {
                existingDriver.setNumber(updatedDriver.getNumber());
            }
            if (updatedDriver.getNationality() != null) {
                existingDriver.setNationality(updatedDriver.getNationality());
            }
            if (updatedDriver.getDate() != existingDriver.getDate()) {
                existingDriver.setDate(updatedDriver.getDate());
            }

            if (updatedDriver.getTeam() != null && updatedDriver.getTeam().getId() != 0) {
                existingDriver.setTeam(updatedDriver.getTeam());
            }


            existingDriver.setDate(updatedDriver.getDate());


            repository.save(existingDriver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing driver: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        Driver driver = repository.get(id);

        if (driver == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Driver not found with id: " + id);
        }

        repository.delete(driver);
    }
}
