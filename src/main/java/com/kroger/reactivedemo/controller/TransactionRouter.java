package com.kroger.reactivedemo.controller;

import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;

@Configuration
public class TransactionRouter {

    @Bean
    TransactionService transactionService() {
        return new TransactionService();
    }

    @Bean
    RouterFunction<ServerResponse> composedRoutes() {
        return route(GET("/transaction/{id}"),
            request -> ok().body(
                transactionService().getOneTransaction(request.pathVariable("id")),
                Transaction.class
            ))
            .and(route(GET("/transaction"),
                request -> ok().body(
                    transactionService().getAllTransactions(),
                    Transaction.class)
            ))
            .and(route(POST("/transaction/create"),
                request -> request.body(toMono(Transaction.class))
                    .doOnNext(transactionService()::createTransaction)
                    .then(ok().build())));
    }
}
