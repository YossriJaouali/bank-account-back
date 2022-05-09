package com.bankaccount.domain.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String msg) {
        super(msg);
    }
}
