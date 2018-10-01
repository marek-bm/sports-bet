package pl.coderslab.sportsbet2.model.sportEvent;

import javax.persistence.*;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private int points;

    private int goalsScoredAway;

    private int getGoalsLostAway;

    private int getGoalsScoredHome;

    private int getGoalsLostHome;

    private int wins;

    private int draws;

    private int lost;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Season season;

}
