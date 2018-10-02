package pl.coderslab.sportsbet2.model;

import org.hibernate.validator.constraints.NotEmpty;
import pl.coderslab.sportsbet2.model.sportEvent.League;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.model.sportEvent.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty
    @OneToOne
    private SportCategory category;

    @NotEmpty
    @ManyToOne
    private League league;

    @ManyToOne
    private Season season;

    @NotEmpty
    private int matchday;

    @Temporal(TemporalType.DATE)
    private java.util.Date Date;

    @NotNull
    private String matchStatus;

    @NotEmpty
    @ManyToOne (cascade = CascadeType.ALL)
    private Team homeTeam;

    @NotEmpty
    @ManyToOne (cascade = CascadeType.ALL)
    private Team awayTeam;

    //final time home team goals
    private Integer FTHG;

    //final time away team goals
    private Integer FTAG;

    //final result (H-HomeTeam, A- AwayTeam, D-draw)
    private String FTR;

    //home team half-time goal
    private Integer HTHG;

    //away team half-time goal
    private Integer HTAG;

    //half time result (H-HomeTeam, A- AwayTeam, D-draw)
    private char HTR;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public SportCategory getCategory() {
        return category;
    }

    public void setCategory(SportCategory category) {
        this.category = category;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getFTHG() {
        return FTHG;
    }

    public void setFTHG(Integer FTHG) {
        this.FTHG = FTHG;
    }

    public Integer getFTAG() {
        return FTAG;
    }

    public void setFTAG(Integer FTAG) {
        this.FTAG = FTAG;
    }

    public String getFTR() {
        return FTR;
    }

    public void setFTR(String FTR) {
        this.FTR = FTR;
    }

    public Integer getHTHG() {
        return HTHG;
    }

    public void setHTHG(Integer HTHG) {
        this.HTHG = HTHG;
    }

    public Integer getHTAG() {
        return HTAG;
    }

    public void setHTAG(Integer HTAG) {
        this.HTAG = HTAG;
    }

    public char getHTR() {
        return HTR;
    }

    public void setHTR(char HTR) {
        this.HTR = HTR;
    }
}
