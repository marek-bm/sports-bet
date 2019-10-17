package pl.coderslab.sportsbet2.betting.bet;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.sportsbet2.betting.coupon.Coupon;
import pl.coderslab.sportsbet2.fixture.Fixture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @Getter @Setter
    private Fixture event;

    @Getter @Setter
    private String placedBet;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date dateCreated;

    @Getter @Setter
    private boolean won;

    @Getter @Setter
    private BigDecimal betPrice;

    @ManyToOne
    @Getter @Setter
    private Coupon coupon;

    public Bet() {
        setDateCreated();
    }

    private void setDateCreated() {
        Date date=new Date();
        this.dateCreated = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet singleBet = (Bet) o;
        return Objects.equals(event, singleBet.event);
    }

    public boolean checkIfUniqe(List<Bet> bets){
        if (bets!=null){
            for( Bet b:bets){
                int x= this.getEvent().getId();
                int y= b.getEvent().getId();
                if (x == y){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}

