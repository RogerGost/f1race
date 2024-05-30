package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "RACE_RESULT")
public class RaceResult implements cat.uvic.teknos.f1race.models.RaceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "RACE_ID")
    private int raceId;

    @Column(name = "POSITION")
    private int position;

    @Column(name = "FASTEST_LAP")
    private String fastestLap;

    @Column(name = "POINTS")
    private int points;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID", nullable = false)
    private Driver driver;

    // Getters y Setters

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
        this.raceId = raceId;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getFastestLap() {
        return fastestLap;
    }

    @Override
    public void setFastestLap(String fastestLap) {
        this.fastestLap = fastestLap;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public void setDriver(cat.uvic.teknos.f1race.models.Driver driver) {
        this.driver = (Driver) driver;
    }
}
