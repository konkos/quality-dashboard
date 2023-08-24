package gr.uom.strategicplanning.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

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
    private int linesOfCode;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private Project project;

    public Language(String name) {
        this.name = name;
    }

    public boolean is(String language) {
        return this.name.equals(language);
    }
}
