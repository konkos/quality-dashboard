package gr.uom.strategicplanning.models.stats;

import gr.uom.strategicplanning.models.analyses.OrganizationAnalysis;
import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.LanguageStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralStats {

    @Id
    @GeneratedValue
    private Long id;
    private int totalProjects;
    private int totalLanguages;
    @ManyToMany
    private List<Language> languages;
    @ManyToMany
    private Map <Integer, Language> topLanguages;
    private int totalCommits;
    private int totalFiles;
    private int totalLinesOfCode;
    private int totalDevs;
    @OneToOne
    private OrganizationAnalysis organizationAnalysis;

    public Language addLanguage(Language language) {
        Optional<Language> existingLanguage = getLanguageByName(language.getName());

        if (existingLanguage.isEmpty()) {
            Language newLanguage = new Language();
            newLanguage.setName(language.getName());
            newLanguage.setLinesOfCode(language.getLinesOfCode());

            languages.add(newLanguage);
            this.totalLanguages = languages.size();
            return newLanguage;
        }

        Language lang = existingLanguage.get();
        int currentLinesOfCode = lang.getLinesOfCode();
        lang.setLinesOfCode(currentLinesOfCode + language.getLinesOfCode());

        return lang;
    }

    private boolean languageExists(Language language) {
        for (Language lang : languages) {
            if (lang.getName().equals(language.getName())) {
                return true;
            }
        }
        return false;
    }

    private Optional<Language> getLanguageByName(String languageName) {
        for (Language lang : languages) {
            if (lang.getName().equals(languageName)) {
                return Optional.of(lang);
            }
        }
        return Optional.empty();
    }
}
