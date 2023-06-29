package com.kroger.reactivedemo.client;

import java.time.Duration;

import com.kroger.reactivedemo.config.ConfigConstants;
import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

/**
 * Web client setup
 */
@Component
public class AuthorizationClient {

    private WebClient webClient;

    public AuthorizationClient(@Value("$client.authorization.baseUrl") String baseUrl){

        //note be sure to use netty version
        HttpClient client = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(ConfigConstants.timeoutvalue));

        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Someheader", "someValue")
            .clientConnector(new ReactorClientHttpConnector(client))
            .build();
    }

    public Flux<TransactionResponse> getAuthorizationResponse(Transaction transaction){
        return webClient.post()
            .uri(ConfigConstants.authEndpoint)
            .body(BodyInserters.fromValue(transaction))
            .retrieve()
            .bodyToFlux(TransactionResponse.class)
            .doFirst(() -> System.out.println("calling fake service"))
            .doOnNext(cr -> System.out.println("Got a response " + cr));

    }
}
