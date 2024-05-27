package cat.uvic.teknos.f1race.models;

public interface Car {

    int getId();
    void setId(int id);

    String getModel();
    void setModel(String model);

    String getEngine();
    void setEngine(String engine);

    String getChassis();
    void setChassis(String chassis);

    Team getTeam();

    void setTeam(Team team);
}
