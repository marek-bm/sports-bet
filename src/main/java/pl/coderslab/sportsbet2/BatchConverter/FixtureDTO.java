package pl.coderslab.sportsbet2.BatchConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@NoArgsConstructor
public class FixtureDTO {

    @Getter
    @Setter
    private Integer Id;

    @Getter
    @Setter
    private int category_id;

    @Getter
    @Setter
    private int league_id;

    @Getter
    @Setter
    private int season_id;

    @Getter
    @Setter
    private int matchday;

    private java.util.Date Date;

    public java.util.Date getDate() {
        return Date;
    }

    @Getter
    @Setter
    private String matchStatus;

    @Getter
    @Setter
    private String homeTeam;

    @Getter
    @Setter
    private String awayTeam;

    @Getter
    @Setter
    //final time home team goals
    private Integer FTHG;

    @Getter
    @Setter
    //final time away team goals
    private Integer FTAG;

    @Getter
    @Setter
    //final result (H-HomeTeam, A- AwayTeam, D-draw)
    private String FTR;

    @Getter
    @Setter
    //home team half-time goal
    private Integer HTHG;

    @Getter
    @Setter
    //away team half-time goal
    private Integer HTAG;

    @Getter
    @Setter
    //half time result (H-HomeTeam, A- AwayTeam, D-draw)
    private String HTR;

    @Getter
    @Setter
    private BigDecimal homeWinOdd;

    @Getter
    @Setter
    private BigDecimal drawOdd;

    @Getter
    @Setter
    private BigDecimal awayWinOdd;

    @Getter
    @Setter
    private BigDecimal goal_more_2_5;

    @Getter
    @Setter
    private BigDecimal goal_less_2_5;

    public void setDate(String date) {
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        try {
            java.util.Date dateConverted = format.parse(date);
            this.Date = dateConverted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
