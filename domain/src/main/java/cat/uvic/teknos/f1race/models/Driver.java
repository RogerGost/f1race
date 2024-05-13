package cat.uvic.teknos.f1race.models;

import java.sql.Date;
import java.time.LocalDate;

public interface Driver {

    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    String getNationality();
    void setNationality(String nationality);

    int getDate();
    void setDate(int date);

    int getNumber();
    void setNumber(int number);

    int getTeamId();
    void setTeamId(int teamId);


}
