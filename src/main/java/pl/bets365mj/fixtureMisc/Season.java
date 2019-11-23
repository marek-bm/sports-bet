package pl.bets365mj.fixtureMisc;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.bets365mj.fixture.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String seasonYear;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(mappedBy = "seasons", cascade = CascadeType.ALL)
    private List<League> leagues;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Fixture> fixtures;

    @OneToMany(mappedBy = "season")
    List<SeasonResult> seasonResults;

    private long apiId;
    private int currentMatchday;
}
