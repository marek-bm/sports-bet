package pl.coderslab.sportsbet2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Min(value = 0)
    @Getter @Setter
    private BigDecimal balance;

    @OneToOne
    @Getter @Setter
    private User owner;


    @Getter @Setter
    private Float bankAccount;

    @ElementCollection( targetClass = String.class )
    @Getter @Setter
    private List<String> transactions=new ArrayList<>();

}
