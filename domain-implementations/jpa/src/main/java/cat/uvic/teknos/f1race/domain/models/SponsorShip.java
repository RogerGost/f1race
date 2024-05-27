package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SponsorShip implements cat.uvic.teknos.f1race.models.SponsorShip {
    @EmbeddedId()
    private SponsorShipId id;
    @ManyToOne
    @MapsId
    private Team team;
    @MapsId
    @ManyToOne
    private Sponsor sponsor;
    @Column(name = "NAME")
    private String name;
    @Column(name = "START_DATE")
    private int startDate;
    @Column(name = "END_DATE")
    private int endDate;
    @Column(name = "TEAM_ID")
    private int teamId;



    @Override
    public  cat.uvic.teknos.f1race.models.SponsorShipId getId() {
        return id;
    }

    @Override
    public void setId(cat.uvic.teknos.f1race.models.SponsorShipId id) {
        this.id=(SponsorShipId) id;
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
    public int getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    @Override
    public int getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    @Override
    public int getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
