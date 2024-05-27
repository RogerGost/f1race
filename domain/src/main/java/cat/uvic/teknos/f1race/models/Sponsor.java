package cat.uvic.teknos.f1race.models;

import java.util.Set;

public interface Sponsor {

    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    String getCountry();
    void setCountry(String country);

    int getPhone();
    void setPhone(int phone);

    String getSponsorType();
    void setSponsorType(String sponsorType);

    int getSponsorshipId();


     void setSponsorshipId(int sponsorshipId);

    void setTeam(Set<Team> Team);

    Set<Team> getTeams();

}
