package pl.coderslab.sportsbet2.model.sportEvent;

import javax.persistence.*;
import java.util.List;

@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String seasonYear;

    @OneToMany
    private List<Team> teams;

    @OneToMany
    private List<League> leagues;

}
