package com.kroger.reactivedemo.service;

import com.kroger.reactivedemo.client.AuthorizationClient;
import com.kroger.reactivedemo.exception.model.TransactionCreationException;
import com.kroger.reactivedemo.exception.model.TransactionDbException;
import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.model.TransactionResponse;
import com.kroger.reactivedemo.repository.TransactionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransactionService {

    private TransactionRepository transactionRepository;

    private AuthorizationClient authorizationClient;

    /**
     * Throws new error is empty, also logging
     * @param id
     * @return
     */
    public Mono<Transaction> getOneTransaction(String id){
        return transactionRepository.findById(id)
            .switchIfEmpty(Mono.error(new TransactionDbException("failed to find thing with id:" + id)))
            .doFirst(() -> System.out.println("Looking up transaction"))
            .doOnError(ex -> System.out.println("Failed to look up transaction"))
            .doFinally(signalType -> System.out.println("Looked up transaction"));
    }

    public Flux<TransactionResponse> authorizeTransaction(Transaction transaction){
        return authorizationClient.getAuthorizationResponse(transaction)
            .map(response -> response.toModel(response))
            .flatMap(transactionToSave -> createTransactions(transactionToSave))
            .map(transactionToReturn -> transactionToReturn.fromModel(transactionToReturn));
    }

    /**
     * Remaps Error
     * @return
     */
    public Flux<Transaction> getAllTransactions(){
        return transactionRepository.findAll().onErrorResume(e ->
            Mono.error(TransactionDbException::new));
    }

    /**
     * maps any exception to new exception and preserves error message
     * @param transactionRequest
     * @return
     */
    public Mono<Transaction> createTransactions(Transaction transactionRequest){
        return Mono.just(transactionRequest)
                .flatMap(transaction -> transactionRepository.save(transaction)
                    .onErrorMap(ex -> new TransactionCreationException(ex.getMessage())));
    }
}
