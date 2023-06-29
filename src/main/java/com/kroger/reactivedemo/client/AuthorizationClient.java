package com.kroger.reactivedemo.client;

import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Web client setup
 */
@Component
public class AuthorizationClient {

    private WebClient webClient;

    public AuthorizationClient(@Value("$client.authorization.baseUrl") String baseUrl){

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();

    }

    public Flux<TransactionResponse> getAuthorizationResponse(Transaction transaction){
        return webClient.post()
            .uri("/authorize")
            .body(BodyInserters.fromValue(transaction))
            .retrieve()
            .bodyToFlux(TransactionResponse.class)
            .doFirst(() -> System.out.println("calling fake service"))
            .doOnNext(cr -> System.out.println("Got a response " + cr));

    }
}
