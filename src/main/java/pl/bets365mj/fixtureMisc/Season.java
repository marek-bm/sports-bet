package pl.bets365mj.fixtureMisc;

import lombok.Getter;
import lombok.Setter;
import pl.bets365mj.fixture.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private String seasonYear;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @Getter
    @Setter
    @OneToMany(mappedBy = "seasons", cascade = CascadeType.ALL)
    private List<League> leagues;

    @Getter
    @Setter
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Fixture> fixtures;

    @OneToMany(mappedBy = "season")
    @Getter
    @Setter
    List<SeasonResult> seasonResults;
}
