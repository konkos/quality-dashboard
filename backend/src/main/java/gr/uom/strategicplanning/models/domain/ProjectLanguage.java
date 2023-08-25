package gr.uom.strategicplanning.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class ProjectLanguage {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int bytesOfCode;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private Project project;

    public boolean is(ProjectLanguage language) {
        return this.name.equals(language.getName());
    }
}
