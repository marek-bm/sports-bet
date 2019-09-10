package pl.coderslab.sportsbet2.model.sportEvent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class SeasonResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private Season season;

    @Getter
    @Setter
    private int playedGames = 0;

    @Getter
    @Setter
    private int points = 0;

    @Getter
    @Setter
    private int goalsScoredAway = 0;

    @Getter
    @Setter
    private int goalsLostAway = 0;

    @Getter
    @Setter
    private int goalsScoredHome = 0;

    @Getter
    @Setter
    private int goalsLostHome = 0;

    @Getter
    @Setter
    private int wins = 0;

    @Getter
    @Setter
    private int draws = 0;

    @Getter
    @Setter
    private int lost = 0;

    @Getter
    @Setter
    @ManyToOne
    private Team team;

//    @Setter @Getter
//    Fixture fixture;
}
