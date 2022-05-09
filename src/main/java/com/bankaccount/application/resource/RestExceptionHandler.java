package com.bankaccount.application.resource;

import com.bankaccount.domain.exception.BankAccountNotFoundException;
import com.bankaccount.domain.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(BankAccountNotFoundException ex, WebRequest request) {
        return ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value())
                .date(new Date()).message(ex.getMessage()).description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        return ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .date(new Date()).message(ex.getMessage()).description(request.getDescription(false))
                .build();
    }
}
