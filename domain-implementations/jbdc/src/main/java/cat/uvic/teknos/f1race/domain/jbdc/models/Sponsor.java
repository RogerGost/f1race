package cat.uvic.teknos.f1race.domain.jbdc.models;

import cat.uvic.teknos.f1race.models.Team;

import java.util.Set;

public class Sponsor implements cat.uvic.teknos.f1race.models.Sponsor{


    private int id;
    private String name;
    private String country;
    private int phone;
    private String sponsorType;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name= name;

    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country=country;

    }

    @Override
    public int getPhone() {
        return phone;
    }

    @Override
    public void setPhone(int phone) {
        this.phone=phone;

    }

    @Override
    public String getSponsorType() {
        return sponsorType;
    }

    @Override
    public void setSponsorType(String sponsorType) {
        this.sponsorType=sponsorType;

    }

    @Override
    public void setTeam(Set<Team> Team) {

    }

    @Override
    public Set<Team> getTeam() {
        return null;
    }
}
