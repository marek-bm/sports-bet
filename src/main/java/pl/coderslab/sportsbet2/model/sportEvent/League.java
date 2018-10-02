package pl.coderslab.sportsbet2.model.sportEvent;

import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
public class League {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private int division;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Season> seasons;

    @OneToOne
    private Country country;

    @OneToMany (mappedBy = "league")
    private List<Fixture> fixtures;

}
