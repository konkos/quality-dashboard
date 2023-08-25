package gr.uom.strategicplanning.repositories;

import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.ProjectLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLanguageRepository extends JpaRepository<ProjectLanguage, Long> {
}
