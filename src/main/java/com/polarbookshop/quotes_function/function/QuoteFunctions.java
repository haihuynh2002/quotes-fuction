package com.polarbookshop.quotes_function.function;

import com.polarbookshop.quotes_function.domain.Genre;
import com.polarbookshop.quotes_function.domain.Quote;
import com.polarbookshop.quotes_function.domain.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class QuoteFunctions {
    private static final Logger log =
            LoggerFactory.getLogger(QuoteFunctions.class);


    @Bean
    Supplier<Flux<Quote>> allQuotes(QuoteService quoteService) {
        return () -> {
            log.info("Getting all quotes");
            return quoteService.getAllQuotes()
                    .delaySequence(Duration.ofSeconds(1));
        };
    }

    @Bean
    Supplier<Mono<Quote>> randomQuote(QuoteService quoteService) {
        return () -> {
            log.info("Getting random quote");
            return quoteService.getRandomQuote();
        };
    }

    @Bean
    Consumer<Mono<Quote>> logQuote() {
        return mono ->
                mono.doOnNext(quote -> {
                    log.info("Quote: {} by {}", quote.content(), quote.author());
                });
    }

    @Bean
    Function<Genre, Mono<Quote>> genreQuote(QuoteService quoteService) {
        return quoteService::getRandomQuoteByGenre;
    }
}
