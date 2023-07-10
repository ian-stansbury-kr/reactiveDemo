package com.kroger.reactivedemo.controller;

import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping("/{ids}")
    public Mono<Transaction> getTransactionById(@PathVariable String ids){
        return transactionService.getOneTransaction(ids);
    }

    @GetMapping
    public Flux<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public Mono<Transaction> createTransaction(@RequestBody Transaction transaction){
        return transactionService.createTransaction(transaction);
    }
}
