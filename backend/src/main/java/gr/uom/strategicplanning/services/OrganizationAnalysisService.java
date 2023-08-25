package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.models.analyses.OrganizationAnalysis;
import gr.uom.strategicplanning.models.domain.*;
import gr.uom.strategicplanning.models.stats.GeneralStats;
import gr.uom.strategicplanning.repositories.OrganizationAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OrganizationAnalysisService {
    @Value("${organization.analysis.top-lags-count}")
    private int TOP_LANGUAGES_COUNT;

    private OrganizationAnalysisRepository organizationAnalysisRepository;

    private GeneralStatsService generalStatsService;

    @Autowired
    private LanguageService languageService;

    public OrganizationAnalysisService(OrganizationAnalysisRepository organizationAnalysisRepository,TechDebtStatsService techDebtStatsService, ActivityStatsService activityStatsService, GeneralStatsService generalStatsService) {
        this.organizationAnalysisRepository = organizationAnalysisRepository;
        this.generalStatsService = generalStatsService;
    }


    public void updateOrganizationAnalysis(Organization organization) {
        // Current datetime
        long currentTimestamp = System.currentTimeMillis();
        // Convert to date
        java.sql.Date currentDate = new java.sql.Date(currentTimestamp);

        OrganizationAnalysis organizationAnalysis = organization.getOrganizationAnalysis();
        organizationAnalysis.setAnalysisDate(currentDate);

        if (organizationAnalysis == null) {
            organizationAnalysis = new OrganizationAnalysis();
        }

        // Set Most Forked and Most Starred Projects
        Project mostStarredProject = getMostStarredProject(organization);
        Project mostForkedProject = getMostForkedProject(organization);

        organizationAnalysis.setOrgName(organization.getName());
        organizationAnalysis.setMostForkedProject(mostForkedProject);
        organizationAnalysis.setMostStarredProject(mostStarredProject);

        updateGeneralStats(organization);

        organizationAnalysisRepository.save(organizationAnalysis);
    }

    private GeneralStats updateGeneralStats(Organization organization) {
        GeneralStats generalStats = organization.getOrganizationAnalysis().getGeneralStats();

        // Find the number of projects
        int numberOfProjects = getNumberOfProjects(organization);
        generalStats.setTotalProjects(numberOfProjects);

        // Find the number of commits
        int numberOfCommits = getNumberOfCommits(organization);
        generalStats.setTotalCommits(numberOfCommits);

        // Update the language statistics
        updateLanguageStats(organization);

        generalStats.findTopLanguages(TOP_LANGUAGES_COUNT);
        generalStats.calculateTotalLinesOfCode();

        int totalLanguages = generalStats.getLanguages().size();
        generalStats.setTotalLanguages(totalLanguages);

        generalStatsService.saveGeneralStats(generalStats);
        return generalStats;
    }

    private Collection<Language> updateLanguageStats(Organization organization) {
        Collection<Language> languages = organization
                .getOrganizationAnalysis()
                .getGeneralStats()
                .getLanguages();

        GeneralStats generalStats = organization.getOrganizationAnalysis().getGeneralStats();

        for (Project project : organization.getProjects()) {
            for (ProjectLanguage language : project.getLanguages()) {
                String projectLanguageName = language.getName();
                int projectLoC = language.getBytesOfCode();

                // Check if the language already exists in the database
                Optional<Language> existingLanguage = languageService.getLanguageByName(projectLanguageName);
                Language newLanguage = new Language();

                if (existingLanguage.isPresent()) newLanguage = existingLanguage.get();

                // Update the language's stats
                int totalLoCBefore = newLanguage.getTotalBytesOfCode();
                int totalLoCAfter = totalLoCBefore + projectLoC;
                newLanguage.setName(projectLanguageName);
                newLanguage.setTotalBytesOfCode(totalLoCAfter);
                newLanguage.setGeneralStats(generalStats);

                languageService.saveLanguage(newLanguage);

                // Update the language's stats in the general stats
                generalStats.addLanguage(newLanguage);
            }
        }

        return languages;
    }

    private int getNumberOfCommits(Organization organization) {
        int numberOfCommits = 0;
        for (Project project : organization.getProjects()) {
            numberOfCommits += project.getTotalCommits();
        }
        return numberOfCommits;
    }

    private Project getMostStarredProject(Organization organization) {
        int maxStars = 0;
        for (Project project : organization.getProjects()) {
            if (project.getStars() > maxStars) {
                maxStars = project.getStars();
            }
        }

        for (Project project : organization.getProjects()) {
            if (project.getStars() == maxStars) {
                return project;
            }
        }

        return null;
    }

    private Project getMostForkedProject(Organization organization) {
        int maxForks = 0;

        // TODO: This is a bug. If there are two or more projects with the same number of forks, only one will be returned.
        Project mostForkedProject = null;
        for (Project project : organization.getProjects()) {
            if (project.getForks() >= maxForks) {
                maxForks = project.getForks();
                mostForkedProject = project;
            }
        }

        return mostForkedProject;
    }

    private int getNumberOfProjects(Organization organization) {
        return organization.getProjects().size();
    }

    public void saveOrganizationAnalysis(OrganizationAnalysis organizationAnalysis) {
        generalStatsService.saveGeneralStats(organizationAnalysis.getGeneralStats());
        organizationAnalysisRepository.save(organizationAnalysis);
    }
}
