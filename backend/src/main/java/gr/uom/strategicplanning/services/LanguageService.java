package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.analysis.github.GithubApiClient;
import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.LanguageStats;
import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class LanguageService {

    private final GithubApiClient githubApiClient;
    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, @Value("${github.token}") String githubToken) {
        this.languageRepository = languageRepository;
        this.githubApiClient = new GithubApiClient(githubToken);
    }
    public Optional<Language> getLanguageByName(String languageName) {
        return languageRepository.findByName(languageName);
    }

    public void saveLanguage(Language newLanguage) {
        languageRepository.save(newLanguage);
    }

}
