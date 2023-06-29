package com.kroger.reactivedemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @NonNull
    private String name;

    private String responseBody;

    public TransactionResponse fromModel(Transaction transactionToReturn) {
        return TransactionResponse.builder()
            .name(transactionToReturn.name)
            .httpCode(201)
            .build();
    }
}
