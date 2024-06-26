package cat.uvic.teknos.f1race.models;

import java.util.List;
import java.util.Set;

public interface Team {

    int getId();
    void setId(int Id);

    String getTeamName();
    void setTeamName(String teamName);

    String getPrincipalName();

    void setPrincipalName(String principalName);

    String getHeadquarters();

    void setHeadquarters(String headquarters);

    String getSponsorName();
    void setSponsorName(String sponsor);


    Set<Driver> getDriver();

    void setDriver (Driver driver);

    Set<? extends Car> getCars();

    void setCars(Set<? extends Car> cars);

    Set<Sponsor> getSponsor();

    void setSponsor(Set<Sponsor> sponsor);

    public List<Integer> getSponsorIds();
    public void setSponsorIds(List<Integer> sponsorIds);
}



