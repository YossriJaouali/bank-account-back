package com.bankaccount.application.dto;

import com.bankaccount.domain.model.BankAccount;
import com.bankaccount.domain.model.OperationType;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDto {
    private OperationType operationType;
    private double amount;
    private OffsetDateTime creationDate;
    private String description;
    private BankAccount bankAccount;
}
