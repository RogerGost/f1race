package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.models.Team;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SPONSOR")
public class Sponsor implements cat.uvic.teknos.f1race.models.Sponsor, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PHONE")
    private int phone;

    @Column(name = "SPONSOR_TYPE")
    private String sponsorType;

    @ManyToMany(mappedBy = "sponsors", targetEntity = cat.uvic.teknos.f1race.domain.models.Team.class)
    private Set<Team> teams = new HashSet<>();


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
        this.name = name;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int getPhone() {
        return phone;
    }

    @Override
    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String getSponsorType() {
        return sponsorType;
    }

    @Override
    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }

    @Override
    public Set<Team> getTeams() {
        return teams;
    }

    @Override
    public void setTeam(Set<Team> teams) {
        this.teams = teams;
    }
}
