package cat.uvic.teknos.f1race.models;

public interface ModelFactory {
    Team createTeam();
    Car createCar();
    Driver createDriver();
    Sponsor createSponsor();
    RaceResult createRaceResult();

}
