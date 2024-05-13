package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SponsorShipId implements Serializable, cat.uvic.teknos.f1race.models.SponsorShipId {

    public int getSponorId() {
        return sponorId;
    }

    public void setSponorId(int sponorId) {
        this.sponorId = sponorId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    private int sponorId;
    private int teamId;
}
