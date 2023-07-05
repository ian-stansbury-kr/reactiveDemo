package com.kroger.reactivedemo.service;

import java.util.UUID;

import com.kroger.reactivedemo.client.AuthorizationClient;
import com.kroger.reactivedemo.model.Transaction;
import com.kroger.reactivedemo.repository.TransactionRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import static reactor.core.publisher.Mono.when;
import reactor.test.StepVerifier;

class TransactionServiceTest {

    private static final String TRANSACTION_ID = UUID.randomUUID().toString();

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AuthorizationClient authorizationClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Transaction transaction =
            Transaction.builder().name("blah").responseBody("somebody").build();
        when(transactionRepository.findById(anyString()).thenReturn(Mono.just(transaction)));
        when(authorizationClient.getAuthorizationResponse(any()).thenReturn(Mono.just(transaction)));
    }

    /**
     * Format for tests:
     * //Given
     * //When
     * //Then
     */
    @Test
    void createTransactions() {
        //Given
        Transaction request = Transaction.builder().name("blah").build();
        //When
        StepVerifier.create(transactionService.createTransaction(request))
        //Then
            .verifyComplete();

    }

    @Test
    void getOneTransaction() {
        //Given
        String transId = TRANSACTION_ID;
        //When
        StepVerifier.create(transactionService.getOneTransaction(transId))
            //Then
            .verifyComplete();
    }

    @Test
    void authorizeTransaction() {
        //Given
        //When
        //Then
    }

    @Test
    void getAllTransactions() {
        //Given
        //When
        //Then
    }


}