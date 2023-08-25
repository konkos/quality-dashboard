package gr.uom.strategicplanning.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.uom.strategicplanning.models.stats.GeneralStats;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Language {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imageUrl;
    private int totalBytesOfCode = 0;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private GeneralStats generalStats;


    public Language(String name) {
        this.name = name;
    }

    public boolean is(String language) {
        return this.name.equals(language);
    }
}
