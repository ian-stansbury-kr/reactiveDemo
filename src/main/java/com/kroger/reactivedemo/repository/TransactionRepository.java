package com.kroger.reactivedemo.repository;

import com.kroger.reactivedemo.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {


}
