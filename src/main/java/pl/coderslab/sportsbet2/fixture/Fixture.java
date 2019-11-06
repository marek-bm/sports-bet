package pl.coderslab.sportsbet2.fixture;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.sportsbet2.fixtureMisc.League;
import pl.coderslab.sportsbet2.fixtureMisc.Season;
import pl.coderslab.sportsbet2.fixtureMisc.SportCategory;
import pl.coderslab.sportsbet2.fixtureMisc.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@NoArgsConstructor
@Entity
@Data
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    private SportCategory category;

    @NotNull
    @ManyToOne
    private League league;

    @ManyToOne (cascade = CascadeType.ALL)
    private Season season;

    @NotNull
    private int matchday;

    @Temporal(TemporalType.DATE)
    private java.util.Date Date;

    @NotNull
    private String matchStatus;

    @NotNull
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team homeTeam;

    @NotNull
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    private String HTR;

    //odds
    private BigDecimal homeWinOdd;
    private BigDecimal drawOdd;
    private BigDecimal awayWinOdd;
    private BigDecimal goal_more_2_5;
    private BigDecimal goal_less_2_5;

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
}
