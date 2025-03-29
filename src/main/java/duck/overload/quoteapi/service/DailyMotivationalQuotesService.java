package duck.overload.quoteapi.service;

import duck.overload.openai.model.OpenAIRequest;
import duck.overload.openai.service.OpenAIService;
import duck.overload.quoteapi.dto.DailyMotivationalQuoteDto;
import duck.overload.quoteapi.entity.MotivationalQuotes;
import duck.overload.quoteapi.exception.QuoteNotFoundException;
import duck.overload.quoteapi.repository.DailyMotivationalQuotesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyMotivationalQuotesService {

    private final DailyMotivationalQuotesRepository dailyMotivationalQuotesRepository;

    public DailyMotivationalQuotesService(DailyMotivationalQuotesRepository dailyMotivationalQuotesRepository, OpenAIService openAIService) {
        this.dailyMotivationalQuotesRepository = dailyMotivationalQuotesRepository;
    }

    public DailyMotivationalQuoteDto getByDate (LocalDate date) throws QuoteNotFoundException {
        Optional<MotivationalQuotes> model = dailyMotivationalQuotesRepository.findMotivationalQuotesByDate(date);

        if (model.isPresent()) {
            MotivationalQuotes modelValue = model.get();
            return new DailyMotivationalQuoteDto(modelValue);
        }

        OpenAIRequest openAIRequest = new OpenAIRequest();

        throw new QuoteNotFoundException("Frase não encontrada.");
    }

}
