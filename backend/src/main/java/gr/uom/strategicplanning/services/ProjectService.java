package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.models.domain.*;
import gr.uom.strategicplanning.models.enums.ProjectStatus;
import gr.uom.strategicplanning.repositories.ProjectLanguageRepository;
import gr.uom.strategicplanning.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private ProjectLanguageRepository projectLanguageRepository;

    public void saveProject(Project project) {
//        Collection<Language> langs = project.getLanguages();
//
//        for (Language lang : langs) {
//            languageService.saveLanguage(lang);
//        }

        projectRepository.save(project);
    }

    public void authorizeProjectForAnalysis(Long id) throws Exception {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setStatus(ProjectStatus.ANALYSIS_READY);
        analysisService.startAnalysis(project);
        saveProject(project);
    }

    public void unauthorizeProjectForAnalysis(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setStatus(ProjectStatus.ANALYSIS_SKIPPED);
        saveProject(project);
    }
}
