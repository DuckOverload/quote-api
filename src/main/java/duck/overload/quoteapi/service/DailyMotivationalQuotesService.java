package duck.overload.quoteapi.service;

import duck.overload.openai.model.Message;
import duck.overload.openai.model.OpenAIRequest;
import duck.overload.openai.model.OpenAIResponse;
import duck.overload.openai.service.OpenAIService;
import duck.overload.quoteapi.dto.DailyMotivationalQuoteDto;
import duck.overload.quoteapi.entity.MotivationalQuotes;
import duck.overload.quoteapi.exception.QuoteNotFoundException;
import duck.overload.quoteapi.repository.DailyMotivationalQuotesRepository;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DailyMotivationalQuotesService {

    private static final Message systemMessage = new Message("system", "Você é um assistente que gera frases motivacionais diárias. Não utilize nenhum emoji. Nunca use aspas pois ele já vai retornar numa String.");
    private static final Message userMessage = new Message("user", "Me dê uma frase motivacional.");

    private final DailyMotivationalQuotesRepository dailyMotivationalQuotesRepository;
    private final OpenAIService openAIService;

    public DailyMotivationalQuotesService(DailyMotivationalQuotesRepository dailyMotivationalQuotesRepository, OpenAIService openAIService) {
        this.dailyMotivationalQuotesRepository = dailyMotivationalQuotesRepository;
        this.openAIService = openAIService;
    }

    public DailyMotivationalQuoteDto getByDate(LocalDate date) throws QuoteNotFoundException {
        Optional<MotivationalQuotes> model = dailyMotivationalQuotesRepository.findMotivationalQuotesByDate(date);

        if (model.isPresent()) {
            MotivationalQuotes modelValue = model.get();
            return new DailyMotivationalQuoteDto(modelValue);
        }

        try {
            OpenAIRequest openAIRequest = new OpenAIRequest("gpt-3.5-turbo", List.of(systemMessage, userMessage), 100);
            Call<OpenAIResponse> call = openAIService.textGenerate(openAIRequest);
            Response<OpenAIResponse> callExecute = call.execute();
            if (callExecute.isSuccessful() && callExecute.body() != null) {
                String response = callExecute.body().getFirstMessageContent();
                if (!response.isEmpty()) {
                    MotivationalQuotes modelValue = new MotivationalQuotes();
                    modelValue.setDate(date);
                    modelValue.setQuote(response);
                    dailyMotivationalQuotesRepository.save(modelValue);
                    return new DailyMotivationalQuoteDto(modelValue);
                } else {
                    throw new QuoteNotFoundException(response);
                }
            }
        } catch (Exception e) {
            throw new QuoteNotFoundException("Frase não encontrada.");
        }

        throw new QuoteNotFoundException("Frase não encontrada.");
    }
}
