package cat.uvic.teknos.f1race.repositories;

import cat.uvic.teknos.f1race.models.Driver;

import java.util.Set;

public interface DriverRepository  extends Repository<Integer, Driver>{

    @Override
    void save(Driver model);

    @Override
    void delete(Driver model);

    @Override
    Driver get(Integer id);

    @Override
    Set<Driver> getAll();
}
