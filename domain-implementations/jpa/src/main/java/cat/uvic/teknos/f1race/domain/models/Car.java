package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CAR")
public class Car implements cat.uvic.teknos.f1race.models.Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String model;

    @Column(name = "ENGINE")
    private String engine;

    @Column(name = "CHASSIS")
    private String chassis;

    @Column(name = "TEAM_ID", insertable = false, updatable = false)
    private int teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;

    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model=model;

    }

    @Override
    public String getEngine() {
        return engine;
    }

    @Override
    public void setEngine(String engine) {
        this.engine=engine;

    }

    @Override
    public String getChassis() {
        return chassis;
    }

    @Override
    public void setChassis(String chassis) {
        this.chassis=chassis;

    }

    @Override
    public int getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(int teamId) {
        this.teamId=teamId;

    }
}
