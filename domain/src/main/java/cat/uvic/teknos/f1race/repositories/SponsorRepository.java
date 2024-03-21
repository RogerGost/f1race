package cat.uvic.teknos.f1race.repositories;

import cat.uvic.teknos.f1race.models.Sponsor;

import java.util.Set;

public interface SponsorRepository extends Repository <Integer, Sponsor>{
    @Override
    void save(Sponsor model);

    @Override
    void delete(Sponsor model);

    @Override
    Sponsor get(Integer id);

    @Override
    Set<Sponsor> getAll();
}
