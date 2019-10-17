package pl.coderslab.sportsbet2.fixture.eventData;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private Map<Season, SeasonResult> results;
//    private List<SeasonResult> results;

    //constructor
    public Team() {
        this.results = new HashMap<>();
    }
}
