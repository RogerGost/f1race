package cat.uvic.teknos.f1race.models;

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

    String getSponsor();

    void setSponsorships(Set<Sponsorship> sponsorships);

    Set<Sponsorship> getSponsorships();

    Set<Driver> driver();

    Set<Car> car();

}

