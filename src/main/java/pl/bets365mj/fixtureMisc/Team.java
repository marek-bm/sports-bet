package pl.bets365mj.fixtureMisc;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private long apiId;

    private String logoUrl;

    public Team() {this.results = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apiId=" + apiId +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return apiId == team.apiId &&
                id.equals(team.id) &&
                name.equals(team.name) &&
                Objects.equals(logoUrl, team.logoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, apiId, logoUrl);
    }
}
