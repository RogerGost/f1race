package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.DriverRepository;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
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
        Driver driver = repositoryFactory.getDriverRepository().get(id);  // Acceso por ID

        if (driver == null) {
            throw new ResourceNotFoundExeption("Driver not found with id: " + id);  // Manejo de error
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(driver);  // Serialización a JSON
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing driver: " + e.getMessage(), e);  // Error en serialización
        }
    }

    @Override
    public String get() {
        var drivers = repositoryFactory.getDriverRepository().getAll();  // Obtener todos los conductores

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(drivers);  // Serialización de la lista de conductores
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing drivers: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            Driver driver = mapper.readValue(json, Driver.class);
            repository.save(driver);
            System.out.println("Driver added: " + driver.getName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing driver: " + e.getMessage(), e);
        }
    }


    @Override
    public void put(int id, String json) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        Driver existingDriver = repository.get(id);  // Busca el conductor existente

        if (existingDriver == null) {
            throw new ResourceNotFoundExeption("Cannot update. Driver not found with id: " + id);  // Si no se encuentra
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Driver updatedDriver = mapper.readValue(json, Driver.class);  // Deserialización del JSON

            // Actualiza solo los campos que no son nulos
            if (updatedDriver.getName() != null) {
                existingDriver.setName(updatedDriver.getName());
            }
            if (updatedDriver.getNumber() != 0) {
                existingDriver.setNumber(updatedDriver.getNumber());
            }
            if (updatedDriver.getNationality() != null) {
                existingDriver.setNationality(updatedDriver.getNationality());
            }

            existingDriver.setDate(updatedDriver.getDate());


            repository.save(existingDriver);  // Guardar los cambios en el conductor
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing driver: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        DriverRepository repository = repositoryFactory.getDriverRepository();
        Driver driver = repository.get(id);  // Buscar el conductor por ID

        if (driver == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Driver not found with id: " + id);  // Error si no existe
        }

        repository.delete(driver);  // Eliminar el conductor
    }
}
