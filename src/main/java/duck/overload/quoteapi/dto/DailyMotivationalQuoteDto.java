package duck.overload.quoteapi.dto;

import duck.overload.quoteapi.entity.MotivationalQuotes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DailyMotivationalQuoteDto {

    private Long id;

    private String quote;

    public DailyMotivationalQuoteDto(MotivationalQuotes modelValue) {
        this.id = modelValue.getId();
        this.quote = modelValue.getQuote();
    }

}
