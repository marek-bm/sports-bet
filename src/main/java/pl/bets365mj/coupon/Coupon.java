package pl.bets365mj.coupon;

import lombok.Getter;
import lombok.Setter;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @NotNull
    @OneToMany (mappedBy = "coupon", cascade = CascadeType.MERGE)
    @Getter @Setter
    private List<Bet> bets;

    @NotNull
    @Min(value = 1)
    @Getter @Setter
    private BigDecimal betValue;

    @Getter @Setter
    private BigDecimal winValue;

    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @ManyToOne //(cascade = CascadeType.PERSIST)
    @Getter @Setter
    private User user;

    @Getter @Setter
    @Column (name = "is_won")
    private boolean won=false;

    @Getter @Setter
    @Column (name = "is_active")
    private boolean active=true;

    //constructor
    public Coupon() {
        setDateCreated();
        setActive(true);
        this.bets=new ArrayList<>();
    }

//    public List<SingleBet> getBets() {
//        return bets;
//    }

//    public void setBets(List<SingleBet> bets) {
//        this.bets = bets;
//    }

    public void setWinValue(BigDecimal winValue) {
        this.winValue = winValue;
    }

    public void setDateCreated() {
        Date date=new Date();
        this.dateCreated = date;
    }
}
