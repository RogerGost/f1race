package cat.uvic.teknos.f1race.domain.jbdc.models;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


public class  Team implements cat.uvic.teknos.f1race.models.Team, Serializable{

    private int id;
    private String name;
    private String principalName;
    private String headquarters;

    private String sponsorName;
    private Set<Sponsor> sponsors;
    private Set<Driver> drivers;
    private Set<Car> cars;
    private List<Integer> sponsorIds;

    public Team() {}

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
    public String getSponsorName() {
        return sponsorName;
    }
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }



    @Override
    public Set<Driver> getDriver() {
        return drivers;
    }

    @Override
    public void setDriver(Driver driver) {

    }


    public Set<Car> getcar() {
        return cars;
    }


    public void setCar(Car car) {

    }

    @Override
    public Set<Car> getCars() {
        return cars;
    }

    @Override
    public void setCars(Set<? extends Car> cars) {

    }

    @Override
    public Set<Sponsor> getSponsor() {
        return sponsors ;
    }

    @Override
    public void setSponsor(Set<Sponsor> sponsor) {
        this.sponsors=sponsor;

    }
    public List<Integer> getSponsorIds() {
        return sponsorIds;
    }

    public void setSponsorIds(List<Integer> sponsorIds) {
        this.sponsorIds = sponsorIds;
    }
}
