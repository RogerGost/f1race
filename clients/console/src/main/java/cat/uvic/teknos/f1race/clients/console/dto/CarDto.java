package cat.uvic.teknos.f1race.clients.console.dto;

import cat.uvic.teknos.f1race.models.Team;

public class CarDto implements cat.uvic.teknos.f1race.models.Car {
    private int id;
    private String model;
    private String engine;
    private String chassis;

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
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(Team team) {
        this.team=team;

    }


}
