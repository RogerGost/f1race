package cat.uvic.teknos.f1race.clients.console.dto;

import cat.uvic.teknos.f1race.models.Team;

import java.io.Serializable;


public class DriverDto implements cat.uvic.teknos.f1race.models.Driver, Serializable{

    private int id;
    private String name;
    private String nationality;
    private int date;
    private int number;
    private Team team;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;

    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setNationality(String nationality) {
        this.nationality=nationality;

    }

    @Override
    public int getDate() {
        return date;
    }

    @Override
    public void setDate(int date) {
        this.date=date;

    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number=number;

    }


    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(Team team) {
        this.team=team;

    }
}
