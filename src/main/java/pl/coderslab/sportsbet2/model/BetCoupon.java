package pl.coderslab.sportsbet2.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class BetCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//
//    @NotNull
//    @OneToMany (mappedBy = "coupon")
//    private List<SingleBet> bets;
//
//    public void addBet(SingleBet bet){
//        this.bets.add(bet);
//    }

    @NotNull
    private BigDecimal betValue;

    private BigDecimal winValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @ManyToOne //(cascade = CascadeType.PERSIST)
    private User user;

    private boolean isWon=false;

    private boolean isActive=true;


    //constructor
    public BetCoupon() {
        setDateCreated();
//        this.bets=new ArrayList<>();
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

//    public List<SingleBet> getBets() {
//        return bets;
//    }

    public BigDecimal getBetValue() {
        return betValue;
    }

    public BigDecimal getWinValue() {
        return winValue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public User getUser() {
        return user;
    }

    public boolean getIsWon() {
        return isWon;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public void setBets(List<SingleBet> bets) {
//        this.bets = bets;
//    }

    public void setBetValue(BigDecimal betValue) {
        this.betValue = betValue;
    }

    public void setWinValue(BigDecimal winValue) {
        this.winValue = winValue;
    }

    public void setDateCreated() {
        Date date=new Date();
        this.dateCreated = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
