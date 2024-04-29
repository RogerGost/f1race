package cat.uvic.teknos.f1race.repositories;

public interface RepositoryFactory {
    TeamRepository getTeamRepository();
    CarRepository getCarRepository();
    DriverRepository getDriverRepository();
    SponsorRepository getSponsorRepository();
    RaceResultRepository getRaceResultRepository();

}
