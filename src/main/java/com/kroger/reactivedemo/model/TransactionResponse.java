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
public class TransactionResponse {

    @NonNull
    private String name;

    private Integer httpCode;

    public Transaction toModel(TransactionResponse transactionResponse){
        return Transaction.builder()
            .name(transactionResponse.name)
            .responseBody(transactionResponse.toString())
            .build();
    }
}
