package gr.uom.strategicplanning.models.analyses;

import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.models.stats.ActivityStats;
import gr.uom.strategicplanning.models.stats.GeneralStats;
import gr.uom.strategicplanning.models.stats.TechDebtStats;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrganizationAnalysis {
    public static final int COMMITS_THRESHOLD = 50;

    @Id
    @GeneratedValue
    private Long id;
    private String orgName;
    private Date analysisDate;
    @OneToOne
    private GeneralStats generalStats = new GeneralStats();
    @OneToOne
    private TechDebtStats techDebtStats;
    @OneToOne
    private ActivityStats activityStats;
    @OneToOne
    private Project mostStarredProject;
    @OneToOne
    private Project mostForkedProject;
    @OneToOne
    @ToString.Exclude
    private Organization organization;
}