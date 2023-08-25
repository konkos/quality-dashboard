package gr.uom.strategicplanning.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.services.*;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.models.enums.ProjectStatus;
import gr.uom.strategicplanning.models.users.User;
import gr.uom.strategicplanning.repositories.ProjectRepository;
import gr.uom.strategicplanning.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final OrganizationAnalysisService organizationAnalysisService;

    @Autowired
    public AnalysisController(ProjectService projectService, OrganizationAnalysisService organizationAnalysisService, AnalysisService analysisService, UserService userService, ProjectRepository projectRepository) {
        this.analysisService = analysisService;
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.organizationAnalysisService = organizationAnalysisService;
        this.projectService = projectService;
    }

    @PostMapping("/start")
    public ResponseEntity startAnalysis(@RequestParam("github_url") String githubUrl, HttpServletRequest request) throws Exception {
        DecodedJWT decodedJWT = TokenUtil.getDecodedJWTfromToken(request.getHeader("AUTHORIZATION"));
        String email = decodedJWT.getSubject();
        User user = userService.getUserByEmail(email);
        Organization organization = user.getOrganization();

        Project project = new Project();
        project.setRepoUrl(githubUrl);
        project.setOrganization(organization);

        organization.addProject(project);

        Optional<Project> projectOptional = projectRepository.findFirstByRepoUrl(githubUrl);

        if (projectOptional.isPresent()) project = projectOptional.get();

        analysisService.fetchGithubData(project);

        if (!project.canBeAnalyzed()) {
            project.setStatus(ProjectStatus.ANALYSIS_TO_BE_REVIEWED);
            return ResponseEntity.ok("Project has been added to the queue");
        }

        projectService.saveProject(project);
        organizationAnalysisService.updateOrganizationAnalysis(organization);

        Map<String, String> response = Collections.singletonMap("message", "Project has been analyzed");
        return ResponseEntity.ok(response);
    }
}
