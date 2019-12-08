package pl.bets365mj.fixture;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.api.ScoreDto;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.fixtureMisc.League;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SportCategory;
import pl.bets365mj.fixtureMisc.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Data
public class Fixture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<Bet> observers;

    @OneToOne
    private SportCategory category;

    @NotNull @ManyToOne (cascade = {CascadeType.MERGE})
    private League league;

    @ManyToOne (cascade = CascadeType.ALL)
    private Season season;

    @NotNull
    private int matchday;

    @Temporal(TemporalType.DATE)
    private java.util.Date Date;

    @NotNull
    private String matchStatus;

    @NotNull @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team homeTeam;

    @NotNull @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team awayTeam;

    private Integer FTHG; //final time home team goals
    private Integer FTAG; //final time away team goals
    private Integer HTHG; //home team half-time goal
    private Integer HTAG; //away team half-time goal
    private String HTR; //half time result (H-HomeTeam, A- AwayTeam, D-draw)
    private String FTR; //final result (H-HomeTeam, A- AwayTeam, D-draw)

    private BigDecimal homeWinOdd;
    private BigDecimal drawOdd;
    private BigDecimal awayWinOdd;
    @Column(name = "goal_less_2_5")
    private BigDecimal goalsLessOrEquals2odd;
    @Column(name = "goal_more_2_5")
    private BigDecimal goalsMoreThan2odd;
    private long apiId;

    public void setDate(java.util.Date date) {
        Date = date;
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


    public void update(MatchDto matchDto) {
        String apiStatus=matchDto.getStatus();
        this.setMatchStatus(apiStatus);
        ScoreDto score=matchDto.getScore();
        String winner=score.getWinner();
        Map<String, Integer> halfTimeResult = score.getHalfTime();
        int halfTimefHomeTeamScore = halfTimeResult.get("homeTeam");
        int halfTimeAwayTeamScore = halfTimeResult.get("awayTeam");
        this.setHTHG(halfTimefHomeTeamScore);
        this.setHTAG(halfTimeAwayTeamScore);

        Map<String, Integer> fullTimeResult = score.getFullTime();
        int fullTimefHomeTeamScore = fullTimeResult.get("homeTeam");
        int fullTimeAwayTeamScore = fullTimeResult.get("awayTeam");
        this.setFTHG(fullTimefHomeTeamScore);
        this.setFTAG(fullTimeAwayTeamScore);
        this.setFTR(String.valueOf(winner.charAt(0)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fixture fixture = (Fixture) o;
        return matchday == fixture.matchday &&
                apiId == fixture.apiId &&
                id.equals(fixture.id) &&
                Objects.equals(season, fixture.season) &&
                Objects.equals(Date, fixture.Date) &&
                Objects.equals(matchStatus, fixture.matchStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, season, matchday, Date, matchStatus, apiId);
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "id=" + id +
                ", matchday=" + matchday +
                ", Date=" + Date +
                ", matchStatus='" + matchStatus + '\'' +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", FTHG=" + FTHG +
                ", FTAG=" + FTAG +
                ", HTHG=" + HTHG +
                ", HTAG=" + HTAG +
                ", HTR='" + HTR + '\'' +
                ", FTR='" + FTR + '\'' +
                ", homeWinOdd=" + homeWinOdd +
                ", drawOdd=" + drawOdd +
                ", awayWinOdd=" + awayWinOdd +
                ", goalsLessOrEquals2odd=" + goalsLessOrEquals2odd +
                ", goalsMoreThan2odd=" + goalsMoreThan2odd +
                ", apiId=" + apiId +
                '}';
    }
}
