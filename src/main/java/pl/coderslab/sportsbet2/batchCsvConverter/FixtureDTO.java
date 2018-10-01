package pl.coderslab.sportsbet2.batchCsvConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class FixtureDTO {

    private Integer Id;

    private int category_id;

    private int league_id;

    private int season_id;

    private int matchday;

    private java.util.Date Date;

    private String matchStatus;

    private int homeTeam_id;

    private int awayTeam_id;

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


    //getters and setters
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
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

    public void setDate(String date) {
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        try {
            java.util.Date dateConverted=format.parse(date);
            this.Date=dateConverted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public int getHomeTeam_id() {
        return homeTeam_id;
    }

    public void setHomeTeam_id(int homeTeam_id) {
        this.homeTeam_id = homeTeam_id;
    }

    public int getAwayTeam_id() {
        return awayTeam_id;
    }

    public void setAwayTeam_id(int awayTeam_id) {
        this.awayTeam_id = awayTeam_id;
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
