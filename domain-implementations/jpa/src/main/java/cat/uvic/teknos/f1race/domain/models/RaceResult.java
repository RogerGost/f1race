package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Time;
@Entity
public class RaceResult implements cat.uvic.teknos.f1race.models.RaceResult{

    @Id
    private int id;
    private int raceId;
    private int driverId;
    private int position;
    private Time fastestlap;
    private int points;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;

    }

    @Override
    public int getRaceId() {
        return raceId;
    }

    @Override
    public void setRaceId(int raceId) {
        this.raceId=raceId;

    }

    @Override
    public int getDriverId() {
        return driverId;
    }

    @Override
    public void setDriverId(int driverId) {
        this.driverId=driverId;

    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position=position;

    }

    @Override
    public Time getFastestLap() {
        return fastestlap;
    }

    @Override
    public void setFastestLap(Time fastestLap) {
        this.fastestlap=fastestLap;

    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points=points;

    }
}
