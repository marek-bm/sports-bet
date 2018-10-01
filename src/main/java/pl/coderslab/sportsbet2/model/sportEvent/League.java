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

    @OneToMany
    private List<Season> seasons;

    @OneToOne
    private Country country;

    @OneToMany
    private List<Fixture> fixtures;

}
