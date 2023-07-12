package gr.uom.strategicplanning.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAnalysis {

    @Id
    private Long id;
    @OneToMany
    private Collection<CodeSmell> codeSmells;
    private float technicalDebt;
    private float techDebtPerLoC;
    private int totalCodeSmells;
    
}
