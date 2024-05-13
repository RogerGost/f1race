package cat.uvic.teknos.f1race.models;

import java.time.LocalDate;

public interface SponsorShip {

    SponsorShipId getId();
    void setId(SponsorShipId id);

    String getName();
    void setName(String name);

    LocalDate getStartDate();
    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();
    void setEndDate(LocalDate endDate);

    int getTeamId();
    void setTeamId(int teamId);
}
