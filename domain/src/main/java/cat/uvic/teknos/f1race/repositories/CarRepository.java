package cat.uvic.teknos.f1race.repositories;

import cat.uvic.teknos.f1race.models.Car;

import java.util.Set;

public interface CarRepository extends Repository<Integer, Car>{

    @Override
    void save(Car model);

    @Override
    void delete(Car model);

    @Override
    Car get(Integer id);

    @Override
    Set<Car> getAll();
}
