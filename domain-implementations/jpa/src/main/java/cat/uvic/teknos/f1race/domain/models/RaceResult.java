package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;

import java.sql.Time;
@Entity
@Table(name = "RACE_RESULT")
public class RaceResult implements cat.uvic.teknos.f1race.models.RaceResult{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "RACE_ID")
    private int raceId;
    @Column(name = "DRIVER_ID", insertable = false, updatable = false)
    private int driverId;
    @Column(name = "POSITION")
    private int position;
    @Column(name = "FASTEST_LAP")
    private String fastestlap;
    @Column(name = "POINTS")
    private int points;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
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
    public String getFastestLap() {
        return fastestlap;
    }

    @Override
    public void setFastestLap(String fastestLap) {
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
