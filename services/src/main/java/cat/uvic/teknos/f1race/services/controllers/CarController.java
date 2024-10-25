package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.repositories.CarRepository;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.utils.Mappers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarController implements Controller {
    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public CarController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        Car car = repositoryFactory.getCarRepository().get(id);

        if (car == null) {
            throw new ResourceNotFoundExeption("Car not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing car: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var cars = repositoryFactory.getCarRepository().getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(cars);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing cars: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        CarRepository repository = repositoryFactory.getCarRepository();

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            Car car = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.Car.class);
            repository.save(car);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing car: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        CarRepository repository = repositoryFactory.getCarRepository();
        Car existingCar = repository.get(id);

        if (existingCar == null) {
            throw new ResourceNotFoundExeption("Cannot update. Car not found with id: " + id);
        }

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            Car updatedCar = mapper.readValue(json,cat.uvic.teknos.f1race.domain.jbdc.models.Car.class);

            if (updatedCar.getChassis() != null) {
                existingCar.setChassis(updatedCar.getChassis());
            }
            if (updatedCar.getModel() != null) {
                existingCar.setModel(updatedCar.getModel());
            }
            if (updatedCar.getEngine() != null) {
                existingCar.setEngine(updatedCar.getEngine());
            }
            if (updatedCar.getTeam() != null) {
                existingCar.setTeam(updatedCar.getTeam());
            }

            repository.save(existingCar);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing car: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        CarRepository repository = repositoryFactory.getCarRepository();
        Car car = repository.get(id);

        if (car == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Car not found with id: " + id);
        }

        repository.delete(car);
    }
}
