package gr.uom.strategicplanning.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commit {

    @Id
    @GeneratedValue
    private Long id;
    private String hash;
    @OneToOne
    private Developer developer;
    private Date commitDate;
    @OneToOne
    private CommitAnalysis commitAnalysis;

}
