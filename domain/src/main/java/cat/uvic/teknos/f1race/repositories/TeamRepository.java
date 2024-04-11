package cat.uvic.teknos.f1race.repositories;

import cat.uvic.teknos.f1race.models.Team;

import java.util.Set;

public interface TeamRepository extends Repository<Integer, Team> {
    @Override
    void save(Team model);

    @Override
    void delete(Team model);


    @Override
    Team get(Integer id);

    @Override
    Set<Team> getAll();

}
