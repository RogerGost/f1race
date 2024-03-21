package cat.uvic.teknos.f1race.repositories;

import cat.uvic.teknos.f1race.models.RaceResult;

import java.util.Set;

public interface RaceResultRepository extends Repository<Integer, RaceResult>{
    @Override
    void save(RaceResult model);

    @Override
    void delete(RaceResult model);

    @Override
    RaceResult get(Integer id);

    @Override
    Set<RaceResult> getAll();
}
