package gr.uom.strategicplanning.controllers.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.ProjectLanguage;
import gr.uom.strategicplanning.models.enums.ProjectStatus;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.models.stats.ProjectStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String name;
    private String organizationName;
    private Long organizationId;
    private String repoUrl;
    private int forks;
    private int stars;
    private int totalLanguages;
    private ProjectStatus status;
    private Collection<LanguageResponse> languages = new HashSet<>();
    private ProjectStats projectStats;

    @JsonIgnore
    private int totalDevelopers;
    @JsonIgnore
    private int totalCommits;
    @JsonIgnore
    private Set<DeveloperResponse> developers = new HashSet<>();
    @JsonIgnore
    private Collection<CommitResponse> commits = new HashSet<>();

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.repoUrl = project.getRepoUrl();
        this.forks = project.getForks();
        this.stars = project.getStars();
        this.totalDevelopers = project.getTotalDevelopers();
        this.totalCommits = project.getTotalCommits();
        this.totalLanguages = project.getTotalLanguages();
        this.status = project.getStatus();
        this.projectStats = project.getProjectStats();
        this.organizationName = project.getOrganization().getName();
        this.organizationId = project.getOrganization().getId();

        Collection<ProjectLanguage> languages = project.getLanguages();
        for (ProjectLanguage language : languages) {
            this.languages.add(new LanguageResponse(language));
        }
    }

    public static List<ProjectResponse> convertToProjectResponseList(List<Project> projects) {
        List<ProjectResponse> projectResponses = new java.util.ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(new ProjectResponse(project));
        }
        return projectResponses;
    }
}
