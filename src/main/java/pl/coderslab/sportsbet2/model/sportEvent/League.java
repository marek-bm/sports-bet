package pl.coderslab.sportsbet2.model.sportEvent;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int division;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Season> seasons;

    @OneToOne
    @Getter
    @Setter
    private Country country;

    @OneToMany(mappedBy = "league")
    @Getter
    @Setter
    private List<Fixture> fixtures;
}
