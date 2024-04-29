package cat.uvic.teknos.f1race.domain.jbdc.models;

import cat.uvic.teknos.f1race.models.*;
import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.models.Team;

public class JdbcModelFactory implements ModelFactory {


    @Override
    public Team createTeam() {
        return new cat.uvic.teknos.f1race.domain.jbdc.models.Team();
    }

    @Override
    public Car createCar() {
        return new cat.uvic.teknos.f1race.domain.jbdc.models.Car();
    }

    @Override
    public Driver createDriver() {
        return new cat.uvic.teknos.f1race.domain.jbdc.models.Driver();
    }

    @Override
    public Sponsor createSponsor() {
        return null;
    }

    @Override
    public RaceResult createRaceResult() {
        return null;
    }

    @Override
    public Sponsorship createSponsorShip() {
        return null;
    }
}
