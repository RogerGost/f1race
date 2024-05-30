package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CAR")
public class Car implements cat.uvic.teknos.f1race.models.Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "ENGINE")
    private String engine;

    @Column(name = "CHASSIS")
    private String chassis;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;

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
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String getEngine() {
        return engine;
    }

    @Override
    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public String getChassis() {
        return chassis;
    }

    @Override
    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    @Override
    public cat.uvic.teknos.f1race.models.Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(cat.uvic.teknos.f1race.models.Team team) {
        this.team = (Team) team;
    }
}
