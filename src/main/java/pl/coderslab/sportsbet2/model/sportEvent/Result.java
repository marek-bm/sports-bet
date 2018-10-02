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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsScoredAway() {
        return goalsScoredAway;
    }

    public void setGoalsScoredAway(int goalsScoredAway) {
        this.goalsScoredAway = goalsScoredAway;
    }

    public int getGetGoalsLostAway() {
        return getGoalsLostAway;
    }

    public void setGetGoalsLostAway(int getGoalsLostAway) {
        this.getGoalsLostAway = getGoalsLostAway;
    }

    public int getGetGoalsScoredHome() {
        return getGoalsScoredHome;
    }

    public void setGetGoalsScoredHome(int getGoalsScoredHome) {
        this.getGoalsScoredHome = getGoalsScoredHome;
    }

    public int getGetGoalsLostHome() {
        return getGoalsLostHome;
    }

    public void setGetGoalsLostHome(int getGoalsLostHome) {
        this.getGoalsLostHome = getGoalsLostHome;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
