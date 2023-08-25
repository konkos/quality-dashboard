package gr.uom.strategicplanning.models.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.uom.strategicplanning.models.analyses.OrganizationAnalysis;
import gr.uom.strategicplanning.models.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private int totalBytesOfCode;

    @OneToOne
    @JsonIgnore
    private OrganizationAnalysis organizationAnalysis;

    public void addLanguage(Language language) {
        if (!this.languages.contains(language))
            this.languages.add(language);
    }

    public Map<Integer, Language> findTopLanguages(int numberOfTopLanguages) {
        // Create a comparator to sort languages by lines of code in descending order
        Comparator<Language> linesOfCodeComparator = (l1, l2) ->
                Integer.compare(l2.getTotalBytesOfCode(), l1.getTotalBytesOfCode());

        // Sort the languages using the comparator
        List<Language> sortedLanguages = languages.stream()
                .sorted(linesOfCodeComparator)
                .collect(Collectors.toList());

        // Clear the existing topLanguages map
        topLanguages.clear();

        // Get the specified number of top languages or fewer if there are less than that number of languages
        int topCount = Math.min(numberOfTopLanguages, sortedLanguages.size());
        for (int i = 0; i < topCount; i++) {
            topLanguages.put(i + 1, sortedLanguages.get(i));
        }

        // Return the top languages
        return this.topLanguages;
    }

    public int calculateTotalLinesOfCode() {
        int totalLoC = languages.stream()
                .mapToInt(Language::getTotalBytesOfCode)
                .sum();

        this.totalBytesOfCode = totalLoC;

        return this.totalBytesOfCode;
    }
}
