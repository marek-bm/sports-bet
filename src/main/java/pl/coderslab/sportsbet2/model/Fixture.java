package pl.coderslab.sportsbet2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.sportsbet2.model.sportEvent.League;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.model.sportEvent.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Integer id;

    @NotNull
    @Setter @Getter
    @OneToOne
    private SportCategory category;

    @NotNull
    @Setter @Getter
    @ManyToOne
    private League league;

    @ManyToOne (cascade = CascadeType.ALL)
    @Setter @Getter
    private Season season;

    @NotNull
    @Setter @Getter
    private int matchday;

    @Temporal(TemporalType.DATE)
    @Getter
    private java.util.Date Date;

    @NotNull
    @Setter @Getter
    private String matchStatus;

    @NotNull
    @ManyToOne (cascade = CascadeType.ALL)
    @Setter @Getter
    private Team homeTeam;

    @NotNull
    @ManyToOne (cascade = CascadeType.ALL)
    @Setter @Getter
    private Team awayTeam;

    @Setter @Getter
    //final time home team goals
    private Integer FTHG;

    @Setter @Getter
    //final time away team goals
    private Integer FTAG;

    //final result (H-HomeTeam, A- AwayTeam, D-draw)
    @Setter @Getter
    private String FTR;

    //home team half-time goal
    @Setter @Getter
    private Integer HTHG;

    //away team half-time goal
    @Setter @Getter
    private Integer HTAG;

    //half time result (H-HomeTeam, A- AwayTeam, D-draw)
    @Setter @Getter
    private String HTR;

    //odds
    @Setter @Getter
    private BigDecimal homeWinOdd;
    @Setter @Getter
    private BigDecimal drawOdd;
    @Setter @Getter
    private BigDecimal awayWinOdd;
    @Setter @Getter
    private BigDecimal goal_more_2_5;
    @Setter @Getter
    private BigDecimal goal_less_2_5;


/*
Getters, setters and constructor replaced with lombok annotation
 */

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
