package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.analysis.github.GithubApiClient;
import gr.uom.strategicplanning.analysis.sonarqube.SonarAnalyzer;
import gr.uom.strategicplanning.models.domain.Commit;
import gr.uom.strategicplanning.models.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnalysisService {
    private SonarAnalyzer sonarAnalyzer;
    private final GithubApiClient githubApiClient;
    private final ProjectService projectService = new ProjectService();
    @Autowired
    private LanguageService languageService;

    @Autowired
    public AnalysisService(@Value("${github.token}") String githubToken) {
        this.githubApiClient = new GithubApiClient(githubToken);
    }
    
    public void fetchGithubData(Project project) throws Exception {
        githubApiClient.fetchProjectData(project);
    }

    public void startAnalysis(Project project) throws Exception {
        GithubApiClient.cloneRepository(project);

        sonarAnalyzer = new SonarAnalyzer(project.getName() + project.hashCode());
        sonarAnalyzer.analyzeProject(project);

        GithubApiClient.deleteRepository(project);
    }
}
