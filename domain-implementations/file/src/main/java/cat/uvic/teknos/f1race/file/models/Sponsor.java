package cat.uvic.teknos.f1race.file.models;

import cat.uvic.teknos.f1race.models.Team;

import java.util.Set;

public class Sponsor implements cat.uvic.teknos.f1race.models.Sponsor{
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public void setCountry(String country) {

    }

    @Override
    public int getPhone() {
        return 0;
    }

    @Override
    public void setPhone(int phone) {

    }

    @Override
    public String getSponsorType() {
        return null;
    }

    @Override
    public void setSponsorType(String sponsorType) {

    }

    @Override
    public void setTeam(Set<Team> Team) {

    }

    @Override
    public Set<Team> getTeam() {
        return null;
    }
}
