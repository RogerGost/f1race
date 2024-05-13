package cat.uvic.teknos.f1race.domain.models;
import cat.uvic.teknos.f1race.models.Driver;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TEAM")
public class  Team implements cat.uvic.teknos.f1race.models.Team, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "PRINCIPAL_NAME")
    private String principalName;
    @Column(name = "HEADQUARTERS")
    private String headquarters;

    @Column(name = "SPONSOR")
    private String sponsor;

    @OneToMany(mappedBy = "team")
    private Set<SponsorShip> sponsorships;







    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private Set<Driver> drivers;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TEAM_ID")
    private Set<Car> cars = new HashSet<>();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id= id;

    }
    public Team() {
        this.sponsorships = new HashSet<>();
        this.drivers = new HashSet<>();
        this.cars = new HashSet<>();
    }

    @Override
    public String getTeamName() {
        return name;
    }

    @Override
    public void setTeamName(String name) {
        this.name= name;

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
    public String getSponsor() {
        return sponsor;
    }
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public void setSponsorships(Set<cat.uvic.teknos.f1race.models.SponsorShip> sponsorships) {
        this.sponsorships.clear();
        sponsorships.forEach(sponsorShip -> {this.sponsorships.add((SponsorShip)sponsorShip);});

    }

    @Override
    public Set<cat.uvic.teknos.f1race.models.SponsorShip> getSponsorships() {
        return new HashSet<>(sponsorships);
    }

    @Override
    public Set<Driver> getDriver() {
        return drivers;
    }

    @Override
    public void setDriver(Driver driver) {

    }

    @Override
    public Set<Car> getCars() {
        return cars;
    }

    @Override
    public void setCars(Set<? extends cat.uvic.teknos.f1race.models.Car> cars) {
        //falta codi
    }
}
