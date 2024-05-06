package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.RaceResult;
import cat.uvic.teknos.f1race.models.Sponsor;
import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.models.*;

public class JpaModelFactory implements ModelFactory {


    @Override
    public Team createTeam() {
        return new cat.uvic.teknos.f1race.domain.models.Team();
    }

    @Override
    public Car createCar() {
        return new cat.uvic.teknos.f1race.domain.models.Car();
    }

    @Override
    public Driver createDriver() {
        return new cat.uvic.teknos.f1race.domain.models.Driver();
    }

    @Override
    public Sponsor createSponsor() {
        return new cat.uvic.teknos.f1race.domain.models.Sponsor();
    }

    @Override
    public RaceResult createRaceResult() {
        return new cat.uvic.teknos.f1race.domain.models.RaceResult();
    }

    @Override
    public Sponsorship createSponsorShip() {
        return new cat.uvic.teknos.f1race.domain.models.SponsorShip();
    }
}
