package cat.uvic.teknos.f1race.models;

import java.sql.Date;
import java.time.LocalDate;

public interface Sponsorship {

    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    LocalDate getStartDate();
    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();
    void setEndDate(LocalDate endDate);

    int getTeamId();
    void setTeamId(int teamId);
}
