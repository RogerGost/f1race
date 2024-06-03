package cat.uvic.teknos.f1race.domain.models;

import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsor;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TEAM")
public class Team implements cat.uvic.teknos.f1race.models.Team, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRINCIPAL_NAME")
    private String principalName;

    @Column(name = "HEADQUARTERS")
    private String headquarters;

    @Column(name = "SPONSOR_NAME")
    private String sponsorName;

    @ManyToMany(targetEntity = cat.uvic.teknos.f1race.domain.models.Sponsor.class)
    @JoinTable(
            name = "TEAM_SPONSOR",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "SPONSOR_ID")
    )
    private Set<Sponsor> sponsors = new HashSet<>();

    @OneToMany(mappedBy = "team", targetEntity = cat.uvic.teknos.f1race.domain.models.Driver.class)
    private Set<Driver> drivers = new HashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTeamName() {
        return name;
    }

    @Override
    public void setTeamName(String name) {
        this.name = name;
    }

    @Override
    public String getPrincipalName() {
        return principalName;
    }

    @Override
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    @Override
    public String getHeadquarters() {
        return headquarters;
    }

    @Override
    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    @Override
    public String getSponsorName() {
        return sponsorName;
    }

    @Override
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    @Override
    public Set<Driver> getDriver() {
        return drivers;
    }

    @Override
    public void setDriver(Driver driver) {
        this.drivers.add(driver);
    }

    @Override
    public Set<Car> getCars() {
        return cars;
    }

    @Override
    public void setCars(Set<? extends cat.uvic.teknos.f1race.models.Car> cars) {
        this.cars = (Set<Car>) cars;
    }

    @Override
    public Set<Sponsor> getSponsor() {
        return sponsors;
    }

    @Override
    public void setSponsor(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    @Override
    public List<Integer> getSponsorIds() {
        return null;
    }

    @Override
    public void setSponsorIds(List<Integer> sponsorIds) {

    }
}
