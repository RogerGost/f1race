package cat.uvic.teknos.f1race.domain.jbdc.models;

public class Car implements cat.uvic.teknos.f1race.models.Car{
    private int id;
    private String model;
    private String engine;
    private String chassis;
    private int teamId;

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
