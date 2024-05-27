package cat.uvic.teknos.f1race.models;

import java.sql.Time;

public interface RaceResult {

    int getId();
    void setId(int id);

    int getRaceId();
    void setRaceId(int raceId);

    int getDriverId();
    void setDriverId(int driverId);

    int getPosition();
    void setPosition(int position);

    String getFastestLap();
    void setFastestLap(String fastestLap);

    int getPoints();
    void setPoints(int points);

    Driver getDriver();
    void setDriver(Driver driver);
}
