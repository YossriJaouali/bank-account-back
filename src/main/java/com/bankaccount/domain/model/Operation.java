package com.bankaccount.domain.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operation {
    private OperationType operationType;
    private double amount;
    private OffsetDateTime creationDate;
    private String description;
    private BankAccount bankAccount;
}
