package pl.coderslab.sportsbet2.model.sportEvent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL)
    private List<Result> results;

    //constructor
    public Team() {
        this.results=new ArrayList<>();
    }

    //add result

    public void addResult(Result result){
        this.results.add(result);
    }


    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
