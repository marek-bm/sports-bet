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
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<Fixture> fixtures;
    private int apiId;

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", division=" + division +
                ", seasons=" + seasons +
                ", country=" + country +
                ", apiId=" + apiId +
                '}';
    }
}
