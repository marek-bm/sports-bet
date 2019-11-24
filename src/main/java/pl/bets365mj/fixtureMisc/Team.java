package pl.bets365mj.fixtureMisc;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Season, SeasonResult> results;

    private int apiId;

    private String logoUrl;

    public Team() {this.results = new HashMap<>();
    }
}
