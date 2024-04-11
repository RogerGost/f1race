package cat.uvic.teknos.f1race.file.models;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsorship;

import java.util.Set;

public class  Team implements cat.uvic.teknos.f1race.models.Team{

    private int id;
    private String name;
    private String principalName;
    private String headquarters;

    private String sponsor;
    private Set<Sponsorship> sponsorships;
    private Set<Driver> drivers;
    private Set<Car> cars;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id= id;

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
    public void setSponsorships(Set<Sponsorship> sponsorships) {
        this.sponsorships = sponsorships;

    }

    @Override
    public Set<Sponsorship> getSponsorships() {
        return sponsorships;
    }

    @Override
    public Set<Driver> getDriver() {
        return drivers;
    }

    @Override
    public void setDriver(Driver driver) {

    }

    @Override
    public Set<Car> getcar() {
        return cars;
    }

    @Override
    public void setCar(Car car) {

    }

    @Override
    public Set<Car> getCar() {
        return cars;
    }
}
