package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.models.analyses.OrganizationAnalysis;
import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.LanguageStats;
import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.models.stats.ActivityStats;
import gr.uom.strategicplanning.models.stats.GeneralStats;
import gr.uom.strategicplanning.models.stats.TechDebtStats;
import gr.uom.strategicplanning.repositories.OrganizationAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OrganizationAnalysisService {

    private OrganizationAnalysisRepository organizationAnalysisRepository;

    private GeneralStatsService generalStatsService;

    private ActivityStatsService activityStatsService;

    private TechDebtStatsService techDebtStatsService;

    public OrganizationAnalysisService(OrganizationAnalysisRepository organizationAnalysisRepository,TechDebtStatsService techDebtStatsService, ActivityStatsService activityStatsService, GeneralStatsService generalStatsService) {
        this.organizationAnalysisRepository = organizationAnalysisRepository;
        this.generalStatsService = generalStatsService;
        this.activityStatsService = activityStatsService;
        this.techDebtStatsService = techDebtStatsService;
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

        int numberOfProjects = getNumberOfProjects(organization);
        generalStats.setTotalProjects(numberOfProjects);

        int numberOfCommits = getNumberOfCommits(organization);
        generalStats.setTotalCommits(numberOfCommits);

        updateLanguageStats(organization);

        return generalStats;
    }

    private Collection<Language> updateLanguageStats(Organization organization) {
        Collection<Language> languages = organization
                .getOrganizationAnalysis()
                .getGeneralStats()
                .getLanguages();

        GeneralStats generalStats = organization.getOrganizationAnalysis().getGeneralStats();


        for (Project project : organization.getProjects()) {
            for (Language language : project.getLanguages()) {
                generalStats.addLanguage(language);
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
