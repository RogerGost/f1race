package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.models.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.util.HashSet;
import java.util.Set;
@Entity
public class Sponsor implements cat.uvic.teknos.f1race.models.Sponsor{

    @Id
    private int id;
    private String name;
    private String country;
    private int phone;
    private String sponsorType;
    @Transient
    private Set <Team> teams = new HashSet();




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
    public void setTeam(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Set<Team> getTeams() {
        return teams;
    }
}
