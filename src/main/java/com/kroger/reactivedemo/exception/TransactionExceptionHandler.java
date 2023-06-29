package com.kroger.reactivedemo.exception;

import java.sql.SQLException;

import com.kroger.reactivedemo.exception.model.SomeCustomErrorMessage;
import com.kroger.reactivedemo.exception.model.TransactionDbException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(TransactionDbException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SomeCustomErrorMessage handleTransactionDbException(){
        return SomeCustomErrorMessage.builder().message("Db done blew up").build();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SomeCustomErrorMessage handleDbException(){
        return SomeCustomErrorMessage.builder().message("Db done blew up").build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SomeCustomErrorMessage handleTransactionRequestException(){
        return SomeCustomErrorMessage.builder().message("Bad Request").build();
    }


}
