package pl.coderslab.sportsbet2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    @Min(value = 0)
    private BigDecimal balance;

    @Getter @Setter
    @OneToOne
    private User owner;

    @NotNull
    @Getter @Setter
    private String bankAccount;

    @ElementCollection( targetClass = String.class )
    @Getter @Setter
    private List<String> transactions=new ArrayList<>();

}
