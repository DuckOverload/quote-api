package duck.overload.quoteapi.repository;

import duck.overload.quoteapi.entity.MotivationalQuotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyMotivationalQuotesRepository extends JpaRepository<MotivationalQuotes, Long> {

    Optional<MotivationalQuotes> findMotivationalQuotesByDate(LocalDate date);
    
}
