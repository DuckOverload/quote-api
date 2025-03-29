package duck.overload.quoteapi.controller;

import duck.overload.quoteapi.dto.DailyMotivationalQuoteDto;
import duck.overload.quoteapi.exception.QuoteNotFoundException;
import duck.overload.quoteapi.service.DailyMotivationalQuotesService;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/quote")
public class MotivationalQuoteController {

    private final DailyMotivationalQuotesService dailyMotivationalQuotesService;

    public MotivationalQuoteController(DailyMotivationalQuotesService dailyMotivationalQuotesService) {
        this.dailyMotivationalQuotesService = dailyMotivationalQuotesService;
    }

    @GetMapping(value = "/motivational")
    public DailyMotivationalQuoteDto getDailyQuote() throws QuoteNotFoundException {
        return dailyMotivationalQuotesService.getByDate(LocalDate.now());
    }

    @GetMapping(value = "/motivational/{date}")
    public DailyMotivationalQuoteDto getQuoteByDate(@PathVariable String date) throws QuoteNotFoundException {
        LocalDate localDate = LocalDate.parse(date);
        return dailyMotivationalQuotesService.getByDate(localDate);
    }

}
