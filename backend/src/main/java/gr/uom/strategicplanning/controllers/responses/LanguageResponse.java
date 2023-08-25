package gr.uom.strategicplanning.controllers.responses;

import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.ProjectLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageResponse {
    public Long id;
    public String name;
    public String imageUrl;
    public int bytesOfCode;

    public LanguageResponse(Language languageStats) {
        this.id = languageStats.getId();
        this.name = languageStats.getName();
        this.imageUrl = languageStats.getImageUrl();
        this.bytesOfCode = languageStats.getTotalBytesOfCode();
    }

    public LanguageResponse(ProjectLanguage languageStats) {
        this.id = languageStats.getId();
        this.name = languageStats.getName();
        this.imageUrl = "None";
        this.bytesOfCode = languageStats.getBytesOfCode();
    }
}

