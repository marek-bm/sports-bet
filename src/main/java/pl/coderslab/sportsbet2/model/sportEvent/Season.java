package pl.coderslab.sportsbet2.model.sportEvent;

import pl.coderslab.sportsbet2.model.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String seasonYear;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany (cascade = CascadeType.ALL)
    private List<League> leagues;

    @OneToMany (mappedBy = "season", cascade = CascadeType.ALL)
    private  List<Fixture> fixtures;


}
