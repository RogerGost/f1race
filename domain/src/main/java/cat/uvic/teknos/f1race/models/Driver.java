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

    int getDate(); // Cambiado de int a LocalDate
    void setDate(int date); // Cambiado de int a LocalDate

    int getNumber();
    void setNumber(int number);

    int getTeamId(); // Este método puede ser removido si solo utilizas la relación ManyToOne
    void setTeamId(int teamId); // Este método puede ser removido si solo utilizas la relación ManyToOne

    Team getTeam();

    void setTeam(Team team);
}