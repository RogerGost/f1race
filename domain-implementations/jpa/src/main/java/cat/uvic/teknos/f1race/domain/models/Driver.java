package cat.uvic.teknos.f1race.domain.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "DRIVER")
public class Driver implements cat.uvic.teknos.f1race.models.Driver, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "DATE_OF_BIRTH")
    private int date;

    @Column(name = "NUMBER")
    private int number;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RaceResult> raceResults;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public int getDate() {
        return date;
    }

    @Override
    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public cat.uvic.teknos.f1race.models.Team getTeam() {
        return team;
    }

    @Override
    public void setTeam(cat.uvic.teknos.f1race.models.Team team) {
        this.team = (Team) team;
    }

    public Set<RaceResult> getRaceResults() {
        return raceResults;
    }

    public void setRaceResults(Set<RaceResult> raceResults) {
        this.raceResults = raceResults;
    }
}
