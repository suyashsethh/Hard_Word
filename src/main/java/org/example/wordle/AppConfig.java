package org.example.wordle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public WordRepository wordRepository() {
        return new WordRepository();
    }

    @Bean
    public GameService gameService() {
        return new GameService(wordRepository());
    }
}
