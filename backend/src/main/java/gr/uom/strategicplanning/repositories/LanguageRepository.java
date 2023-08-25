package gr.uom.strategicplanning.repositories;

import gr.uom.strategicplanning.models.domain.Language;
import gr.uom.strategicplanning.models.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("SELECT l FROM Language l WHERE l.name = ?1")
    Optional<Language> findByName(String languageName);
}
