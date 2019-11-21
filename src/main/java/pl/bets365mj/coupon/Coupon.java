package pl.bets365mj.coupon;

import lombok.Data;
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
@Data
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull @OneToMany (mappedBy = "coupon",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Bet> bets;
    @NotNull @Min(value = 1)
    private BigDecimal betValue;
    private BigDecimal winValue;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @ManyToOne (cascade = CascadeType.MERGE)
    private User user;
    @Column (name = "is_won")
    private boolean won=false;
    @Column (name = "is_active")
    private boolean active=true;

    public Coupon() {
        setDateCreated();
        setActive(true);
        this.bets=new ArrayList<>();
    }

    public void setDateCreated() {
        Date date=new Date();
        this.dateCreated = date;
    }

    public void addBet(Bet newBet){
        if(bets.contains(newBet)){
            return;
        } else {
            bets.add(newBet);
            newBet.setCoupon(this);
        }
    }

    public void removeBet(Bet bet){
        bets.removeIf(b -> b.equals(bet));
        bet.setCoupon(null);
    }

    public void calculateWin(List<Bet> bets, BigDecimal charge) {
        this.winValue = charge;
        for (Bet bet : bets) {
            winValue = winValue.multiply(bet.getBetPrice());
        }
    }
}
