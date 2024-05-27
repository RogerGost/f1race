package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SponsorShipId implements Serializable, cat.uvic.teknos.f1race.models.SponsorShipId {

    private int sponsorId; // Corrected field name
    private int teamId;

    @Override
    public int getSponsorId() {
        return sponsorId;
    }

    @Override
    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
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
