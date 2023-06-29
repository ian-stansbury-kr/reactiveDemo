package com.kroger.reactivedemo.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebClientResponseException extends Throwable{

    String message;

    Exception ex;
}
