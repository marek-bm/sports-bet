package pl.bets365mj.fixtureMisc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.bets365mj.fixture.Fixture;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int division;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Season> seasons;
    @OneToOne
    private Country country;
    @OneToMany(mappedBy = "league")
    private List<Fixture> fixtures;
    private int apiId;
}
