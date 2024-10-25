package cat.uvic.teknos.f1race.services.controllers;

import cat.uvic.teknos.f1race.models.ModelFactory;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.repositories.RepositoryFactory;
import cat.uvic.teknos.f1race.repositories.SponsorRepository;
import cat.uvic.teknos.f1race.repositories.TeamRepository;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.utils.Mappers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SponsorController implements Controller{

    private final ModelFactory modelFactory;
    private final RepositoryFactory repositoryFactory;

    public SponsorController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }
    @Override
    public String get(int id) {
        Sponsor sponsor = repositoryFactory.getSponsorRepository().get(id);

        if (sponsor == null) {
            throw new ResourceNotFoundExeption("Sponsor not found with id: " + id);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(sponsor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing sponsor: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() throws JsonProcessingException {
        var sponsors = repositoryFactory.getSponsorRepository().getAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(sponsors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing sponsors: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        SponsorRepository repository = repositoryFactory.getSponsorRepository();

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("Received JSON: " + json);
        try {
            Sponsor sponsor = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor.class);
            repository.save(sponsor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing sponsor: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        SponsorRepository repository = repositoryFactory.getSponsorRepository();
        Sponsor existingSponsor = repository.get(id);

        if (existingSponsor == null) {
            throw new ResourceNotFoundExeption("Cannot update. Sponsor not found with id: " + id);
        }

        ObjectMapper mapper = Mappers.get();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            Sponsor updatedSponsor = mapper.readValue(json, cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor.class);

            if (updatedSponsor.getSponsorType() != null) {
                existingSponsor.setSponsorType(updatedSponsor.getSponsorType());
            }

            if (updatedSponsor.getCountry() != null) {
                existingSponsor.setCountry(updatedSponsor.getCountry());
            }
            if (updatedSponsor.getName() != null) {
                existingSponsor.setName(updatedSponsor.getName());
            }
            if (updatedSponsor.getPhone() != 0) {
                existingSponsor.setPhone(updatedSponsor.getPhone());
            }

            repository.save(existingSponsor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing sponsor: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        SponsorRepository repository = repositoryFactory.getSponsorRepository();
        Sponsor sponsor = repository.get(id);

        if (sponsor == null) {
            throw new ResourceNotFoundExeption("Cannot delete. Sponsor not found with id: " + id);
        }

        repository.delete(sponsor);
    }
}
