package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.models.domain.Commit;
import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.models.domain.Project;
import gr.uom.strategicplanning.models.stats.GeneralStats;
import gr.uom.strategicplanning.repositories.GeneralStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneralStatsService {

    @Autowired
    private GeneralStatsRepository generalStatsRepository;

    @Autowired
    private LanguageService languageService;

    public void saveGeneralStats(GeneralStats generalStats) {
        generalStatsRepository.save(generalStats);
    }
}
