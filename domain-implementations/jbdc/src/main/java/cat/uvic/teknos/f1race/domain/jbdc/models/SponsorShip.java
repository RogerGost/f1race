package cat.uvic.teknos.f1race.domain.jbdc.models;

import cat.uvic.teknos.f1race.models.SponsorShipId;

import java.time.LocalDate;

public class SponsorShip implements cat.uvic.teknos.f1race.models.SponsorShip{
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int teamId;


    public cat.uvic.teknos.f1race.domain.models.SponsorShipId getId() {
        return id;
    }

    @Override
    public void setId(SponsorShipId id) {

    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public int getTeamId() {
        return teamId;
    }


    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
