package cat.uvic.teknos.f1race.file.models;

import cat.uvic.teknos.f1race.models.Car;
import cat.uvic.teknos.f1race.models.Driver;
import cat.uvic.teknos.f1race.models.Sponsorship;

import java.util.Set;

public class  Team implements cat.uvic.teknos.f1race.models.Team{

    private int id;
    private String name;
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
    public void setTeamName(String teamName) {
        this.name= name;

    }

    @Override
    public String getPrincipalName() {
        return null;
    }

    @Override
    public void setPrincipalName(String principalName) {

    }

    @Override
    public String getHeadquarters() {
        return null;
    }

    @Override
    public void setHeadquarters(String headquarters) {

    }

    @Override
    public String getSponsor() {
        return null;
    }

    @Override
    public void setSponsorships(Set<Sponsorship> sponsorships) {

    }

    @Override
    public Set<Sponsorship> getSponsorships() {
        return null;
    }

    @Override
    public Set<Driver> driver() {
        return null;
    }

    @Override
    public Set<Car> car() {
        return null;
    }
}
