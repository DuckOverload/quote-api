package duck.overload.quoteapi.config;

import duck.overload.openai.client.OpenAIClient;
import duck.overload.openai.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public OpenAIService openAIService() {
        OpenAIClient client = new OpenAIClient(apiKey);
        return client.createService();
    }

}
