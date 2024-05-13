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
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Column(name = "TEAM_ID")
    private int teamId;



    @Override
    public int getId() {
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
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(LocalDate endDate) {
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
