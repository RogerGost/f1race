package cat.uvic.teknos.f1race.file.models;

import java.time.LocalDate;
import java.io.Serializable;


public class Driver implements cat.uvic.teknos.f1race.models.Driver, Serializable{

    private int id;
    private String name;
    private String nationality;
    private LocalDate date;
    private int number;
    private int teamId;
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
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
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
    public int getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(int teamId) {
        this.teamId=teamId;

    }
}
