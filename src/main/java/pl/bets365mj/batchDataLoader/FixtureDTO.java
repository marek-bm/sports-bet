package pl.bets365mj.batchDataLoader;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
class FixtureDTO {
    private Integer Id;
    private int category_id;
    private int league_id;
    private int season_id;
    private int matchday;
    private java.util.Date Date;
    private String matchStatus;
    private String homeTeam;
    private String awayTeam;
    private Integer FTHG; //final time home team goals
    private Integer FTAG; //final time away team goals
    private String FTR; //final result (H-HomeTeam, A- AwayTeam, D-draw)
    private Integer HTHG; //home team half-time goal
    private Integer HTAG; //away team half-time goal
    private String HTR; //half time result (H-HomeTeam, A- AwayTeam, D-draw)
    private BigDecimal homeWinOdd;
    private BigDecimal drawOdd;
    private BigDecimal awayWinOdd;
    private BigDecimal goal_more_2_5;
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

    public java.util.Date getDate() {
        return Date;
    }
}
